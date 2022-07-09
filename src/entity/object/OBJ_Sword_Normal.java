package entity.object;

import entity.Entity;
import main.GamePanel;
import entity.Player;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp){
        super(gp);

        value = itemValue;
        type = type_sword;
        name = "Normal Sword";
        down[0] = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 10;
        attackArea.width = 32;
        attackArea.height = 32;
        description = "[" + name + "]\n" + "Attack value: " + attackValue;

    }
}
