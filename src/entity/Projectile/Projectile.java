package entity.Projectile;

import entity.Entity;
import main.GamePanel;

public class Projectile extends Entity {
    Entity user;
    public Projectile(GamePanel gp){
        super(gp);
    }
    public void set(int worldX, int worldY, String direction, boolean alive,Entity user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        life = maxLife;
    }
    public void update(){
        if(user == gp.player){
            int monIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monIndex != 999){
                gp.player.damageMonster(monIndex, atk);
                alive = false;
            }
        }else if(user.type == 2){
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(!gp.player.invincible&&contactPlayer){
                damagePlayer(atk);
                alive = false;
            }
        }

        switch (direction){
            case "up"-> worldY-=speed;
            case "left"-> worldX-=speed;
            case "down"-> worldY+=speed;
            case "right"-> worldX+=speed;
        }
        life--;
        if(life < 0){
            alive = false;
        }
        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 2){
                spriteNum = 1;
            } else if(spriteNum == 1) spriteNum = 2;
        }
    }
    public boolean haveResource(Entity user){
        return false;
    }
    public void subtractResource(Entity user){
    }
}
