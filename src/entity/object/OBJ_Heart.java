package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp){
        super(gp);
        name = "Heart";
                stay[0] = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
                stay[1] = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
                stay[2] = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);
        collision = false;
    }
}
