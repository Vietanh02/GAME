package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject {
    String resImage = new String("/objects/Layer 1_chest_%d.png");
    GamePanel gp;
    public OBJ_Chest(GamePanel gp){
        this.gp =gp;
        name = "Chest";
        try {
            for (int i = 0; i < 4; i++) {
                image[i] = ImageIO.read(getClass().getResourceAsStream(String.format(resImage, i + 1)));
                image[i]=uTool.scaleImage(image[i], gp.tileSize, gp.tileSize);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        collision = true;
    }
}
