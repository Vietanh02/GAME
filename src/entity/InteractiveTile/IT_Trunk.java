package entity.InteractiveTile;

import entity.Entity;
import main.GamePanel;

public class IT_Trunk extends InteractiveTile{
    GamePanel gp;
    public IT_Trunk(GamePanel gp,int col, int row){
        super(gp,col,row);
        this.gp = gp;
        stay[0] = setup("/objects/trunk", gp.tileSize, gp.tileSize);
        stay[1] = stay[0];
        stay[2] = stay[0];
        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
