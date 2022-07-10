package entity.Projectile;

import entity.Entity;
import main.GamePanel;

public class Ptile_Fireball extends Projectile{

    GamePanel gp;
    public Ptile_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Fireball";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        atk = 2;
        skillManaCost = 1;
        alive = false;
        getImage();
    }

    private void getImage() {
        String imageDown1 = "/bullet/fireball_down_%d";
        String imageUp1 = "/bullet/fireball_up_%d";
        String imageLeft1 = "/bullet/fireball_left_%d";
        String imageRight1 = "/bullet/fireball_right_%d";

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
}
