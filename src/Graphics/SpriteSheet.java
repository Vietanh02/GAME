package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics2D;
import Graphics.Font;

public class SpriteSheet {

    public Sprite[][] spriteArray;   // Mảng các ảnh
    private final int TILE_SIZE = 24;    /* Kích thước mặc định ( dùng trong hàm cắt theo 32x32
                                            Cái này VA có thể chỉnh tùy vào cái ảnh mà ae add vào nhé
                                            */

    private Sprite SPRITESHEET = null;

    public int width;      //   Độ rộng của ảnh ( tính theo pixel)
    public int height;  //  Độ cao ảnh ( tính theo pixel )

    private float x;    //  Tọa độ x
    private float y;    //  Tọa độ y

    private int wSprite;    //  Số phần rộng cắt ra được tính bằng : độ rộng ảnh/ độ rộng ảnh con
    private int hSprite;    //  Số phần cao cắt ra được tính bằng : độ cao ảnh/ độ cao ảnh con

    private String file;    //  khai báo 1 file đầu vào

/* Hàm getSprite ở dòng xx trả về một ảnh nhỏ (sprite) trong mảng ảnh đã cắt ra có tọa độ là x,y , chiều rộng, cao đã biết
   sử dụng trong hàm chia ảnh đã biết kích thước ( VD: hàm SpriteSheet dòng xx đã biết TILE_SIZE )
   giá trị trả lại sẽ là 1 sprite ( ảnh con ) ở vị trí có tọa độ tính theo pixel

   */
    public Sprite getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * width, y * height, width, height);
    }

    public Sprite getSprite(int x, int y, int width, int height) {
        return SPRITESHEET.getSubimage(x * width, y * height, width, height);
    }
/* hàm setWidth setHeight setSize dùng để tính kích thước mảng ảnh ( VD : ảnh 1024x576 chia theo
    16x16 sẽ có kích thước là 64 x36
 */
    public void setWidth(int index) {
        width = index;
        wSprite = SPRITESHEET.image.getWidth() / width;
    }
    public void setHeight(int index) {
        height = index;
        hSprite = SPRITESHEET.image.getHeight() / height;
    }
    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
//  Ngoại lệ khi k load được ảnh
    private BufferedImage loadSprite(String file){
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("ERROR: could not load this file: " + file);
        }
        return image;
    }
// mảng 2 chiều gồm các ảnh con sau khi được cắt
    public void loadSpriteArray() {
        spriteArray = new Sprite[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }
    //  Các phương thức khởi tạo của SpriteSheet ( overloading )
    //  khác nhau ở đầu vào



    //  Đầu vào là 1 file ảnh, chia theo kích thước mặc định là TILE_SIZE
    //  trả về đối tượng là một mảng 2 chiều gồm các ảnh Sprite (ảnh con) sau khi cắt
    //  phương thức SpriteSheet ngay dưới đây thích hợp cho ae làm hiệu ứng nhân vật, quái có kích thước 32x32
    public SpriteSheet(String file){
        this.file = file;
        this.width = TILE_SIZE;
        this.height = TILE_SIZE;

        System.out.println("Loading: "+file);
        SPRITESHEET = new Sprite(loadSprite(file)); //  Kiểm tra ngoại lệ
        wSprite = SPRITESHEET.image.getWidth() / width;
        hSprite = SPRITESHEET.image.getHeight() / height;
        loadSpriteArray();
    }

    //  đầu vào là 1 Sprite, kích cỡ của Sprite muốn cắt
    /*  đầu ra là đối tượng ( đối tượng này chính là phần ảnh mà mình muốn cắt ,
     nói cách khác là trả về 1 cái ảnh con nhưng thuộc đối tượng là SpriteSheet
     giúp mn lấy được các ảnh nhỏ độc lập bên trong 1 ảnh lớn
     VD trong phần vũ khí 1 ảnh có rất nhiều vũ khí, ae xài cái phương thức khởi tạo này sẽ
     trả về đối tượng là 1 cái ảnh là 1 vũ khí duy nhất
     ** nói cách khác cái hàm này chính là cái tool mà ae hay dùng
     */
    public SpriteSheet(Sprite sprite, int width, int height) {
        this.width = width;
        this.height = height;

        SPRITESHEET = sprite;

        wSprite = SPRITESHEET.image.getWidth() / width;
        hSprite = SPRITESHEET.image.getHeight() / height;
        loadSpriteArray();
    }
/*  PT khởi tạo này chỉ khác với SpriteSheet đầu tiền ở chỗ là ae có thể cắt theo kích cỡ tùy ý
    vẫn trả về đối tượng là 1 mảng ảnh
    PT SpriteSheet này thích hợp cho ae làm hiệu ứng nhân vật quái có kích cỡ tùy chỉnh
 */
    public SpriteSheet(String file, int width, int height) {
        this.file = file;
        this.width = width;
        this.height = height;

        System.out.println("Loading: " + file + ".....");
        SPRITESHEET = new Sprite(loadSprite(file));

        wSprite = SPRITESHEET.image.getWidth() / width;
        hSprite = SPRITESHEET.image.getHeight() / height;
        loadSpriteArray();
    }


    public static void drawArray(Graphics2D g, ArrayList<BufferedImage> img, Vector2D pos, int width, int height,
                                 int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        for (int i = 0; i < img.size(); i++) {
            if (img.get(i) != null) {
                g.drawImage(img.get(i), (int) x, (int) y, width, height, null);
            }
            x += xOffset;
            y += yOffset;
        }
    }
    public static Font currentFont;

    public static void drawArray(Graphics2D g, String word, Vector2D vector, int size) {
        drawArray(g, currentFont, word, vector, size, size, size, 0);
    }

    public static void drawArray(Graphics2D g, String word, Vector2D vector, int size, int xOffset) {
        drawArray(g, currentFont, word, vector, size, size, xOffset, 0);
    }

    public static void drawArray(Graphics2D g, String word, Vector2D vector, int width, int height, int xOffset) {
        drawArray(g, currentFont, word, vector, width, height, xOffset, 0);
    }

    public static void drawArray(Graphics2D g, Font f, String word, Vector2D vector, int size, int xOffset) {
        drawArray(g, f, word, vector, size, size, xOffset, 0);
    }
    public static void drawArray(Graphics2D g, Font f, String word, Vector2D vector,
                                 int width, int height, int xOffset, int yOffset) {
        float x = vector.x;
        float y = vector.y;
        currentFont = f;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != 32) {
                g.drawImage(f.getFont(word.charAt(i)), (int) x, (int) y, width, height, null);
            }
            x += xOffset;
            y += yOffset;
        }
    }

}
