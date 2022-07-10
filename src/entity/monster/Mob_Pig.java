package entity.monster;

import Graphics.SpriteSheet;
import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Mob_Pig extends Entity {
    public Mob_Pig(GamePanel gp){
        super(gp);
        name = "Pig";
        speed = 1;
        maxLife = 5;
        life = maxLife;
        direction = "down";
        type = type_monster;
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
            up[i]   = ss.spriteArray[3][6+i].image;
            down[i] =  ss.spriteArray[0][6+i].image;
            left[i] =  ss.spriteArray[1][6+i].image;
            right[i] =  ss.spriteArray[2][6+i].image;
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
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
        speed = 5;
        switch (gp.player.direction){
            case "up" -> direction =  "up";
            case "down" -> direction =  "down";
            case "left" -> direction =  "left";
            case "right" -> direction =  "right";
        };
    }
}
