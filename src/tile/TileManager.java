package tile;
import Graphics.SpriteSheet;
import main.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;  // lưu các ô của bản đồ
	public int[][] layer0; // vẽ theo tọa đồ Oxy, ví dụ (0,1) là tường thì chèn ảnh tường vaò vị trí (0,1)
	public int[][] layer1;// lớp phủ trên bề mặt
	public int[][] layer2;//
	public int[][] layer3;//
	public String mapType = "Outside";

	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10000];
		tile[0] = new Tile();
		tile[0].image = new SpriteSheet("tiles/Outside_B.png").spriteArray[0][0].image;
		layer0 = new int[gp.maxWorldCol][gp.maxWorldRow];
//		layer1 = new int[gp.maxWorldCol][gp.maxWorldRow];
		layer2 = new int[gp.maxWorldCol][gp.maxWorldRow];
//		layer3 = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImageOutside();
//		getTileImageInside();
		loadMap("/maps/map1.00.txt",layer0);
		//	loadMap("/maps/map1.01.txt",layer1);
		loadMap("/maps/map1.02.txt",layer2);
		//	loadMap("/maps/map1.03.txt",layer3);
	}

	// nhập các ảnh wall, water, grass vào mảng tile
	public void getTileImageInside() {
		setupTilesA1("tiles/Inside_A1.png");
		setupTilesA2("tiles/Inside_A2.png");
		//setupTilesA3("tiles/Outside_A3.png");
		setupTilesB("tiles/Inside_B.png");
		setupTilesC("tiles/Inside_C.png");
		setupTileA4("tiles/Inside_A4.png");
		setupTileA5("tiles/Inside_A5.png");
		mapType = "Inside";
	}
	public void getTileImageOutside() {
		setupTilesA1("tiles/Outside_A1.png");
		setupTilesA2("tiles/Outside_A2.png");
		setupTilesA3("tiles/Outside_A3.png");
		setupTilesB("tiles/Outside_B.png");
		setupTilesC("tiles/Outside_C.png");
		setupTileA4("tiles/Outside_A4.png");
		setupTileA5("tiles/Outside_A5.png");
		setupTileWA1("tiles/World_A1.png");
		setupTileWA2("tiles/World_A2.png");
		mapType = "Outside";
	}

	//tải map 01. quy định 0 là cỏ, 1 là tường 2 là nước, xem res/map01.txt
	public void loadMap(String filePath, int[][] layer) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			assert is != null;
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col =0;
			int row =0;
			while(col<gp.maxWorldCol && row<gp.maxWorldRow) {
				String line = br.readLine();
				while(col < gp.maxWorldCol) {
					String[] numbers = line.split(",");
					int num = Integer.parseInt(numbers[col]);
					layer[col][row] = num; // đọc mảng cho lớp nền
					col++;
				}
				if(col == gp.maxWorldCol) {
					col=0;
					row++;
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {

		int worldCol =0;
		int worldRow = 0;

		while(worldCol< gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int layer0Num = layer0[worldCol][worldRow];
//			int layer1Num = layer1[worldCol][worldRow];
			int worldX = worldCol*gp.tileSize;
			int worldY = worldRow*gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			// stop moving the camera at the edge

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
				//vẽ nền
				g2.drawImage(tile[layer0Num].image, screenX, screenY, 48,48, null);
				//vẽ lớp thứ 2
				//	g2.drawImage(tile[layer1Num].image, screenX, screenY,  gp.tileSize,gp.tileSize, null);

			}
			else if (gp.player.screenX > gp.player.worldX ||
					gp.player.screenY > gp.player.worldY ||
					rightOffset > gp.WorldWidth - gp.player.worldX ||
					bottomOffset > gp.WorldHeight - gp.player.worldY) {
				//vẽ nền
				g2.drawImage(tile[layer0Num].image, screenX, screenY, 48,48, null);

				//vẽ lớp thứ 2
				//	g2.drawImage(tile[layer1Num].image, screenX, screenY,  gp.tileSize,gp.tileSize, null);

			}

			worldCol++;

			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}

	}
	//lớp 3 4 ưu tiên trên nhân vật
	public void draw2(Graphics2D g2) {

		int worldCol =0;
		int worldRow = 0;
		while(worldCol< gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int layer2Num = layer2[worldCol][worldRow];
//			int layer3Num = layer3[worldCol][worldRow];
			int worldX = worldCol*gp.tileSize;
			int worldY = worldRow*gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			// stop moving the camera at the edge

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
				//vẽ nền
				g2.drawImage(tile[layer2Num].image, screenX, screenY, 48,48, null);

//				g2.drawImage(tile[layer3Num].image, screenX, screenY, gp.tileSize,gp.tileSize, null);
			}
			else if (gp.player.screenX > gp.player.worldX ||
					gp.player.screenY > gp.player.worldY ||
					rightOffset > gp.WorldWidth - gp.player.worldX ||
					bottomOffset > gp.WorldHeight - gp.player.worldY){
				//vẽ nền
				g2.drawImage(tile[layer2Num].image, screenX, screenY, 48,48, null);


//				g2.drawImage(tile[layer3Num].image, screenX, screenY, gp.tileSize,gp.tileSize, null);
			}

			worldCol++;

			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}

	}
//	public void buildWall_Tiles(SpriteSheet sp1, int index, int x, int y, boolean collision){
//
//		this.collision = collision;
//		for(int i = index +32; i > index;i--){
//			setupTile(sp1,i,x,y,"00033033");//99-68
//		}
//
//		setupTile(sp1,index--,x,y,"00033033");//67
//		setupTile(sp1,index--,x,y,"02033233");//66
//		setupTile(sp1,index--,x,y,"20233033");//65
//		setupTile(sp1,index--,x,y,"22233233");//64
//
//		setupTile(sp1,index--,x,y,"00013031");//63
//		setupTile(sp1,index--,x,y,"02013231");//62
//		setupTile(sp1,index--,x,y,"20213031");//61
//		setupTile(sp1,index--,x,y,"22213231");//60
//
//		setupTile(sp1,index--,x,y,"00031013");//59
//		setupTile(sp1,index--,x,y,"02031213");//58
//		setupTile(sp1,index--,x,y,"20231013");//57
//		setupTile(sp1,index--,x,y,"21231213");//56
//
//		setupTile(sp1,index--,x,y,"00011011");//55
//		setupTile(sp1,index--,x,y,"02011211");//54
//		setupTile(sp1,index--,x,y,"20211011");//53
//		setupTile(sp1,index,x,y, "22211211");//52
//
//	}
//	public void buildGround_Tiles(SpriteSheet sp1, int index, int x, int y, boolean collision){
//
//		this.collision = collision;
//		setupTile(sp1,index+1,x,y,"00011011");//95
//		setupTile(sp1,index--,x,y,"00011011");//94
//		setupTile(sp1,index--,x,y,"21015111");//93
//		setupTile(sp1,index--,x,y,"30331011");//92
//		setupTile(sp1,index--,x,y,"00221052");//91
//		setupTile(sp1,index--,x,y,"00014043");//90
//		setupTile(sp1,index--,x,y,"40035051");//89
//		setupTile(sp1,index--,x,y,"40415051");//88
//		setupTile(sp1,index--,x,y,"02435253");//87
//		setupTile(sp1,index--,x,y,"42435253");//86
//		setupTile(sp1,index--,x,y,"22231233");//85
//		setupTile(sp1,index--,x,y,"22233233");//84
//		setupTile(sp1,index--,x,y,"20213013");//83
//		setupTile(sp1,index--,x,y,"20213031");//82
//		setupTile(sp1,index--,x,y,"21225152");//81
//		setupTile(sp1,index--,x,y,"30334043");//80
//		setupTile(sp1,index--,x,y,"02035152");//79
//		setupTile(sp1,index--,x,y,"41035152");//78
//		setupTile(sp1,index--,x,y,"02425152");//77
//		setupTile(sp1,index--,x,y,"41425152");//76
//		setupTile(sp1,index--,x,y,"02331243");//75
//		setupTile(sp1,index--,x,y,"02334243");//74
//		setupTile(sp1,index--,x,y,"32331243");//74
//		setupTile(sp1,index--,x,y,"32334243");//72
//		setupTile(sp1,index--,x,y,"21221213");//71
//		setupTile(sp1,index--,x,y,"21221232");//70
//		setupTile(sp1,index--,x,y,"21223113");//69
//		setupTile(sp1,index--,x,y,"21223132");//68
//		setupTile(sp1,index--,x,y,"30034013");//67
//		setupTile(sp1,index--,x,y,"30314013");//66
//		setupTile(sp1,index--,x,y,"30034041");//65
//		setupTile(sp1,index--,x,y,"30314041");//64
//		setupTile(sp1,index--,x,y,"02031213");//64
//		setupTile(sp1,index--,x,y,"31031213");//62
//		setupTile(sp1,index--,x,y,"02321213");//61
//		setupTile(sp1,index--,x,y,"31321213");//60
//		setupTile(sp1,index--,x,y,"02031242");//59
//		setupTile(sp1,index--,x,y,"31031242");//58
//		setupTile(sp1,index--,x,y,"02321242");//57
//		setupTile(sp1,index--,x,y,"31321242");//56
//		setupTile(sp1,index--,x,y,"02034113");//55
//		setupTile(sp1,index--,x,y,"31034113");//54
//		setupTile(sp1,index--,x,y,"02324113");//53
//		setupTile(sp1,index--,x,y,"31324113");//52
//		setupTile(sp1,index--,x,y,"02034142");//51
//		setupTile(sp1,index--,x,y,"31034142");//50
//		setupTile(sp1,index--,x,y,"02324142");//49
//		setupTile(sp1,index--,x,y,"31324142");//48
//		tile[index] = tile[0];
//	}
//	//setup tile dặc biệt tại vị trí x, y
//	public void buildTile(SpriteSheet sp, int index, int x, int y, boolean collision){
//
//	}
//	public void setupTile(SpriteSheet sp, int index,int x, int y,String s){
//
//	}

	public void setupTilesA1(String file){
		SpriteSheet ss = new SpriteSheet(file);
		int index = 1;
		for(int i = 0; i < 24; i++){
			for(int j = 0; j < 32; j++){
				tile[index] = new Tile();
				tile[index].image = ss.spriteArray[i][j].image;
				tile[index].collision = true;
				index++;
			}
		}
	}
	public void setupTilesA2(String file){
		SpriteSheet ss = new SpriteSheet(file);
		int index = 769;
		for(int i = 0; i < 24; i++){
			for(int j = 0; j < 32; j++){
				tile[index] = new Tile();
				tile[index].image = ss.spriteArray[i][j].image;

				index++;
			}
		}
	}
	public void setupTileWA1(String file){
		SpriteSheet ss = new SpriteSheet(file);
		int index = 1537;
		for(int i = 0; i < 24; i++){
			for(int j = 0; j < 32; j++){
				tile[index] = new Tile();
				tile[index].image = ss.spriteArray[i][j].image;
				tile[index].collision = true;
				index++;
			}
		}
	}
	public void setupTileWA2(String file){
		SpriteSheet ss = new SpriteSheet(file);
		int index = 2305;
		for(int i = 0; i < 24; i++){
			for(int j = 0; j < 32; j++){
				tile[index] = new Tile();
				tile[index].image = ss.spriteArray[i][j].image;
				index++;
			}
		}
	}
	public void setupTilesA3(String file){
		SpriteSheet ss = new SpriteSheet(file);
		int index = 6593;
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 32; j++){
				tile[index] = new Tile();
				tile[index].image = ss.spriteArray[i][j].image;
				tile[index].collision = true;
				index++;
			}
		}
	}
	public void setupTileA4(String file){
		SpriteSheet ss = new SpriteSheet(file);
		int index = 4609;
		for(int i = 0; i < 30; i++){
			for(int j = 0; j < 32; j++){
				tile[index] = new Tile();
				tile[index].image = ss.spriteArray[i][j].image;
				tile[index].collision = true;
				index++;
			}
		}
	}
	public void setupTileA5(String file){
		SpriteSheet ss = new SpriteSheet(file);
		int index = 3073;
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 16; j++){
				tile[index] = new Tile();
				tile[index].image = ss.spriteArray[i][j].image;
				tile[index].collision = true;
				index++;
			}
		}
	}
	public void setupTilesB(String file) {
		SpriteSheet ss = new SpriteSheet(file);
		int index = 3585;
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				tile[index] = new Tile();
				tile[index].image = ss.spriteArray[i][j].image;
				index++;
			}
		}
	}
	public void setupTilesC(String file){
		SpriteSheet ss = new SpriteSheet(file);
		int index = 5569;
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				tile[index] = new Tile();
				tile[index].image = ss.spriteArray[i][j].image;
				index++;
			}
		}
	}

	//hoạt ảnh nước
	public void update(){
	}
	//chuyển map
	public void update(String file){
	}
}