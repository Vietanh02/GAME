package entity.Projectile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class Ptile_rock extends Projectile {
        GamePanel gp;
        public Ptile_rock(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Rock";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        atk = 5;
        skillManaCost = 1;
        alive = false;
        getImage();
    }

    private void getImage() {
        String imageDown1 = "/bullet/rock_down_1";
        String imageUp1 = "/bullet/rock_down_1";
        String imageLeft1 = "/bullet/rock_down_1";
        String imageRight1 = "/bullet/rock_down_1";

        for(int i=0;i<2;i++) {
            up[i]   = setup(String.format(imageUp1,i+1),gp.tileSize, gp.tileSize);
            down[i] = setup(String.format(imageDown1,i+1), gp.tileSize, gp.tileSize );
            left[i] = setup(String.format(imageLeft1,i+1),gp.tileSize, gp.tileSize);
            right[i] = setup(String.format(imageRight1,i+1),gp.tileSize, gp.tileSize);
        }
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
        return new Color(65,50,30);
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
