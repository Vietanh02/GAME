package entity.monster;

import Graphics.SpriteSheet;
import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Mob_Catgirl extends Entity {
    public Mob_Catgirl(GamePanel gp){
        super(gp);
        name = "Catgirl";
        speed = 1;
        maxLife = 20;
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

        SpriteSheet ss = new SpriteSheet("monster/Mob.png",48,48);

        for(int i=0;i<2;i++) {
            up[i]   = ss.spriteArray[7][i].image;
            down[i] =  ss.spriteArray[4][i].image;
            left[i] =  ss.spriteArray[5][i].image;
            right[i] =  ss.spriteArray[6][i].image;
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
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true){
            if(gp.player.invincible == false){
                //	player can give damage

                gp.player.life -=1;
                gp.player.invincible = true;
            }
        }
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
        actionLockCounter = 0;
        switch (gp.player.direction){
            case "up" -> direction =  "down";
            case "down" -> direction =  "up";
            case "left" -> direction =  "right";
            case "right" -> direction =  "left";
        };
    }
}
