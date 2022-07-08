package main;

//import entity.NPC_LittleRed;
import entity.NPC_OldMan;
import object.*;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 12*gp.tileSize;
        gp.obj[0].worldY = 6*gp.tileSize;

        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = 13*gp.tileSize;
        gp.obj[1].worldY = 13*gp.tileSize;

        gp.obj[2] = new OBJ_Chest(gp);
        gp.obj[2].worldX = 6* gp.tileSize;
        gp.obj[2].worldY = 5*gp.tileSize;

        gp.obj[3] = new OBJ_Port(gp);
        gp.obj[3].worldX = 18* gp.tileSize;
        gp.obj[3].worldY = 18*gp.tileSize;
    }
    public void setNPC(){
        gp.NPC[0] = new NPC_OldMan(this.gp);
        gp.NPC[0].worldX = 5*gp.tileSize;
        gp.NPC[0].worldY = 10*gp.tileSize;

    }
}
