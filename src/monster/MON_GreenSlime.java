package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp){
        super(gp);
        name = "Green Slime";
        speed = 1;
        maxLife = 10;
        life = maxLife;
        direction = "down";
        type = 2;
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){

        String imageDown1 = "/monster/greenslime_down_%d";
        String imageUp1 = "/monster/greenslime_down_%d";
        String imageLeft1 = "/monster/greenslime_down_%d";
        String imageRight1 = "/monster/greenslime_down_%d";

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
            speed = 1;
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
        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "right" -> worldX += speed;
                case "left" -> worldX -= speed;
            }
        }
        spriteCounter++;
        if (spriteCounter == 12) {
            if (spriteNum == 2) spriteNum = 0;
            spriteNum++;
            spriteCounter = 0;
        }
        //neu quai bi danh, bat tu 1 thoi gian
        if(invicible){
            invicibleCounter++;
            if(invicibleCounter > 60){
                invicible = false;
                invicibleCounter = 0;
            }
        }
    }
    public void damageReaction(){
        speed = 4;
        actionLockCounter = 80;
        switch (gp.player.direction){
            case "up" -> direction =  "down";
            case "down" -> direction =  "up";
            case "left" -> direction =  "right";
            case "right" -> direction =  "left";
        };
    }
}
