package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity{
    public NPC_OldMan(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }
    public void setDialogue(){
        dialogues[0] = "Hello,Kiwi.";
        dialogues[1] = "Kiwi oi! Kiwi di dau day?";
        dialogues[2] = "Toi la Ure";
        dialogues[3] = "Co 1 cai ruong o gan day";
        dialogues[4] = "Toi dang ban, hay de luc khac noi chuyen";
        dialogues[5] = "Nay chang trai tre a, cau co biet ve nguoi anh \nhung Ba Binh, nguoi ma da danh bai quai vat \nSima de giai cuu the gioi khong?";

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
    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);
        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
            }
        }
        spriteCounter++;
        if (spriteCounter == 12) {
            if (spriteNum == 2) spriteNum = 0;
            spriteNum++;
            spriteCounter = 0;
        }
    }
    public void speak(){
        super.speak();
    }
}
