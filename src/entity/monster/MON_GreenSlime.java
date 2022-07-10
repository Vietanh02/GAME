package entity.monster;

import Graphics.SpriteSheet;
import entity.Entity;
import entity.Projectile.Projectile;
import entity.Projectile.Ptile_rock;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp){
        super(gp);
        type =2;
        name = "Green Slime";
        speed = 1;
        maxLife = 10;
        life = maxLife;
        atk = 5;
        def = 0;
        EXP = 2;
        skillShot = new Ptile_rock(gp);
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

        SpriteSheet ss = new SpriteSheet("monster/monsters.png",48,48);

        for(int i=0;i<2;i++) {
            up[i]   = ss.spriteArray[3][9+i].image;
            down[i] =  ss.spriteArray[0][9+i].image;
            left[i] =  ss.spriteArray[1][9+i].image;
            right[i] =  ss.spriteArray[2][9+i].image;
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
        int i = new Random().nextInt(100)+1;
        if(i > 99 && !skillShot.alive && shotCounter == 50){
            skillShot.set(worldX,worldY,direction,true,this);
            gp.projectileList.add(skillShot);
            shotCounter = 0;
        }
    }
    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer){
            if(!gp.player.invincible){
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
        if(shotCounter < 50){
            shotCounter++;
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
