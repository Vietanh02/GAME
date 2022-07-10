package entity;

import main.GamePanel;

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
        dialogues[0] = "Welcome to summoner's rift captain Kiwi!!";
        dialogues[1] = "The monster is destroying my village,\n we need captain Kiwi to save our lives";
        dialogues[2] = "Fistly, let's kill small monsters,\n collect items to upgrade your power";
        dialogues[3] = "Then, find a gate where you can teleport ";
        dialogues[4] = "You will see the manor of the DesTroyBoss!";
        dialogues[5] = "Finally, Let's kill this boss and receive your treasure!!!";

    }
    public void getImage(){
        // tải các ảnh vảo mảng

        String imageDown = "/npc/oldman_down_%d";
        String imageUp = "/npc/oldman_up_%d";
        String imageLeft = "/npc/oldman_left_%d";
        String imageRight = "/npc/oldman_right_%d";
        String imageStay = "/npc/oldman_down_%d";
        for(int i=0;i<2;i++) {
            stay[i] = setup(String.format(imageStay,i+1), gp.tileSize, gp.tileSize);
            up[i]   = setup(String.format(imageUp,i+1), gp.tileSize, gp.tileSize);
            down[i] = setup(String.format(imageDown,i+1), gp.tileSize, gp.tileSize);
            left[i] = setup(String.format(imageLeft,i+1), gp.tileSize, gp.tileSize);
            right[i] = setup(String.format(imageRight,i+1), gp.tileSize, gp.tileSize);
        }
    }
    public void setAction() {
        if(onPath){
            int goalCol = 29;
            int goalRow = 50;
            searchPath(goalCol,goalRow);
        } else {
            actionLockCounter++;
            if (actionLockCounter == 200) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;//ngau nhien tu 1 toi 100
                if (i <= 25) {
                    direction = "up";
                } else if (i <= 50) {
                    direction = "down";
                } else if (i <= 75) {
                    direction = "left";
                } else {
                    direction = "right";
                }
            }
        }

    }
    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);
        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "right" -> worldX += speed;
                case "left" -> worldX -= speed;
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
