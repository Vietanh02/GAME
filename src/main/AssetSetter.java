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
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 38*gp.tileSize;
        gp.obj[0].worldY = 52*gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 42*gp.tileSize;
        gp.obj[2].worldY = 52*gp.tileSize;

        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = 40*gp.tileSize;
        gp.obj[1].worldY = 40*gp.tileSize;

        gp.obj[3] = new OBJ_Teleport(gp);
        gp.obj[3].worldX = 40*gp.tileSize;
        gp.obj[3].worldY = 36*gp.tileSize;

        gp.obj[4] = new OBJ_AXE(gp);
        gp.obj[4].worldX = 56*gp.tileSize;
        gp.obj[4].worldY = 56*gp.tileSize;

        gp.obj[5] = new OBJ_Blue_Shield(gp);
        gp.obj[5].worldX = 58*gp.tileSize;
        gp.obj[5].worldY = 54*gp.tileSize;

        gp.obj[6] = new OBJ_Potion_Red(gp);
        gp.obj[6].worldX = 48*gp.tileSize;
        gp.obj[6].worldY = 48*gp.tileSize;

        gp.obj[7] = new OBJ_Shield_Wood(gp);
        gp.obj[7].worldX = 60*gp.tileSize;
        gp.obj[7].worldY = 54*gp.tileSize;

        gp.obj[8] = new OBJ_Sword_Normal(gp);
        gp.obj[8].worldX = 52*gp.tileSize;
        gp.obj[8].worldY = 48*gp.tileSize;

        gp.obj[10] = new OBJ_Coin(gp);
        gp.obj[10].worldX = 48*gp.tileSize;
        gp.obj[10].worldY = 46*gp.tileSize;

        gp.obj[9] = new OBJ_Coin(gp);
        gp.obj[9].worldX = 48*gp.tileSize;
        gp.obj[9].worldY = 47*gp.tileSize;

        gp.obj[11] = new OBJ_Heart(gp);
        gp.obj[11].worldX = 48*gp.tileSize;
        gp.obj[11].worldY = 51*gp.tileSize;

        gp.obj[12] = new OBJ_ManaCrystal(gp);
        gp.obj[12].worldX = 48*gp.tileSize;
        gp.obj[12].worldY = 49*gp.tileSize;

    }
    public void setNPC(){
        gp.NPC[0] = new NPC_OldMan(gp);
        gp.NPC[0].worldX = 5*gp.tileSize;
        gp.NPC[0].worldY = 10*gp.tileSize;

        gp.NPC[0] = new NPC_Fairy(gp);
        gp.NPC[0].worldX = 31*gp.tileSize;
        gp.NPC[0].worldY = 20*gp.tileSize;
    }
    public void setMonster(){

        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = 48*gp.tileSize;
        gp.monster[0].worldY = 40*gp.tileSize;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = 46*gp.tileSize;
        gp.monster[1].worldY = 42*gp.tileSize;

        gp.monster[2] = new MON_GreenSlime(gp);
        gp.monster[2].worldX = 50*gp.tileSize;
        gp.monster[2].worldY = 42*gp.tileSize;

        gp.monster[3] = new MON_GreenSlime(gp);
        gp.monster[3].worldX = 46*gp.tileSize;
        gp.monster[3].worldY = 50*gp.tileSize;

        gp.monster[4] = new MON_GreenSlime(gp);
        gp.monster[4].worldX = 48*gp.tileSize;
        gp.monster[4].worldY = 56*gp.tileSize;

        gp.monster[5] = new Mob_Catgirl(gp);
        gp.monster[5].worldX = 46*gp.tileSize;
        gp.monster[5].worldY = 52*gp.tileSize;

        gp.monster[6] = new Mob_Pig(gp);
        gp.monster[6].worldX = 10*gp.tileSize;
        gp.monster[6].worldY = 14*gp.tileSize;

    }
}