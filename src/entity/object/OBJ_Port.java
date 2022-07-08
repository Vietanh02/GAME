package object;

import Graphics.SpriteSheet;
import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

public class OBJ_Port extends Entity {
    public OBJ_Port(GamePanel gp){
        super(gp);
        name = "Port";
        SpriteSheet ss = new SpriteSheet("objects/Port0.png",48*3,48*2);
        stay[0] = ss.spriteArray[3][2].image;

        stay[1] = stay[0];
        stay[2] = stay[0];
        collision = false;
        solidArea.x = 28;
        solidArea.y = 4;
        solidArea.width = 44*2;
        solidArea.height = 44*2;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
