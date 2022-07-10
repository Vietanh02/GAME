package entity.monster;

import Graphics.SpriteSheet;
import entity.Entity;
import entity.Projectile.Ptile_DarkEnergy;
import entity.Projectile.Ptile_rock;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Boss_DarkLord extends Entity {
    public Boss_DarkLord(GamePanel gp){
        super(gp);
        name = "Dark_Lord";
        speed = 4;
        maxLife = 5;
        life = maxLife;
        atk = 10;
        def = 0;
        EXP = 50;
        skillShot = new Ptile_DarkEnergy(gp);
        direction = "down";
        type = type_monster;
        solidArea.x = 5;
        solidArea.y = 5;
        solidArea.width = 110;
        solidArea.height = 110;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        isBoss = true;
        getImage();
}
    public void getImage(){

        SpriteSheet ss = new SpriteSheet("monster/Lord.png",120,120);

        for(int i=0;i<2;i++) {
            up[i]   = ss.spriteArray[2][i].image;
            down[i] =  ss.spriteArray[2][i+1].image;
            left[i] =  ss.spriteArray[2][i].image;
            right[i] =  ss.spriteArray[2][i+1].image;
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
        speed = 6;
        actionLockCounter = 60;
        switch (gp.player.direction){
            case "up" -> direction =  "down";
            case "down" -> direction =  "up";
            case "left" -> direction =  "right";
            case "right" -> direction =  "left";
        };
        skillShot.set(worldX,worldY,direction,true,this);
        gp.projectileList.add(skillShot);
//        skillShot.set(worldX+50,worldY+50,direction,true,this);
//        gp.projectileList.add(skillShot);
        shotCounter = 0;
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
            if((type == 2 && hpOn)) {
                double oneScale = (double)2*gp.tileSize/maxLife;
                g2.setColor(Color.BLACK);
                g2.fillRect(screenX+10,screenY-16,(int) (oneScale*maxLife)+2,7);
                g2.setColor(Color.red);
                g2.fillRect(screenX+11,screenY-15, (int) (oneScale*life),5);
                hpOnCounter++;
                if(hpOnCounter > 600) {
                    hpOn = false;
                    hpOnCounter = 0;
                }
            }
            if(invincible) {
                hpOn = true;
                hpOnCounter = 0;
                changeAlpha(g2,0.4f);
            }
            if(dying) dyingAnimation(g2);
            g2.drawImage(image, screenX, screenY,null);
            changeAlpha(g2,1f);

        }
        else if (gp.player.screenX > gp.player.worldX ||
                gp.player.screenY > gp.player.worldY ||
                rightOffset > gp.WorldWidth - gp.player.worldX ||
                bottomOffset > gp.WorldHeight - gp.player.worldY){
            if((type == 2 && hpOn)) {
                double oneScale = (double)2*gp.tileSize/maxLife;
                g2.setColor(Color.BLACK);
                g2.fillRect(screenX+10,screenY-16,(int) (oneScale*maxLife)+2,7);
                g2.setColor(Color.red);
                g2.fillRect(screenX+11,screenY-15, (int) (oneScale*life),5);
                hpOnCounter++;

                if(hpOnCounter > 600) {
                    hpOn = false;
                    hpOnCounter = 0;
                }
            }
            if(invincible) {
                hpOn = true;
                hpOnCounter = 0;
                changeAlpha(g2,0.4f);
            }
            if(dying) dyingAnimation(g2);
            g2.drawImage(image, screenX, screenY,null);
            changeAlpha(g2,1f);
        }
    }
}
