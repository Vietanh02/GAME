package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public boolean collisionOn = false;
	public int actionLockCounter = 0;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int type; //	0 = player, 1 = NPC, 2 = monster
	String dialogues[] = new String[20];
	int dialogueIndex = 0;

	//CHARATER STATUS

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	// Now, Time's up, Ure is coming
	// set again after meeting, all the number is not last one
	protected boolean die = false;

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
	public float life;
	protected int def = 10;
	protected int damage = 50;

	protected int maxMana = 100;
	protected int mana = 100;

	protected int EXP;
	protected int coin = 0;

	protected int attackManaCost = 4;
	protected int skillManaCost = 10;
	// chưa viết được hàm skill

	// get,set
	public boolean getDeath() {
		return die;
	}

	public void setDeath(boolean death) {
		this.die = death;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDef() {
		return def;
	}

	public int getEXP() {
		return EXP;
	}

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

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setDefense(int def) {
		this.def = def;
	}

	public Entity() {
	}

	//	Hàm tính lượng dame nhận
	public int damageCal(Entity entity) {
		if (damage - entity.getDef() <= 0)
			return 1;
		return damage - entity.getDef();
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
	public BufferedImage setup(String imagePath){
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		try{
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = uTool.scaleImage(image,gp.tileSize,gp.tileSize);
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

			g2.drawImage(image, screenX, screenY, null);

		}
		else if (gp.player.screenX > gp.player.worldX ||
				gp.player.screenY > gp.player.worldY ||
				rightOffset > gp.WorldWidth - gp.player.worldX ||
				bottomOffset > gp.WorldHeight - gp.player.worldY){
			g2.drawImage(image, screenX, screenY,gp.tileSize, gp.tileSize, null);
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

		if(this.type == 2 && contactPlayer == true){
			if(gp.player.invincible == false){
				//	player can give damage

				gp.player.life -=1;
				gp.player.invincible = true;
			}
		}

		//	IF COLLISION IS FALSE, PLAYER CAN MOVE
		if (collision = false) {
			switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
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
	}
	public void setAction(){
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
}
