package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Heart";
        type = type_pickupOnly;
        value = 2;
        stay[0] = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        stay[1] = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        stay[2] = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        collision = false;
    }
    public void use(Entity entity){
        gp.ui.addMessage("Life + " + value);
        gp.player.life += value;
    }
}
