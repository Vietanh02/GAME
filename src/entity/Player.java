package entity;



import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	GamePanel gp;
	KeyHandler keyH;

	public int hasKey = 0;
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		// COLLISION AREA FOR PLAYER 32x32
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.height = 16;
		solidArea.width = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize*7;
		worldY = gp.tileSize*7;
		speed = 4;
		direction = "up";
		
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/Layer 1_birdFly1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/Layer 1_birdFly2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/Layer 1_birdFly3.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/Layer 1_birdFly4.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/Layer 1_birdFly5.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/Layer 1_birdFly6.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/Layer 1_birdFly7.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/Layer 1_birdFly8.png"));
			
			// tải các ảnh vảo mảng 
			String imageLeft = new String("/player/bird_left_fly%d.png");
			String imageRight = new String("/player/bird_right_fly%d.png");
			String imageStay = new String("/player/bird_stay_%d.png");
			for(int i=0;i<4;i++) {
				stay[i] = ImageIO.read(getClass().getResourceAsStream(String.format(imageStay,i+1)));
			}
			for(int i=0;i<8;i++) {
				left[i] = ImageIO.read(getClass().getResourceAsStream(String.format(imageLeft, i+1)));
				right[i] = ImageIO.read(getClass().getResourceAsStream(String.format(imageRight, i+1)));
			}
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if(keyH.upPressed == true) {
			direction = "up";
		}
		else if(keyH.downPressed == true) {
			direction = "down";
		}
		else if(keyH.leftPressed == true) {
			direction = "left";
		}
		else if(keyH.rightPressed == true) {
			direction = "right";
		}else {
			direction = "stay";
		}
		
		//CHECK TILE COLLISION
		collisionOn = false;
		gp.cChecker.checkTile(this);
		//IF COLLISION IS FALSE PLAYER CAN MOVE
		if(collisionOn == false) {
			switch(direction) {
			case "stay":              break;
			case "up": worldY-=speed; break;
			case "down": worldY+=speed; break;
			case "left": worldX-=speed; break;
			case "right": worldX+=speed; break;
			}
		}
		
		// spriteNum để vẽ nhân vật  ở đây vẽ 8 bức 1s sẽ tạo đc chuyển động vỗ cánh
		spriteCounter++;
		if(spriteCounter>8) {
			if(spriteNum == 1) {
				spriteNum =2;
			}
			else if(spriteNum ==2) {
				spriteNum = 3;
			}
			else if(spriteNum ==3) {
				spriteNum = 4;
			}
			else if(spriteNum ==4) {
				spriteNum = 5;
			}
			else if(spriteNum ==5) {
				spriteNum = 6;
			}
			else if(spriteNum ==6) {
				spriteNum = 7;
			}
			else if(spriteNum ==7) {
				spriteNum = 8;
			}
			else if(spriteNum ==8) {
				spriteNum = 1;
			}
			spriteCounter = 0;
			
		}
	}
	
	//hàm draw để vẽ hình của nhân vật khi di chuyển hoặc đứng yên
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.titleSize, gp.titleSize);
		BufferedImage image = switch (direction) {
			case "up" -> left[spriteNum - 1];
			case "down" -> left[spriteNum - 1];
			case "left" -> left[spriteNum - 1];
			case "right" -> right[spriteNum - 1];
			case "stay" -> stay[(spriteNum - 1) / 2];
			default -> null;
		};

		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);
	}
}
