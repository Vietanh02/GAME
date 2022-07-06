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
		try {
			// tải các ảnh vảo mảng
			String imageDown = new String("/player/boy_down_%d.png");
			String imageUp = new String("/player/boy_up_%d.png");
			String imageLeft = new String("/player/boy_left_%d.png");
			String imageRight = new String("/player/boy_right_%d.png");
			String imageStay = new String("/player/boy_down_%d.png");
			for(int i=0;i<3;i++) {
				stay[i] = ImageIO.read(getClass().getResourceAsStream(String.format(imageStay,i+1)));
				up[i]   = ImageIO.read(getClass().getResourceAsStream(String.format(imageUp,i+1)));
				down[i] = ImageIO.read(getClass().getResourceAsStream(String.format(imageDown,i+1)));
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
					break;
				case "Door":
					if(hasKey>0){
						gp.obj[i] = null;
						hasKey--;
					}
					break;
			}

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
			case "stay" -> stay[spriteNum - 1];
			default -> null;
		};

		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize,null);
	}
}
