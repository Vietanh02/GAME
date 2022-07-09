package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;
    int value1 = 2;

    public OBJ_Potion_Red(GamePanel gp){
        super(gp);

        this.gp = gp;

        value = itemValue;
        type = type_consumnable;
        name = "Red potion";
        stay[0] = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        stay[1] = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        stay[2] = setup("/objects/potion_red", gp.tileSize, gp.tileSize);

        description = "[" + name + "] Heals value: "+ value1 + "\nHeals yours life";
    }
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n"
                                +"Your life has been recovered by " + value1 +".";
        entity.life += value1;
        if(gp.player.life > gp.player.maxLife ){
            gp.player.life = gp.player.maxLife;
        }
        gp.playSE(3);
    }
}
