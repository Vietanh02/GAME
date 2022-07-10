package entity.InteractiveTile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_Tree extends InteractiveTile{
    GamePanel gp;
    public IT_Tree(GamePanel gp,int col, int row){
        super(gp,col,row);
        this.gp = gp;
        stay[0] = setup("/objects/drytree", gp.tileSize, gp.tileSize);
        stay[1] = stay[0];
        stay[2] = stay[0];
        life = 3;
        destructible = true;
        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;
    }
    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;
        if(entity.currentWeapon.type == type_axe){
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_Trunk(this.gp,this.worldX/gp.tileSize,this.worldY/gp.tileSize);
        return tile;
    }
    public Color getParticleColor(){
        return new Color(65,50,30);
    }
    public int getParticleSize(){
        return 6;
    }
    public int getParticleSpeed(){
        return 2;
    }
    public int getParticleMaxLife(){
        return 20;
    }
}
