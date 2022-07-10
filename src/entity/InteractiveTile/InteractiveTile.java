package entity.InteractiveTile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveTile extends Entity {
    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row){
        super(gp);
        this.gp = gp;
    }
    public boolean isCorrectItem(Entity entity) {
        return false;
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }
    public void update(){
        if(invincible){
           invincibleCounter++;
           if(invincibleCounter > 20){
               invincible = false;
               invincibleCounter = 0;
           }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = switch (direction) {
            case "up" -> up[spriteNum - 1];
            case "down" -> down[spriteNum - 1];
            case "left" -> left[spriteNum - 1];
            case "right" -> right[spriteNum - 1];
            case "stay" -> stay[spriteNum - 1];
            default -> null;
        };

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //

        if (gp.player.screenX > gp.player.worldX){
            screenX = worldX;
        }
        if (gp.player.screenY > gp.player.worldY){
            screenY = worldY;
        }
        int rightOffset = gp.screenWidth -gp.player.screenX;
        if (rightOffset > gp.WorldWidth - gp.player.worldX){
            screenX = gp.screenWidth - (gp.WorldWidth -worldX);
        }
        int bottomOffset = gp.screenHeight -gp.player.screenY;
        if (bottomOffset > gp.WorldHeight - gp.player.worldY){
            screenY = gp.screenHeight - (gp.WorldHeight -worldY);
        }

        if(worldX + gp.tileSize  > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize  < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize  > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize  < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY,null);
        }
        else if (gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.WorldWidth - gp.player.worldX ||
                bottomOffset > gp.WorldHeight - gp.player.worldY){

            g2.drawImage(image, screenX, screenY,null);
        }
    }
}
