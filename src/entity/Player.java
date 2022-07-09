package entity;


import entity.object.OBJ_Key;
import entity.object.OBJ_Shield_Wood;
import entity.object.OBJ_Sword_Normal;
import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity{
	public BufferedImage[] stay = new BufferedImage[3];
	private final BufferedImage[] up = new BufferedImage[3];
	private final BufferedImage[] down = new BufferedImage[3];
	private final BufferedImage[] left = new BufferedImage[3];
	private final BufferedImage[] right = new BufferedImage[3];

	private final BufferedImage[] attackUp = new BufferedImage[4];
	private final BufferedImage[] attackDown = new BufferedImage[4];
	private final BufferedImage[] attackLeft = new BufferedImage[4];
	private final BufferedImage[] attackRight = new BufferedImage[4];

	KeyHandler keyH;

	public int hasKey = 0;
	public final int screenX;
	public final int screenY;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxinventorySize = 48;




	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);

		// COLLISION AREA FOR PLAYER 32x32
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.height = 32;
		solidArea.width = 32;

//		attackArea.width = 36;
//		attackArea.height = 48;
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}

	public void setDefaultValues() {
		worldX = gp.tileSize*50;
		worldY = gp.tileSize*40;
		speed = 4;
		direction = "up";
		//player status
		maxLife = 6;
		life = maxLife;
		level = 1;
		nextLevelExp = 5;
		currentShield = new OBJ_Shield_Wood(gp);
		currentWeapon = new OBJ_Sword_Normal(gp);
		attack = getDamage();
		defense = getDef();

	}
	public void setItems(){

		inventory.add(currentShield);
		inventory.add(currentWeapon);
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Key(gp));






	}

	public int getAttack(){
		attackArea = currentWeapon.attackArea;
		return attack = damage + currentWeapon.attackValue;
	}
	public int getDefense(){ return defense = def + currentShield.defenseValue;}

	public void getPlayerImage() {
			// tải các ảnh vảo mảng

			String imageDown = "/player/boy_down_%d";
			String imageUp = "/player/boy_up_%d";
			String imageLeft = "/player/boy_left_%d";
			String imageRight = "/player/boy_right_%d";
			String imageStay = "/player/boy_down_%d";
			for(int i=0;i<3;i++) {
				stay[i] = setup(String.format(imageStay,i+1), gp.tileSize, gp.tileSize);
				up[i]   = setup(String.format(imageUp,i+1), gp.tileSize, gp.tileSize);
				down[i] = setup(String.format(imageDown,i+1), gp.tileSize, gp.tileSize);
				left[i] = setup(String.format(imageLeft,i+1), gp.tileSize, gp.tileSize);
				right[i] = setup(String.format(imageRight,i+1), gp.tileSize, gp.tileSize);
			}
	}
	//Lấy ảnh tấn công
	public void getPlayerAttackImage(){

		String atkDown = "/player/attack_down_1";
		String atkUp = "/player/attack_up_1";
		String atkLeft = "/player/attack_left_1";
		String atkRight = "/player/attack_right_1";
		for(int i=0;i<3;i++) {
			attackUp[i]   = setup(atkUp, gp.tileSize, gp.tileSize);
			attackDown[i] = setup(atkDown, gp.tileSize, gp.tileSize);
			attackLeft[i] = setup(atkLeft, gp.tileSize, gp.tileSize);
			attackRight[i] = setup(atkRight, gp.tileSize, gp.tileSize);
		}
	}

	public void update() {
		if(attacking){
			attacking();
		}
		else if(keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed || keyH.enterPressed){
			if(keyH.upPressed) {
				direction = "up";
			}
			else if(keyH.downPressed) {
				direction = "down";
			}
			else if(keyH.leftPressed) {
				direction = "left";
			}
			else if(keyH.rightPressed) {
				direction = "right";
//			}else {
//				direction = "stay";
			}
			//	CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			//	CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			//	CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.NPC);
			interactNPC(npcIndex);
			gp.eHandler.checkEvent();
			gp.cChecker.checkEntity(this, gp.monster);
			//	CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			//IF COLLISION IS FALSE PLAYER CAN MOVE
			if(!collisionOn && !keyH.enterPressed) {
				if(keyH.upPressed && keyH.leftPressed){
					worldY -= speed;
					worldX -= speed;
				}
				else if(keyH.upPressed && keyH.rightPressed){
					worldY -= speed;
					worldX += speed;
				}
				else if(keyH.downPressed && keyH.leftPressed){
					worldY += speed;
					worldX -= speed;
				}
				else if(keyH.downPressed && keyH.rightPressed){
					worldY += speed;
					worldX += speed;
				}
				else if(keyH.upPressed) {
					worldY -= speed;
				}
				else if(keyH.downPressed) {
					worldY += speed;
				}
				else if(keyH.leftPressed) {
					worldX -= speed;
				}
				else if(keyH.rightPressed) {
					worldX += speed;
				}
			}
			gp.keyH.enterPressed = false;
			// spriteNum để vẽ nhân vật  ở đây vẽ 8 bức 1s sẽ tạo đc chuyển động nhân vật
			spriteCounter++;
			if(spriteCounter>8) {
				if(spriteNum == 1) {
					spriteNum =2;
				}
				else if(spriteNum ==2) {
					spriteNum = 3;
				}
				else if(spriteNum ==3) {
					spriteNum = 1;
				}
				spriteCounter = 0;

			}
		}

		//	This needs to be outside of the key if statement
		if(invincible == true){
			invincibleCounter ++;
			if(invincibleCounter > 60){
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	//attack
	private void attacking() {
		spriteCounter++;
		if(spriteCounter <= 5){
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter < 25){
			spriteNum = 2;
			//luu worldX,Y, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;

			switch (direction){
				case "up": worldY -= attackArea.height;
				case "down": worldY += attackArea.height;
				case "left": worldX -= attackArea.width;
				case "right": worldX += attackArea.width;
			}

			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;

			int monIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monIndex);
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter >= 25){
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}

	public void damageMonster(int i){
		if(i!= 999){
			if(!gp.monster[i].invicible) {
				//gp.playSE(index);

				damage = atk - gp.monster[i].def;
				if(damage <= 0) damage = 0;
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage+" damage!");
				gp.monster[i].invicible = true;
				gp.monster[i].damageReaction();

				if(gp.monster[i].life == 0){
					gp.monster[i].dying = true;
					gp.ui.addMessage("Killed the "+gp.monster[i].name+"!");
					gp.ui.addMessage("Gained "+gp.monster[i].EXP+" exp!");
					EXP += gp.monster[i].EXP;
					checkLevelUp(EXP);
				}
			}
		}
	}

	private void checkLevelUp(int exp) {
		if(exp >= nextLevelExp){
			level++;
			nextLevelExp*=2;
			maxLife+=2;
			life = maxLife;
			str++;
			dex++;
			atk = getDamage();
			def = getDef();
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "Level up! You are level "+level+" now!\n You become stronger";
		}
	}

	public void sellectItem(){

		int itemIndex = gp.ui.getItemIndexOnSlot();

		if(itemIndex< inventory.size()){

			Entity selectedItem = inventory.get(itemIndex);
			if(selectedItem.type == type_sword || selectedItem.type == type_axe){
				currentWeapon = selectedItem;
				attack = getAttack();
			}
			if (selectedItem.type == type_shield){
				currentShield = selectedItem;
				defense = getDefense();
			}
			if (selectedItem.type == type_consumnable){
					selectedItem.use(this);
					inventory.remove(itemIndex);
			}
		}

	}

	public void pickUpObject(int i){
		if(i!=999) {

			String text = "";
			if (inventory.size() != maxinventorySize ){
				if(gp.obj[i].value == 1) {
					inventory.add(gp.obj[i]);
					gp.playSE(2);
					text = "Got a " + gp.obj[i].name + "!";
				}
			}
		 	else {
			text = "Inventory is full"+"\n"+"You can't carry any more!!!";
			}

			gp.ui.addMessage(text);
			gp.obj[i] = null;
		}
	}

	public void contactMonster(int i){

		if(i!= 999){
			if(invincible == false){
				life -= 1;
				invincible = true;
			}
		}
	}

	//hàm draw để vẽ hình của nhân vật khi di chuyển hoặc đứng yên
	//vẽ hình nhận vật tấn công, kích  2x1 ô
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.titleSize, gp.titleSize);
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch (direction) {
			case "up" -> {
				if (!attacking) image = up[spriteNum - 1];
				if (attacking){
					tempScreenY -= gp.tileSize;
					image = attackUp[spriteNum - 1];
				}
			}
			case "down" -> {
				if (!attacking) image = down[spriteNum - 1];
				if (attacking) image = attackDown[spriteNum - 1];
			}
			case "left" -> {
				if (!attacking) image = left[spriteNum - 1];
				if (attacking){
					tempScreenX -= gp.tileSize;
					image = attackLeft[spriteNum - 1];
				}
			}
			case "right" -> {
				if (!attacking) image = right[spriteNum - 1];
				if (attacking) image = attackRight[spriteNum - 1];
			}
			case "stay" -> image = stay[spriteNum - 1];
		}

		int x = screenX;
		int y = screenY;

		if (screenX > worldX) {
			x = worldX;
		}
		if (screenY > worldY) {
			y = worldY;
		}
		int rightOffset = gp.screenWidth -screenX;
		if (rightOffset > gp.WorldWidth - worldX){
			x = gp.screenWidth - (gp.WorldWidth -worldX);
		}
		int bottomOffset = gp.screenHeight -screenY;
		if (bottomOffset > gp.WorldHeight - worldY){
			y = gp.screenHeight - (gp.WorldHeight -worldY);
		}

		if(invincible == true){
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);

		// reset alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invicible: "+ invincibleCounter,10,400);
	}

	public BufferedImage getDownImage(){
		return this.down[0];
	}
	public void interactNPC(int i){
		if(gp.keyH.enterPressed){
			if(i!=999){
					gp.gameState = gp.dialogueState;
					gp.NPC[i].speak();
				}
			if(i == 999){
				//gp.playSE(index);
				attacking = true;
			}
		}
	}
}
