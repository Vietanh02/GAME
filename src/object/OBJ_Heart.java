package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Heart extends SuperObject{
    GamePanel gp;
    public OBJ_Heart(GamePanel gp){
        this.gp = gp;
        name = "Heart";
        try{
                image[0] = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
                image[1] = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
                image[2] = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
                uTool.scaleImage(image[0],48,48);
                uTool.scaleImage(image[1],48,48);
                uTool.scaleImage(image[2],48,48);
        }catch(Exception e){
            e.printStackTrace();
        }
        collision = true;
    }
}
