package entity.object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin extends Entity {
    GamePanel gp;

    public OBJ_Coin(GamePanel gp){
        super(gp);

        this.gp = gp;

        value = 1;
        type = type_pickupOnly;
        name = "Coin";
        stay[0] = setup("/objects/coin", gp.tileSize, gp.tileSize);
        stay[1] = setup("/objects/coin", gp.tileSize, gp.tileSize);
        stay[2] = setup("/objects/coin", gp.tileSize, gp.tileSize);


    }
    public void use(Entity entity){
        gp.ui.addMessage("Coin + " + value);
        gp.player.coin += value;
    }
}
