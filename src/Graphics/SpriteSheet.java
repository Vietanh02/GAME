package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics2D;
import Graphics.Font;

public class SpriteSheet {
    public Sprite[][] spriteArray;
    private final int TILE_SIZE = 32;
    private Sprite SPRITESHEET = null;

    public int width;
    public int height;

    private float x;
    private float y;

    private int wSprite;
    private int hSprite;

    private String file;

    public Sprite getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * width, y * height, width, height);
    }

    public Sprite getSprite(int x, int y, int width, int height) {
        return SPRITESHEET.getSubimage(x * width, y * height, width, height);
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

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

    private BufferedImage loadSprite(String file){
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("ERROR: could not load this file: " + file);
        }
        return image;
    }

    public void loadSpriteArray() {
        spriteArray = new Sprite[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }

    public SpriteSheet(String file){
        this.file = file;
        this.width = TILE_SIZE;
        this.height = TILE_SIZE;

        System.out.println("Loading: "+file);
        SPRITESHEET = new Sprite(loadSprite(file));
        wSprite = SPRITESHEET.image.getWidth() / width;
        hSprite = SPRITESHEET.image.getHeight() / height;
        loadSpriteArray();
    }
    //123
    public static void drawArray(Graphics2D g, ArrayList<BufferedImage> img, Vector2D pos, int width, int height, int xOffset, int yOffset) {
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
    public static void drawArray(Graphics2D g, Font f, String word, Vector2D vector, int width, int height, int xOffset, int yOffset) {
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
