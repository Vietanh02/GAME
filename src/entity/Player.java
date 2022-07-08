package entity;



import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	public BufferedImage[] stay = new BufferedImage[3];
	private BufferedImage[] up = new BufferedImage[3];
	private BufferedImage[] down = new BufferedImage[3];
	private BufferedImage[] left = new BufferedImage[3];
	private BufferedImage[] right = new BufferedImage[3];
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

			String imageDown = new String("/player/boy_down_%d");
			String imageUp = new String("/player/boy_up_%d");
			String imageLeft = new String("/player/boy_left_%d");
			String imageRight = new String("/player/boy_right_%d");
			String imageStay = new String("/player/boy_down_%d");
			for(int i=0;i<3;i++) {
				stay[i] = setup(String.format(imageStay,i+1));
				up[i]   = setup(String.format(imageUp,i+1));
				down[i] = setup(String.format(imageDown,i+1));
				left[i] = setup(String.format(imageLeft,i+1));
				right[i] = setup(String.format(imageRight,i+1));
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
//		}else {
//			direction = "stay";
		}

		//CHECK TILE COLLISION
		collisionOn = false;
		gp.cChecker.checkTile(this);
		//CHECK OBJECT COLLISION
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
		int npcIndex = gp.cChecker.checkEntity(this, gp.NPC);
		interactNPC(npcIndex);

		gp.eHandler.checkEvent();
		gp.keyH.enterPressed = false;
		//IF COLLISION IS FALSE PLAYER CAN MOVE
		if(collisionOn == false) {
			if(keyH.upPressed == true && keyH.leftPressed == true){
				worldY -= speed;
				worldX -= speed;
			}
			else if(keyH.upPressed == true && keyH.rightPressed == true){
				worldY -= speed;
				worldX += speed;
				direction = "right";
			}
			else if(keyH.downPressed == true && keyH.leftPressed == true){
				worldY += speed;
				worldX -= speed;
			}
			else if(keyH.downPressed == true && keyH.rightPressed == true){
				worldY += speed;
				worldX += speed;
			}
			else if(keyH.upPressed == true) {
				worldY -= speed;
			}
			else if(keyH.downPressed == true) {
				worldY += speed;
			}
			else if(keyH.leftPressed== true) {
				worldX -= speed;
			}
			else if(keyH.rightPressed == true) {
				worldX += speed;
			}
		}

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
		BufferedImage image = switch (direction) {
			case "up" -> up[spriteNum - 1];
			case "down" -> down[spriteNum - 1];
			case "left" -> left[spriteNum - 1];
			case "right" -> right[spriteNum - 1];
			case "stay" -> stay[spriteNum - 1];
			default -> null;
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

	public BufferedImage getDownImage(){
		return this.down[0];
	}
	public void interactNPC(int i){
			if(i!=999){
				if(gp.keyH.enterPressed == true) {
					gp.gameState = gp.dialogueState;
					gp.NPC[i].speak();
				}
			}
	}
}