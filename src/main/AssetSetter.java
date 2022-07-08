package main;

//import entity.NPC_LittleRed;
import entity.NPC_OldMan;
import entity.monster.MON_GreenSlime;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

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

    }
}
