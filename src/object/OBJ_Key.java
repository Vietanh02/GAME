package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject {
    GamePanel gp;
    public OBJ_Key(GamePanel gp){
        this.gp =gp;
        name = "Key";
        try{
            for(int i=0;i<4;i++) {
                image[i] = ImageIO.read(getClass().getResourceAsStream("/objects/Key0.png"));
                image[i] = uTool.scaleImage(image[i], gp.tileSize, gp.tileSize);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        collision = true;
    }
}
