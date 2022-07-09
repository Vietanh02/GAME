package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp){
        super(gp);

        value = itemValue;
        type = type_shield;
        name = "Shield Wood";
        down[0] = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 10;
        description = "[" + name + "]\n" + "Defense value: " + defenseValue;

    }
}
