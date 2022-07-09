package entity.object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Blue_Shield extends Entity {

    public OBJ_Blue_Shield(GamePanel gp){
        super(gp);

        value = itemValue;
        type = type_shield;
        name = "Blue Shield";
        stay[0] = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        stay[1] = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        stay[2] = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 20;
        description = "[" + name + "]\n" + "Defense value: " + defenseValue;

    }

}
