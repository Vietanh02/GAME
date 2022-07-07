package entity;



import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	KeyHandler keyH;

	public int hasKey = 0;
	public final int screenX;
	public final int screenY;

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
		//player status
		maxLife = 6;
		life = maxLife;
	}

	public void getPlayerImage() {
			// tải các ảnh vảo mảng
			 down1 = setup("/player/boy_down_1");
			 up1 = setup("/player/boy_up_1");
			 left1 = setup("/player/boy_left_1");
			 right1 = setup("/player/boy_right_1");
			 down2 = setup("/player/boy_down_2");
			 up2 = setup("/player/boy_up_2");
			 left2 = setup("/player/boy_left_2");
			 right2 = setup("/player/boy_right_2");
			  stay = setup("/player/boy_down_1");
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
		//CHECK OBJECT COLLISION
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
		//IF COLLISION IS FALSE PLAYER CAN MOVE
		if(collisionOn == false) {
			if(keyH.upPressed == true && keyH.leftPressed == true){
				worldY -= speed;
				worldX -= speed;
				direction = "left";
			}
			else if(keyH.upPressed == true && keyH.rightPressed == true){
				worldY -= speed;
				worldX += speed;
				direction = "right";
			}
			else if(keyH.downPressed == true && keyH.leftPressed == true){
				worldY += speed;
				worldX -= speed;
				direction = "left";
			}
			else if(keyH.downPressed == true && keyH.rightPressed == true){
				worldY += speed;
				worldX += speed;
				direction = "right";
			}
			else if(keyH.upPressed == true) {
				worldY -= speed;
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				worldY += speed;
				direction = "down";
			}
			else if(keyH.leftPressed== true) {
				worldX -= speed;
				direction = "left";
			}
			else if(keyH.rightPressed == true) {
				worldX += speed;
				direction = "right";
			}
			else
				direction = "stay";
		}

		// spriteNum để vẽ nhân vật  ở đây vẽ 8 bức 1s sẽ tạo đc chuyển động nhân vật
		spriteCounter++;
		if(spriteCounter>8) {
			if(spriteNum == 1) {
				spriteNum =2;
			}
			else if(spriteNum ==2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}

	public void pickUpObject(int i){
		if(i!=999){
			String objName = gp.obj[i].name;
			switch (objName){
				case "Key":
					hasKey++;
					gp.obj[i] = null;
					gp.ui.showMessage("You got a key");
					break;
				case "Door":
					if(hasKey>0){
						gp.obj[i] = null;
						hasKey--;
						gp.ui.showMessage("You opened the door !");
					}else{
						gp.ui.showMessage("You needed a key!");
					}
					break;
				case "Boots":
					gp.obj[i] = null;
					speed+=1;
					gp.ui.showMessage("Speed up");
					break;
				case "Chest":
					gp.ui.gameFinished = true;
					gp.stopMusic();
					//gp.playSE(4);
					break;
			}

		}
	}

	//hàm draw để vẽ hình của nhân vật khi di chuyển hoặc đứng yên
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.titleSize, gp.titleSize);
		BufferedImage image = null;
		switch (direction) {
			case "up":
				if(spriteNum == 1){					//dùng spriteNum để tạo chuyển động liên tục
					image = up1;
				}
				if(spriteNum == 2){
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1){
					image = down1;
				}
				if(spriteNum == 2){
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1){
					image = left1;
				}
				if(spriteNum == 2){
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1){
					image = right1;
				}
				if(spriteNum == 2){
					image = right2;
				}
				break;
			default:
				image = stay;
		};

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

		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize,null);
	}
}