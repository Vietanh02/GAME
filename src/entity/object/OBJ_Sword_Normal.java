package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp){
        super(gp);

        name = "Normal Sword";
        down[0] = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 10;
        description = "[" + name + "]\n" + "Attack value: " + attackValue;

    }
}