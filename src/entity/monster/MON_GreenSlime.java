package entity.monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp){
        super(gp);
        type =2;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        direction = "down";
        
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){

        String imageDown1 = new String("/monster/greenslime_down_%d");
        String imageUp1 = new String("/monster/greenslime_down_%d");
        String imageLeft1 = new String("/monster/greenslime_down_%d");
        String imageRight1 = new String("/monster/greenslime_down_%d");

        for(int i=0;i<2;i++) {
            up[i]   = setup(String.format(imageUp1,i+1));
            down[i] = setup(String.format(imageDown1,i+1));
            left[i] = setup(String.format(imageLeft1,i+1));
            right[i] = setup(String.format(imageRight1,i+1));
        }
    }
    public void setAction(){

        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;//ngau nhien tu 1 toi 100
            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else direction = "right";
            actionLockCounter = 0;
        }
    }
    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);
        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
            }
        }
        spriteCounter++;
        if (spriteCounter == 12) {
            if (spriteNum == 2) spriteNum = 0;
            spriteNum++;
            spriteCounter = 0;
        }
    }
}
