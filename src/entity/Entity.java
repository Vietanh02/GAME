package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	public int worldX,worldY;
	public int speed;
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
	// khi đi sang trái thì có 8 ảnh thay nhau đc vẽ trong 1 s
	// tương tự với phải 
	// đứng yên có 4 ảnh 
	public BufferedImage[] left = new BufferedImage[8], right = new BufferedImage[8] ;
	public BufferedImage[] stay = new BufferedImage[4];
	                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	public String direction;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea;
	public boolean collisionOn = false;
}
