package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp){
        super(gp);

        value = itemValue;
        type = type_sword;
        name = "Normal Sword";
        stay[0] = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
        stay[1] = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
        stay[2] = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);

        attackValue = 10;
        attackArea.width = 32;
        attackArea.height = 32;
        description = "[" + name + "]\n" + "Attack value: " + attackValue;

    }
}
