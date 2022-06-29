package object;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject {
    public OBJ_Key(){
        name = "Key";
        try{
            for(int i=0;i<4;i++)
                image[i] = ImageIO.read(getClass().getResourceAsStream("/objects/Key0.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
        collision = true;
    }
}
