package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp){
        super(gp);
        name = "Key";
        stay[0] = setup("/objects/Key0");
        collision = true;
    }
}
