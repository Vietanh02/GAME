package object;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject {
    String resImage = new String("/objects/Layer 1_chest_%d.png");
    public OBJ_Chest(){
        name = "Chest";
        try{
            for(int i=0;i<4;i++)
                image[i] = ImageIO.read(getClass().getResourceAsStream(String.format(resImage,i+1)));
        }catch(Exception e){
            e.printStackTrace();
        }
        collision = true;
    }
}
