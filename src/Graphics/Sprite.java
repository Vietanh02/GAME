package Graphics;

import java.awt.image.BufferedImage;

public class Sprite {
    public BufferedImage image;

    private int width;
    private int height;

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public Sprite(BufferedImage image){
        this.image = image;
        this.width = getWidth();
        this.height = getHeight();
    }

    public Sprite getSubimage(int x, int y, int w, int h) {
        return new Sprite(image.getSubimage(x, y, w, h));
    }
    /*
    ham` getSubimage sẽ trả lại 1 ảnh con sau khi cắt có tọa độ x,y , kích thước w,h
    x,y la` tọa độ của ảnh con trong ảnh to
    w,h la` do rong va chieu cao anh tinh theo pixel
     */
}
