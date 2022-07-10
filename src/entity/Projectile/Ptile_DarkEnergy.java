package entity.Projectile;

import entity.Entity;
import main.GamePanel;
import Graphics.SpriteSheet;
import java.awt.*;

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
}
