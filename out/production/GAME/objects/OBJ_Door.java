package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class OBJ_Door extends  SuperObject {
    public OBJ_Door(){
        name = "Door";
        try{
            for(int i=0;i<4;i++)
                image[i] = ImageIO.read(getClass().getResourceAsStream("/objects/door0.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
