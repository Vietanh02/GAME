package main;

import java.awt.*;


public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
//

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 25;
            eventRect[col][row].y = 25;
            eventRect[col][row].width = gp.tileSize/2;
            eventRect[col][row].height = gp.tileSize/2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }


    }
    public void checkEvent(){
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        if(canTouchEvent){
//            if (hit(3, 3, "any")) {damagePit(3,3,gp.dialogueState);}
            if (hit(40, 36,1,1, "any")) {telemap(70,50,gp.dialogueState);}
//            if(hit(10,10,"up") == true){healingPool(gp.dialogueState);}
        }
    }

    public boolean hit(int col, int row, String reqDirection){

        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[row][col])&& !eventRect[col][row].eventDone){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
    public boolean hit(int x, int y,int width, int height, String reqDirection){

        boolean hit = false;
        Rectangle eventRectan = new Rectangle(x*gp.tileSize,y*gp.tileSize,width*gp.tileSize,height*gp.tileSize);
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;


        if(gp.player.solidArea.intersects(eventRectan)){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return hit;
    }

    public void damagePit(int col, int row, int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "tripping grass and fall";
        gp.player.life -= 1;
//        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }


    public void telemap (int col, int row, int gameState){
        gp.gameState = gameState ;
        gp.ui.currentDialogue = "Teleport";
        gp.player.worldX = gp.tileSize * 70;
        gp.player.worldY = gp.tileSize * 30;
    }
    public void healingPool(int gameState){
        if(gp.keyH.enterPressed){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "Let's play and gain heart";
            gp.player.life += 1;
        }
    }
}