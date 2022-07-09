package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_AXE extends Entity {

    public OBJ_AXE(GamePanel gp){
        super(gp);

        value = itemValue;
        type = type_axe;
        name = "Woodcutter's Axe";
        stay[0] = setup("/objects/axe", gp.tileSize, gp.tileSize);
        stay[1] = setup("/objects/axe", gp.tileSize, gp.tileSize);
        stay[2] = setup("/objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 20;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\n" + "Attack value: " + attackValue;
    }
}
