package entity;

import entity.Projectile.Projectile;
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

	//CHARATER STATUS

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	// Now, Time's up, Ure is coming
	// set again after meeting, all the number is not last one


	protected int level;
	protected int nextLevelExp;
	protected boolean die = false;
	boolean attacking = false;
	protected int attackSpeed = 1000;
	protected int attackDelay = 500;
	protected boolean attackAbility = true;
	protected boolean isAttacking = false;
	protected double attacktime;

	protected int skillSpeed = 1000;
	protected int skillDelay = 500;
	protected long skillStartTime;
	protected double skillTime;
	protected boolean skillAbility = true;
	protected boolean isSkilling = false;

	protected int maxHealth = 100;
	protected int health = 100;
	public int maxLife;
	public int life;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public Entity currentWeapon;
	public Entity currentShield;

	public Projectile skillShot;

	protected int def = 10;
	protected int atk = 10;
	protected int damage = 50;
	protected int str;
	protected int dex;
	protected int inte;
	public int maxMana = 100;
	public int mana = 100;


	protected int normalDef = 10;
	protected int normalAtk = 10;



	protected int EXP;

	public int coin = 0;


	//public int coin = 0;

	protected int attackManaCost = 4;
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

	public int value = 0;
	public int itemValue = 1;

	public Rectangle attackArea = new Rectangle();
	public String description = "";
	// chưa viết được hàm skill

	// get,set
	public boolean getDeath() {
		return die;
	}

	public void setDeath(boolean death) {
		this.die = death;
	}

	public int getLevel() {
		return level;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}


	public int getEXP() {
		return EXP;
	}
	public int getCoin() {return coin;}

	public void setEXP(int EXP) {
		this.EXP = EXP;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getMana() {
		return mana;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getNormalAtk() {
		return normalDef;
	}
	public void setNormalAtk(int normalAtk){
		this.normalAtk = normalAtk;
	}

	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}

	public int getNormalDef() {
		return normalDef;
	}
	public void setNormalDef(int normalDef){
		this.normalDef = normalDef;
	}

	public int getNextLevelExp(){
		return nextLevelExp;
	}

	public Entity() {
	}

	//	Hàm tính lượng dame nhận
	public int damageCal(Entity entity) {
		if (atk - entity.getDef() <= 0)
			return 1;
		return atk - entity.getDef();
	}

	//	Hàm cập nhật trạng thái tấn công
	protected boolean isAttacking(double time) {
		if ((attackDelay / 1000000) > ((time / 1000000) - attackSpeed) || (this.mana - attackManaCost) <= 0) {
			attackAbility = false;
		} else attackAbility = true;
		if ((attacktime / 1000000) + attackDelay > (time / 1000000) && (this.mana - attackManaCost) >= 0) {
			return true;
		}
		return false;
	}

	protected boolean isSkilling(double time) {
		if ((skillTime / 1000000) > ((time / 1000000) - skillSpeed) || (this.mana - skillManaCost) <= 0) {
			skillAbility = false;
		} else skillAbility = true;
		if ((skillTime / 1000000) + skillDelay > (time / 1000000) && (this.mana - skillManaCost) >= 0) {
			return true;
		}
		return false;
	}
	//GET IMAGE ENTITY (GỌI ẢNH THỰC THỂ)
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

	private void dyingAnimation(Graphics2D g2) {
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

	public void draw(Graphics2D g2,int width,int height){
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
			if(invincible) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
			g2.drawImage(image, screenX, screenY,width,height,null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

		}
		else if (gp.player.screenX > gp.player.worldX ||
				gp.player.screenY > gp.player.worldY ||
				rightOffset > gp.WorldWidth - gp.player.worldX ||
				bottomOffset > gp.WorldHeight - gp.player.worldY){
			if(invincible) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
			g2.drawImage(image, screenX, screenY,width,height,null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		}
	}
	public void update() {
		setAction();
		collision = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.NPC);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if(this.type == 2 && contactPlayer){
			damagePlayer(atk);
		}

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
	}
}
