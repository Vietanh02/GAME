package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    public OBJ_ManaCrystal(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "ManaCrystal";
        type = type_pickupOnly;
        value = 2;
        stay[0] = setup("/objects/crystal_full", gp.tileSize, gp.tileSize);
        stay[1] = setup("/objects/crystal_full", gp.tileSize, gp.tileSize);
        stay[2] = setup("/objects/crystal_full", gp.tileSize, gp.tileSize);
        collision = false;
    }
    public void use(Entity entity){
        gp.ui.addMessage("Mana + " + value);
        gp.player.mana += value;
    }
}
