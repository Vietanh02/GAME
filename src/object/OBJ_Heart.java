package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp){
        super(gp);
        name = "Heart";
                stay[0] = setup("/objects/heart_full");
                stay[1] = setup("/objects/heart_half");
                stay[2] = setup("/objects/heart_blank");
        collision = false;
    }
}
