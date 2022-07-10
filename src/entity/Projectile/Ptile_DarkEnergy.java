package entity.Projectile;

import entity.Entity;
import main.GamePanel;
import Graphics.SpriteSheet;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Ptile_DarkEnergy extends Projectile{
    GamePanel gp;
    public Ptile_DarkEnergy(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "DarkEnergy";
        speed = 5;
        maxLife = 100;
        life = maxLife;
        atk = 5;
        skillManaCost = 0;
        alive = false;
        getImage();
    }

    private void getImage() {
        SpriteSheet ss = new SpriteSheet("bullet/Flame.png",48,48);
        down[0] = ss.spriteArray[3][0].image;
        down[1] = ss.spriteArray[3][1].image;
        ss = new SpriteSheet("bullet/Flame2.png",48,48);
        up[0] = ss.spriteArray[0][0].image;
        up[1] = ss.spriteArray[0][1].image;
        ss = new SpriteSheet("bullet/Flame3.png",48,48);
        right[0] = ss.spriteArray[0][0].image;
        right[1] = ss.spriteArray[1][0].image;
        ss = new SpriteSheet("bullet/Flame4.png",48,48);
        left[0] = ss.spriteArray[0][0].image;
        left[1] = ss.spriteArray[1][0].image;
    }
    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if(user.mana >= skillManaCost){
            haveResource = true;
        }
        return haveResource;
    }
    public void subtractResource(Entity user){
        user.mana -= skillManaCost;
    }
    public Color getParticleColor(){
        return new Color(210,50,210);
    }
    public int getParticleSize(){
        return 10;
    }
    public int getParticleSpeed(){
        return 2;
    }
    public int getParticleMaxLife(){
        return 20;
    }

    public void draw(Graphics2D g2){
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.titleSize, gp.titleSize);
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
            g2.drawImage(image, screenX, screenY,gp.tileSize*2,gp.tileSize*2,null);
        }
        else if (gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.WorldWidth - gp.player.worldX ||
                bottomOffset > gp.WorldHeight - gp.player.worldY){
            g2.drawImage(image, screenX, screenY,gp.tileSize*2,gp.tileSize*2,null);
        }
    }
}
