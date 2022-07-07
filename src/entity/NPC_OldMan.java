package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity{
    public NPC_OldMan(GamePanel gp){
        super(gp);
        direction = "down";
        getImage();
    }
    public void getImage(){
        // tải các ảnh vảo mảng

        String imageDown = new String("/npc/oldman_down_%d");
        String imageUp = new String("/npc/oldman_up_%d");
        String imageLeft = new String("/npc/oldman_left_%d");
        String imageRight = new String("/npc/oldman_right_%d");
        String imageStay = new String("/npc/oldman_down_%d");
        for(int i=0;i<2;i++) {
            stay[i] = setup(String.format(imageStay,i+1));
            up[i]   = setup(String.format(imageUp,i+1));
            down[i] = setup(String.format(imageDown,i+1));
            left[i] = setup(String.format(imageLeft,i+1));
            right[i] = setup(String.format(imageRight,i+1));
        }
    }
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;//ngau nhien tu 1 toi 100
            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else direction = "right";
            actionLockCounter = 0;
        }
    }
}
