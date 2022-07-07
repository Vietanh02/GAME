package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class OBJ_Door extends Entity {
    public OBJ_Door(GamePanel gp){
        super(gp);
        name = "Door";
            for(int i=0;i<1;i++) {
                stay[i] = setup("/objects/door0");
            }
            collision = true;
            solidArea.x = 0;
            solidArea.y = 16;
            solidArea.width = 48;
            solidArea.height = 32;
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y;
    }
}
