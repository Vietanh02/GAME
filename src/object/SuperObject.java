package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage[] image = new BufferedImage[4];
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    private static int spriteCounter = 0 ;
    private static int spriteNum = 0;

    public void update(){
        spriteCounter++;
        if(spriteCounter>12) {
            if(spriteNum == 0) {
                spriteNum =1;
            }
            else if(spriteNum ==1) {
                spriteNum = 2;
            }
            else if(spriteNum ==2) {
                spriteNum = 3;
            }else if(spriteNum ==3){
                spriteNum = 0;
            }
            spriteCounter = 0;

        }
    }
    public void draw(Graphics2D g2, GamePanel gp){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //

        if(worldX + gp.tileSize  > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize  < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize  > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize  < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(image[spriteNum], screenX, screenY,gp.tileSize, gp.tileSize, null);

        }

    }
}
