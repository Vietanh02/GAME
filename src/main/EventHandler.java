package main;

import java.awt.*;
import java.awt.image.BufferedImage;


public class EventHandler {
    GamePanel gp;
    EventRect[][] eventRect;
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
            eventRect[col][row].x = 22;
            eventRect[col][row].y = 22;
            eventRect[col][row].width = 4;
            eventRect[col][row].height = 4;
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
            if (hit(3,3, "any")) {damagePit(gp.dialogueState);}
            if (hit(19,19,2,1,"any")) {telemap(gp.dialogueState);}
            if(hit(10,10,"any")){healingPool(gp.dialogueState);}
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
    public boolean hit(int x,int y,int width, int height, String reqDirection){
        boolean hit = false;
        Rectangle rect = new Rectangle(gp.tileSize*x,gp.tileSize*y,gp.tileSize*width,gp.tileSize*height);
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        if(gp.player.solidArea.intersects(rect)){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        rect.x = eventRect[0][0].eventRectDefaultX;
        rect.y = eventRect[0][0].eventRectDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return hit;
    }
    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Vap co va nga";
        gp.player.life -= 1;
//        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }


    public void telemap (int gameState){
        gp.gameState = gameState ;
        gp.ui.currentDialogue = "dich chuyen";
        gp.player.worldX = gp.tileSize * 10;
        gp.player.worldY = gp.tileSize * 13;
    }
    public void healingPool(int gameState){
        if(gp.keyH.enterPressed){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "Ban choi da va hoi mau";
            if(gp.player.life < 6) gp.player.life += 1;
        }
    }
}
