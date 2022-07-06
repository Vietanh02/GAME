package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.text.Utilities;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {

	GamePanel gp;
	public int worldX, worldY;
	public int speed;
	public int solidAreaDefaultX, solidAreaDefaultY;

	public String direction;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea;
	public boolean collisionOn = false;

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
}
