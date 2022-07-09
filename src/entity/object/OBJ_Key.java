package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp){
        super(gp);

        value = itemValue;
        name = "Key";
        stay[0] = setup("/objects/Key0", gp.tileSize, gp.tileSize);
        stay[1] = setup("/objects/Key0", gp.tileSize, gp.tileSize);
        stay[2] = setup("/objects/Key0", gp.tileSize, gp.tileSize);
        collision = true;
        description = "[" + name + "]\n" + "It uses to open the door !";
    }
}
