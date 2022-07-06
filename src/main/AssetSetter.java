package main;

import entity.NPC_LittleRed;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 12*gp.tileSize;
        gp.obj[0].worldY = 6*gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 6*gp.tileSize;
        gp.obj[1].worldY = 3*gp.tileSize;

        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = 1*gp.tileSize;
        gp.obj[2].worldY = 0*gp.tileSize;

        gp.obj[3] = new OBJ_Chest();
        gp.obj[3].worldX = 6* gp.tileSize;
        gp.obj[3].worldY = 5*gp.tileSize;

    }
    public void setNPC(){
        gp.NPC[0] = new NPC_LittleRed(this.gp);
        gp.NPC[0].worldX = 7*gp.tileSize;
        gp.NPC[0].worldX = 7*gp.tileSize;
    }
}
