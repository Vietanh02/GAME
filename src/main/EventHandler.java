package main;

import java.awt.*;


public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];
//
 
    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col ++;
            if (col == gp.maxWorldCol){
                col = 0;
                row ++;
            }

        }
    }
    public void checkEvent(){
        if (hit(2,6, "any") == true ) {damagePit(2,6,gp.dialogueState);}
        if (hit(5,8,"any")== true) {telemap(5,8,gp.dialogueState);}
    }

    public boolean hit(int col, int row, String reqDirection){
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[row][col])){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int col, int row, int gameState){
        gp.gameState = gameState;
        gp.player.life -= 1 ;
    }

    public void telemap (int col, int row, int gameState){
        gp.gameState =gameState ;
        gp.player.worldX = gp.tileSize * 12;
        gp.player.worldY = gp.tileSize * 13;
    }

}
