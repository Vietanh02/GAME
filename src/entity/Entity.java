package entity;

import entity.Particle.Particle;
import entity.Projectile.Projectile;
import entity.object.OBJ_Chest;
import entity.object.OBJ_Coin;
import entity.object.OBJ_Heart;
import entity.object.OBJ_ManaCrystal;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public abstract class Entity {
	protected GamePanel gp;
	public BufferedImage[] image = new BufferedImage[4];
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public int speed;
	public int solidAreaDefaultX, solidAreaDefaultY;

	public String direction = "stay";
	public BufferedImage[] stay = new BufferedImage[3];
	public BufferedImage[] up = new BufferedImage[3];
	public BufferedImage[] down = new BufferedImage[3];
	public BufferedImage[] left = new BufferedImage[3];
	public BufferedImage[] right = new BufferedImage[3];

	public boolean alive = true;
	public boolean dying = false;
	protected int dyingCounter = 0;

	public boolean hpOn = false;
	public int hpOnCounter = 0;
	public int spriteCounter = 0;
	public int spriteNum = 1;

	public int shotCounter = 0;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public boolean collisionOn = false;
	public int actionLockCounter = 0;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int type; //	0 = player, 1 = NPC, 2 = monster
	String[] dialogues = new String[20];
	int dialogueIndex = 0;
	public boolean isBoss = false;
	public boolean isTreasure = false;
	public boolean onPath = false;
	//CHARATER STATUS

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	// Now, Time's up, Ure is coming
	// set again after meeting, all the number is not last one


	protected int level;
	protected int nextLevelExp;
	boolean attacking = false;
	public int maxLife;
	public int life;
	public Entity currentWeapon;
	public Entity currentShield;

	public Projectile skillShot;

	protected int def;
	protected int atk;
	protected int damage = 50;
	protected int maxMana ;
	public int mana ;


	protected int normalDef = 10;
	protected int normalAtk = 10;



	protected int EXP;

	public int coin = 0;


	//public int coin = 0;
	protected int skillManaCost = 10;

	// item attributes
	public int attackValue;
	public int defenseValue;
	//type
	//0 = player, 1 = NPC, 2 = monster
	public final int type_player = 0;
	public final int type_NPC = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumnable = 6;

	public final int type_pickupOnly = 7;

	public final int type_Port = 8;

	public int value = 0;
	public int itemValue = 1;

	public Rectangle attackArea = new Rectangle();
	public String description = "";

	// get,set
	public int getLevel() {
		return level;
	}
	public int getEXP() {
		return EXP;
	}
	public int getCoin() {return coin;}

	public int getMaxMana() {
		return maxMana;
	}

	public int getAtk() {
		return atk;
	}

	public int getNormalAtk() {
		return normalAtk;
	}

	public int getDef() {
		return def;
	}

	public int getNormalDef() {
		return normalDef;
	}

	public int getNextLevelExp(){
		return nextLevelExp;
	}

	//GET IMAGE ENTITY (G???I ???NH TH???C TH???)
	public BufferedImage setup(String imagePath, int tileSize, int size){
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		try{
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = uTool.scaleImage(image,image.getWidth()*3,image.getHeight()*3);

		}catch (IOException e){
			e.printStackTrace();
		}
		return image;
	}
	public void draw(Graphics2D g2){
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.titleSize, gp.titleSize);
		BufferedImage image = switch (direction) {
			case "up" -> up[spriteNum - 1];
			case "down" -> down[spriteNum - 1];
			case "left" -> left[spriteNum - 1];
			case "right" -> right[spriteNum - 1];
			case "stay" -> stay[spriteNum - 1];
			default -> null;
		};

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		//

		if (gp.player.screenX > gp.player.worldX){
			screenX = worldX;
		}
		if (gp.player.screenY > gp.player.worldY){
			screenY = worldY;
		}
		int rightOffset = gp.screenWidth -gp.player.screenX;
		if (rightOffset > gp.WorldWidth - gp.player.worldX){
			screenX = gp.screenWidth - (gp.WorldWidth -worldX);
		}
		int bottomOffset = gp.screenHeight -gp.player.screenY;
		if (bottomOffset > gp.WorldHeight - gp.player.worldY){
			screenY = gp.screenHeight - (gp.WorldHeight -worldY);
		}

		if(worldX + gp.tileSize  > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize  < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize  > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize  < gp.player.worldY + gp.player.screenY) {
			if((type == 2 && hpOn)) {
				double oneScale = (double)gp.tileSize/maxLife;
				g2.setColor(Color.BLACK);
				g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,7);
				g2.setColor(Color.red);
				g2.fillRect(screenX,screenY-15, (int) (oneScale*life),5);
				hpOnCounter++;
				if(hpOnCounter > 600) {
					hpOn = false;
					hpOnCounter = 0;
				}
			}
			if(invincible) {
				hpOn = true;
				hpOnCounter = 0;
				changeAlpha(g2,0.4f);
			}
			if(dying) dyingAnimation(g2);
			g2.drawImage(image, screenX, screenY,null);
			changeAlpha(g2,1f);

		}
		else if (gp.player.screenX > gp.player.worldX ||
				gp.player.screenY > gp.player.worldY ||
				rightOffset > gp.WorldWidth - gp.player.worldX ||
				bottomOffset > gp.WorldHeight - gp.player.worldY){
			if((type == 2 && hpOn)) {
				double oneScale = (double)gp.tileSize/maxLife;
				g2.setColor(Color.BLACK);
				g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,7);
				g2.setColor(Color.red);
				g2.fillRect(screenX,screenY-15, (int) (oneScale*life),5);
				hpOnCounter++;

				if(hpOnCounter > 600) {
					hpOn = false;
					hpOnCounter = 0;
				}
			}
			if(invincible) {
				hpOn = true;
				hpOnCounter = 0;
				changeAlpha(g2,0.4f);
			}
			if(dying) dyingAnimation(g2);
			g2.drawImage(image, screenX, screenY,null);
			changeAlpha(g2,1f);
		}
	}

	protected void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		if(dyingCounter <= 5){changeAlpha(g2,0f);
		} else if(dyingCounter <= 10) {changeAlpha(g2,1f);
		} else if(dyingCounter <= 15) {changeAlpha(g2,0f);
		} else if(dyingCounter <= 20) {changeAlpha(g2,1f);
		} else if(dyingCounter <= 25) {changeAlpha(g2,0f);
		} else if(dyingCounter <= 30) {changeAlpha(g2,1f);
		} else if(dyingCounter <= 35) {changeAlpha(g2,0f);
		} else if(dyingCounter <= 40) {changeAlpha(g2,1f);
		} else {dying = false;alive = false;}
	}
	public void changeAlpha(Graphics2D g2,float alpha){
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
	}
	public void update() {
		setAction();
		collision = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.NPC);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		checkCollision();
		//	IF COLLISION IS FALSE, PLAYER CAN MOVE
		if (!collision) {
			switch (direction) {
				case "up" -> worldY -= speed;
				case "down" -> worldY += speed;
				case "left" -> worldX -= speed;
				case "right" -> worldX += speed;
			}
		}
		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 3;
			} else if (spriteNum == 3) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		if(shotCounter < 50){
			shotCounter++;
		}
	}
	public void damagePlayer(int atk){
		if(!gp.player.invincible){
			//	player can give damage
			int damage = atk - gp.player.def;
			if(damage < 0) damage = 0;
			gp.player.life -= damage;
			gp.player.invincible = true;
		}
	}
	public void setAction(){
	}
	public void damageReaction(){

	}
	public void speak(){
		if(dialogues[dialogueIndex] == null) dialogueIndex = 0;
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		switch (gp.player.direction) {
			case "up" -> direction = "down";
			case "down" -> direction = "up";
			case "left" -> direction = "right";
			case "right" -> direction = "left";
			case "stay" -> direction = "stay";
		}
	}

	protected void use(Entity entity) {
	}

	public void dropItem(Entity droppedItem){
		for(int i = 0; i < gp.obj.length; i++){
			if(gp.obj[i] == null){
				gp.obj[i] = droppedItem;
				gp.obj[i].worldX = worldX;
				gp.obj[i].worldY = worldY;
				break;
			}
		}
	}

	public void checkDrop() {
		int i = new Random().nextInt(100)+1;
		if(i < 25){
			dropItem(new OBJ_Heart(gp));
		}
		if(i < 50) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
		if(i < 75){
			dropItem(new OBJ_Coin(gp));
		}
		if(isBoss){
			dropItem(new OBJ_Chest(gp));
		}
	}

	public Color getParticleColor(){
		return null;
	}
	public int getParticleSize(){
		return 0;
	}
	public int getParticleSpeed(){
		return 0;
	}
	public int getParticleMaxLife(){
		return 0;
	}
	public void genarateParticle(Entity generator, Entity target){
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
//		Color color = target.getParticleColor();
//		int size = target.getParticleSize();
//		int speed = target.getParticleSpeed();
//		int maxLife = target.getParticleMaxLife();
		Particle p1 = new Particle(gp, generator,color,size,speed,maxLife,-1,-1);
		Particle p2 = new Particle(gp, generator,color,size,speed,maxLife,1,-1);
		Particle p3 = new Particle(gp, generator,color,size,speed,maxLife,-1,1);
		Particle p4 = new Particle(gp, generator,color,size,speed,maxLife,1,1);
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
	}
	public void checkCollision(){
		collision = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.NPC);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if(this.type == 2  && contactPlayer){
			damagePlayer(atk);
		}
	}
	public void searchPath(int goalCol, int goalRow){
		int startCol = (worldX + solidArea.x)/gp.tileSize;
		int startRow = (worldY + solidArea.y)/gp.tileSize;
		gp.pathFinder.setNodes(startCol,startRow,goalCol,goalRow,this);
		if(gp.pathFinder.search()){
			int nextX = gp.pathFinder.pathList.get(0).col*gp.tileSize;
			int nextY = gp.pathFinder.pathList.get(0).row*gp.tileSize;

			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x +solidArea.width;

			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y+solidArea.height;

			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
				direction = "up";
			} else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "down";
			} else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				if(enLeftX > nextX) {
					direction = "left";
				}
				if(enLeftX < nextX){
					direction = "right";
				}
			} else if (enTopY > nextY && enLeftX > nextX) {
				direction = "up";
				checkCollision();
				if(collision){
					direction = "left";
				}
			} else if (enTopY > nextY && enLeftX < nextX) {
				direction = "up";
				checkCollision();
				if(collision){
					direction= "right";
				}
			} else if (enTopY < nextY && enLeftX > nextX) {
				direction = "down";
				checkCollision();
				if(collision){
					direction= "left";
				}
			} else if (enTopY < nextY && enLeftX < nextX) {
				direction = "down";
				checkCollision();
				if (collision) {
					direction = "right";
				}
			}
			int nextCol = gp.pathFinder.pathList.get(0).col;
			int nextRow = gp.pathFinder.pathList.get(0).row;
			if(nextCol == goalCol && nextRow == goalRow){
				onPath = false;
			}
		}
	}
}
