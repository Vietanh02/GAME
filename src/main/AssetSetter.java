package main;

//import entity.NPC_LittleRed;
import entity.NPC_OldMan;
import entity.monster.MON_GreenSlime;
import entity.object.OBJ_Door;
import entity.object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 20*gp.tileSize;
        gp.obj[0].worldY = 32*gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 28*gp.tileSize;
        gp.obj[2].worldY = 32*gp.tileSize;

        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = 7*gp.tileSize;
        gp.obj[1].worldY = 7*gp.tileSize;

    }
    public void setNPC(){
        gp.NPC[0] = new NPC_OldMan(gp);
        gp.NPC[0].worldX = 5*gp.tileSize;
        gp.NPC[0].worldY = 10*gp.tileSize;


    }
    public void setMonster(){

        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = 5*gp.tileSize;
        gp.monster[0].worldY = 5*gp.tileSize;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = 17*gp.tileSize;
        gp.monster[1].worldY = 13*gp.tileSize;

        gp.monster[2] = new MON_GreenSlime(gp);
        gp.monster[2].worldX = 50*gp.tileSize;
        gp.monster[2].worldY = 30*gp.tileSize;

        gp.monster[3] = new MON_GreenSlime(gp);
        gp.monster[3].worldX = 35*gp.tileSize;
        gp.monster[3].worldY = 28*gp.tileSize;

        gp.monster[4] = new MON_GreenSlime(gp);
        gp.monster[4].worldX = 35*gp.tileSize;
        gp.monster[4].worldY = 27*gp.tileSize;

        gp.monster[5] = new MON_GreenSlime(gp);
        gp.monster[5].worldX = 70*gp.tileSize;
        gp.monster[5].worldY = 30*gp.tileSize;

    }
}
