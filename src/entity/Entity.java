package entity;

import main.GamePanel;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {

	GamePanel gp;
	public int worldX,worldY;
	public int speed;
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
	// khi đi sang trái thì có 8 ảnh thay nhau đc vẽ trong 1 s
	// tương tự với phải
	// đứng yên có 4 ảnh
	public BufferedImage[] up = new BufferedImage[3], down = new BufferedImage[3] ;
	public BufferedImage[] left = new BufferedImage[3], right = new BufferedImage[3] ;
	public BufferedImage[] stay = new BufferedImage[3];

	public int solidAreaDefaultX, solidAreaDefaultY;

	public String direction;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea;
	public boolean collisionOn = false;

	//CHARATER STATUS

	public Entity(GamePanel gp) {
		this.gp =gp;
	}

	// Now, Time's up, Ure is coming
	// set again after meeting, all the number is not last one

	protected boolean die = false;

	protected int attackSpeed = 1000;
	protected int attackDelay = 500;
	protected boolean attackAbility = true;
	protected boolean isAttacking = false;

	protected int skillSpeed = 1000;
	protected int skillDelay = 500;
	protected long skillStartTime;
	protected double skillTime;
	protected boolean skillAbility = true;
	protected boolean isSkilling = false;

	protected int maxHealth= 100 ;
	protected int health = 100 ;
	public int maxLife;
	public float life;
	protected int def = 10;
	protected int damage= 50;

	protected int maxMana = 100;
	protected int mana = 100;

	protected int EXP;
	protected int coin = 0;

	protected int attackManaCost = 4;
	protected int skillManaCost = 10;
	// chưa viết được hàm skill

	// get,set
	public boolean getDeath() { return die; }
	public void setDeath(boolean death){this.die = death;}
	public int getHealth() { return health; }
	public void setHealth(int health){ this.health = health;}
	public int getDef() { return def; }
	public int getEXP(){ return EXP;}
	public void setEXP(int EXP){ this.EXP= EXP;}
	public int getMaxMana() {return maxMana;}
	public void setMaxMana(int maxMana) {this.maxMana = maxMana;}
	public int getMana() {return mana;}
	public int getDamage() { return damage;}
	public void setDamage(int damage) {this.damage=damage;}
	public void setDefense(int def) {this.def=def;}

	public Entity(){}

	public int damageCal(Entity entity){
		if(damage - entity.getDef() <= 0)
			return 1;
		return damage - entity.getDef();
	}






}
