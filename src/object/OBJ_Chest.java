package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Chest extends Entity {
    String resImage = new String("/objects/Layer 1_chest_%d");
    public OBJ_Chest(GamePanel gp){
        super(gp);
        name = "Chest";
            for (int i = 0; i < 3; i++) {
                stay[i] = setup(String.format(resImage, i + 1));
            }
        collision = true;
    }
}
