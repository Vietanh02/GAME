package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
    String resImage = new String("/objects/Layer 1_chest_%d");
    public OBJ_Chest(GamePanel gp){
        super(gp);
        isTreasure = true;
        name = "Chest";
            for (int i = 0; i < 3; i++) {
                stay[i] = setup(String.format(resImage, i + 1), gp.tileSize, gp.tileSize);
            }
        collision = true;
    }
}
