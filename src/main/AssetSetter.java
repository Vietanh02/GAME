package main;

//import entity.NPC_LittleRed;
import entity.NPC_Fairy;
import entity.NPC_OldMan;
import entity.monster.MON_GreenSlime;
import entity.object.*;
import entity.monster.Mob_Pig;
import entity.monster.Mob_Catgirl;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        int i=0;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = 20*gp.tileSize;
        gp.obj[i].worldY = 32*gp.tileSize;
        i++;

        gp.obj[i] = new OBJ_Door(gp);
        gp.obj[i].worldX = 7*gp.tileSize;
        gp.obj[i].worldY = 7*gp.tileSize;
        i++;

        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = 28*gp.tileSize;
        gp.obj[i].worldY = 32*gp.tileSize;
        i++;

        gp.obj[i] = new OBJ_Teleport(gp);
        gp.obj[i].worldX = 40*gp.tileSize;
        gp.obj[i].worldY = 36*gp.tileSize;
        i++;


        gp.obj[i] = new OBJ_AXE(gp);
        gp.obj[i].worldX = 28*gp.tileSize;
        gp.obj[i].worldY = 28*gp.tileSize;
        i++;

        gp.obj[i] = new OBJ_Blue_Shield(gp);
        gp.obj[i].worldX = 32*gp.tileSize;
        gp.obj[i].worldY = 28*gp.tileSize;
        i++;

        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = 32*gp.tileSize;
        gp.obj[i].worldY = 34*gp.tileSize;
        i++;

    }
    public void setNPC(){
        gp.NPC[0] = new NPC_OldMan(gp);
        gp.NPC[0].worldX = 5*gp.tileSize;
        gp.NPC[0].worldY = 10*gp.tileSize;

        gp.NPC[0] = new NPC_Fairy(gp);
        gp.NPC[0].worldX = 20*gp.tileSize;
        gp.NPC[0].worldY = 23*gp.tileSize;
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

        gp.monster[5] = new Mob_Catgirl(gp);
        gp.monster[5].worldX = 31*gp.tileSize;
        gp.monster[5].worldY = 30*gp.tileSize;

        gp.monster[6] = new Mob_Pig(gp);
        gp.monster[6].worldX = 10*gp.tileSize;
        gp.monster[6].worldY = 14*gp.tileSize;



    }
}
