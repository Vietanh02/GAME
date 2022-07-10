package main;

//import entity.NPC_LittleRed;
import entity.InteractiveTile.IT_Tree;
import entity.InteractiveTile.InteractiveTile;
import entity.NPC_Fairy;
import entity.NPC_OldMan;
import entity.monster.Boss_DarkLord;
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
        gp.obj[0].worldX = 29*gp.tileSize;
        gp.obj[0].worldY = 50*gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 42*gp.tileSize;
        gp.obj[2].worldY = 52*gp.tileSize;

        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = 40*gp.tileSize;
        gp.obj[1].worldY = 40*gp.tileSize;

        gp.obj[3] = new OBJ_Teleport(gp);
        gp.obj[3].worldX = 50*gp.tileSize;
        gp.obj[3].worldY = 50*gp.tileSize;

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
        gp.obj[8].worldX = 62*gp.tileSize;
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

        gp.obj[13] = new OBJ_Teleport(gp);
        gp.obj[13].worldX = 110*gp.tileSize;
        gp.obj[13].worldY = 110*gp.tileSize;
    }
    public void setNPC(){
        gp.NPC[0] = new NPC_OldMan(gp);
        gp.NPC[0].worldX = 30*gp.tileSize;
        gp.NPC[0].worldY = 42*gp.tileSize;

        gp.NPC[1] = new NPC_Fairy(gp);
        gp.NPC[1].worldX = 31*gp.tileSize;
        gp.NPC[1].worldY = 20*gp.tileSize;

        gp.NPC[2] = new NPC_OldMan(gp);
        gp.NPC[2].worldX = 44*gp.tileSize;
        gp.NPC[2].worldY = 57*gp.tileSize;

        gp.NPC[3] = new NPC_OldMan(gp);
        gp.NPC[3].worldX = 78*gp.tileSize;
        gp.NPC[3].worldY = 55*gp.tileSize;

        gp.NPC[4] = new NPC_OldMan(gp);
        gp.NPC[4].worldX = 99*gp.tileSize;
        gp.NPC[4].worldY = 59*gp.tileSize;

        gp.NPC[5] = new NPC_OldMan(gp);
        gp.NPC[5].worldX = 55*gp.tileSize;
        gp.NPC[5].worldY = 89*gp.tileSize;

        gp.NPC[6] = new NPC_OldMan(gp);
        gp.NPC[6].worldX = 50*gp.tileSize;
        gp.NPC[6].worldY = 37*gp.tileSize;
    }
    public void setMonster(){

        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = 14*gp.tileSize;
        gp.monster[0].worldY = 33*gp.tileSize;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = 56*gp.tileSize;
        gp.monster[1].worldY = 15*gp.tileSize;

        gp.monster[2] = new MON_GreenSlime(gp);
        gp.monster[2].worldX = 45*gp.tileSize;
        gp.monster[2].worldY = 42*gp.tileSize;

        gp.monster[3] = new MON_GreenSlime(gp);
        gp.monster[3].worldX = 24*gp.tileSize;
        gp.monster[3].worldY = 50*gp.tileSize;

        gp.monster[4] = new MON_GreenSlime(gp);
        gp.monster[4].worldX = 48*gp.tileSize;
        gp.monster[4].worldY = 67*gp.tileSize;

        gp.monster[5] = new Mob_Catgirl(gp);
        gp.monster[5].worldX = 46*gp.tileSize;
        gp.monster[5].worldY = 89*gp.tileSize;

        gp.monster[6] = new Mob_Pig(gp);
        gp.monster[6].worldX = 23*gp.tileSize;
        gp.monster[6].worldY = 55*gp.tileSize;

        gp.monster[7] = new Mob_Pig(gp);
        gp.monster[7].worldX = 66*gp.tileSize;
        gp.monster[7].worldY = 36*gp.tileSize;

        gp.monster[8] = new MON_GreenSlime(gp);
        gp.monster[8].worldX = 78*gp.tileSize;
        gp.monster[8].worldY = 54*gp.tileSize;

        gp.monster[9] = new MON_GreenSlime(gp);
        gp.monster[9].worldX = 67*gp.tileSize;
        gp.monster[9].worldY = 48*gp.tileSize;

        gp.monster[10] = new MON_GreenSlime(gp);
        gp.monster[10].worldX = 99*gp.tileSize;
        gp.monster[10].worldY = 59*gp.tileSize;

        gp.monster[11] = new Mob_Pig(gp);
        gp.monster[11].worldX = 89*gp.tileSize;
        gp.monster[11].worldY = 102*gp.tileSize;

        gp.monster[12] = new Mob_Catgirl(gp);
        gp.monster[12].worldX = 63*gp.tileSize;
        gp.monster[12].worldY = 44*gp.tileSize;

        gp.monster[12] = new Boss_DarkLord(gp);
        gp.monster[12].worldX = 120*gp.tileSize;
        gp.monster[12].worldY = 100*gp.tileSize;

    }
    public void setInteractiveTile(){
        int i = 0;
        gp.iTile[i++] = new IT_Tree(gp,50,47);
        gp.iTile[i++] = new IT_Tree(gp,51,47);
        gp.iTile[i++] = new IT_Tree(gp,52,47);
        gp.iTile[i++] = new IT_Tree(gp,53,47);
        gp.iTile[i++] = new IT_Tree(gp,54,47);
        gp.iTile[i++] = new IT_Tree(gp,55,47);
        gp.iTile[i++] = new IT_Tree(gp,56,47);
        gp.iTile[i++] = new IT_Tree(gp,57,47);
        gp.iTile[i++] = new IT_Tree(gp,50,48);
        gp.iTile[i++] = new IT_Tree(gp,51,48);
        gp.iTile[i++] = new IT_Tree(gp,52,48);
        gp.iTile[i++] = new IT_Tree(gp,53,48);
        gp.iTile[i++] = new IT_Tree(gp,54,48);
        gp.iTile[i++] = new IT_Tree(gp,55,48);
        gp.iTile[i++] = new IT_Tree(gp,56,48);
        gp.iTile[i++] = new IT_Tree(gp,57,48);
        gp.iTile[i++] = new IT_Tree(gp,50,49);
        gp.iTile[i++] = new IT_Tree(gp,51,49);
        gp.iTile[i++] = new IT_Tree(gp,52,49);
        gp.iTile[i++] = new IT_Tree(gp,53,49);
        gp.iTile[i++] = new IT_Tree(gp,54,49);
        gp.iTile[i++] = new IT_Tree(gp,55,49);
        gp.iTile[i++] = new IT_Tree(gp,56,49);
        gp.iTile[i++] = new IT_Tree(gp,57,49);

    }
}