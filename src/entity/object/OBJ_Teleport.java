package entity.object;


import Graphics.SpriteSheet;
import entity.Entity;
import main.GamePanel;

public class OBJ_Teleport extends Entity {
    public OBJ_Teleport(GamePanel gp){
        super(gp);

        name = "Teleport";
        type = type_Port;
        SpriteSheet sp = new SpriteSheet("objects/SF_Door2.png",48,48);
        stay[0] = sp.spriteArray[4][9].image;
        stay[1] = stay[0];
        stay[2] = stay[0];
        collision = false;
    }
}
