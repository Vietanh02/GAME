package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import Graphics.SpriteSheet;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;  // lưu các ô của bản đồ
	public int[][] layer0; // vẽ theo tọa đồ Oxy, ví dụ (0,1) là tường thì chèn ảnh tường vaò vị trí (0,1)
	public int[][] layer1;// lớp phủ trên bề mặt
	public int[][] layer2;//
	public int[][] layer3;//
	public String mapType = "Outside";
	private boolean collision = false;

	private static int spriteCounter = 0 ;
	private static int spriteNum = 0;
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10000];
		tile[0] = new Tile();
		buildTile(new SpriteSheet("tiles/Tile_0.png",24,24),0, 0,0,false); //ảnh trống
		for(int i = 1; i < 10000; i++){
			tile[i] = tile[0];
		}
		layer0 = new int[gp.maxWorldCol][gp.maxWorldRow];
		layer1 = new int[gp.maxWorldCol][gp.maxWorldRow];
		layer2 = new int[gp.maxWorldCol][gp.maxWorldRow];
		layer3 = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImageOutside();
//		getTileImageInside();
		loadMap("/maps/map1.00.txt",layer0);
		loadMap("/maps/map1.01.txt",layer1);
		loadMap("/maps/map1.02.txt",layer2);
		loadMap("/maps/map1.03.txt",layer3);
	}

	// nhập các ảnh wall, water, grass vào mảng tile
	public void getTileImageInside() {
		setupTilesA1("tiles/Inside_A1.png");
		setupTilesA2("tiles/Inside_A2.png");
		//setupTilesA3("tiles/Outside_A3.png");
		setupTilesB_C("tiles/Inside_B.png","tiles/Inside_C.png");
		setupTileA4("tiles/Inside_A4.png");
		setupTileA5("tiles/Inside_A5.png");
		mapType = "Inside";
	}
	public void getTileImageOutside() {
		setupTilesA1("tiles/Outside_A1.png");
		setupTilesA2("tiles/Outside_A2.png");
		setupTilesA3("tiles/Outside_A3.png");
		setupTilesB_C("tiles/Outside_B.png", "tiles/Outside_C.png");
		setupTileA4("tiles/Outside_A4.png");
		setupTileA5("tiles/Outside_A5.png");
		mapType = "Outside";
	}

	//tải map 01. quy định 0 là cỏ, 1 là tường 2 là nước, xem res/map01.txt
	public void loadMap(String filePath, int[][] layer) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col =0;
			int row =0;
			while(col<gp.maxWorldCol && row<gp.maxWorldRow) {
				String line = br.readLine();
				while(col < gp.maxWorldCol) {
					String[] numbers = line.split(" ");
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
			int layer1Num = layer1[worldCol][worldRow];
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
				g2.drawImage(tile[layer0Num].image[0], screenX, screenY, null);
				g2.drawImage(tile[layer0Num].image[1], screenX + 24, screenY , null);
				g2.drawImage(tile[layer0Num].image[2], screenX , screenY+24 , null);
				g2.drawImage(tile[layer0Num].image[3], screenX+24 , screenY+24, null);
				//vẽ lớp thứ 2
				g2.drawImage(tile[layer1Num].image[0], screenX, screenY,  null);
				g2.drawImage(tile[layer1Num].image[1], screenX + 24, screenY, null);
				g2.drawImage(tile[layer1Num].image[2], screenX , screenY+24, null);
				g2.drawImage(tile[layer1Num].image[3], screenX+24 , screenY+24,  null);
			}
			else if (gp.player.screenX > gp.player.worldX ||
					gp.player.screenY > gp.player.worldY ||
					rightOffset > gp.WorldWidth - gp.player.worldX ||
					bottomOffset > gp.WorldHeight - gp.player.worldY) {
				//vẽ nền
				g2.drawImage(tile[layer0Num].image[0], screenX, screenY, null);
				g2.drawImage(tile[layer0Num].image[1], screenX + 24, screenY , null);
				g2.drawImage(tile[layer0Num].image[2], screenX , screenY+24 , null);
				g2.drawImage(tile[layer0Num].image[3], screenX+24 , screenY+24, null);
				//vẽ lớp thứ 2
				g2.drawImage(tile[layer1Num].image[0], screenX, screenY,  null);
				g2.drawImage(tile[layer1Num].image[1], screenX + 24, screenY, null);
				g2.drawImage(tile[layer1Num].image[2], screenX , screenY+24, null);
				g2.drawImage(tile[layer1Num].image[3], screenX+24 , screenY+24,  null);

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
			int layer3Num = layer3[worldCol][worldRow];
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
				g2.drawImage(tile[layer2Num].image[0], screenX, screenY,  null);
				g2.drawImage(tile[layer2Num].image[1], screenX + 24, screenY,  null);
				g2.drawImage(tile[layer2Num].image[2], screenX , screenY+24,  null);
				g2.drawImage(tile[layer2Num].image[3], screenX+24 , screenY+24, null);

				g2.drawImage(tile[layer3Num].image[0], screenX, screenY, null);
				g2.drawImage(tile[layer3Num].image[1], screenX + 24, screenY,  null);
				g2.drawImage(tile[layer3Num].image[2], screenX , screenY+24,  null);
				g2.drawImage(tile[layer3Num].image[3], screenX+24 , screenY+24, null);
			}
			else if (gp.player.screenX > gp.player.worldX ||
					gp.player.screenY > gp.player.worldY ||
					rightOffset > gp.WorldWidth - gp.player.worldX ||
					bottomOffset > gp.WorldHeight - gp.player.worldY){
				//vẽ nền
				g2.drawImage(tile[layer2Num].image[0], screenX, screenY,  null);
				g2.drawImage(tile[layer2Num].image[1], screenX + 24, screenY,  null);
				g2.drawImage(tile[layer2Num].image[2], screenX , screenY+24,  null);
				g2.drawImage(tile[layer2Num].image[3], screenX+24 , screenY+24, null);

				g2.drawImage(tile[layer3Num].image[0], screenX, screenY, null);
				g2.drawImage(tile[layer3Num].image[1], screenX + 24, screenY,  null);
				g2.drawImage(tile[layer3Num].image[2], screenX , screenY+24,  null);
				g2.drawImage(tile[layer3Num].image[3], screenX+24 , screenY+24, null);
			}

			worldCol++;

			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}

	}
	public void buildWall_Tiles(SpriteSheet sp1, int index, int x, int y, boolean collision){

		this.collision = collision;
		for(int i = index +32; i > index;i--){
			setupTile(sp1,i,x,y,"00033033");//99-68
		}

		setupTile(sp1,index--,x,y,"00033033");//67
		setupTile(sp1,index--,x,y,"02033233");//66
		setupTile(sp1,index--,x,y,"20233033");//65
		setupTile(sp1,index--,x,y,"22233233");//64

		setupTile(sp1,index--,x,y,"00013031");//63
		setupTile(sp1,index--,x,y,"02013231");//62
		setupTile(sp1,index--,x,y,"20213031");//61
		setupTile(sp1,index--,x,y,"22213231");//60

		setupTile(sp1,index--,x,y,"00031013");//59
		setupTile(sp1,index--,x,y,"02031213");//58
		setupTile(sp1,index--,x,y,"20231013");//57
		setupTile(sp1,index--,x,y,"21231213");//56

		setupTile(sp1,index--,x,y,"00011011");//55
		setupTile(sp1,index--,x,y,"02011211");//54
		setupTile(sp1,index--,x,y,"20211011");//53
		setupTile(sp1,index,x,y, "22211211");//52

	}
	public void buildGround_Tiles(SpriteSheet sp1, int index, int x, int y, boolean collision){

		this.collision = collision;
		setupTile(sp1,index+1,x,y,"00011011");//95
		setupTile(sp1,index--,x,y,"00011011");//94
		setupTile(sp1,index--,x,y,"21015111");//93
		setupTile(sp1,index--,x,y,"30331011");//92
		setupTile(sp1,index--,x,y,"00221052");//91
		setupTile(sp1,index--,x,y,"00014043");//90
		setupTile(sp1,index--,x,y,"40035051");//89
		setupTile(sp1,index--,x,y,"40415051");//88
		setupTile(sp1,index--,x,y,"02435253");//87
		setupTile(sp1,index--,x,y,"42435253");//86
		setupTile(sp1,index--,x,y,"22231233");//85
		setupTile(sp1,index--,x,y,"22233233");//84
		setupTile(sp1,index--,x,y,"20213013");//83
		setupTile(sp1,index--,x,y,"20213031");//82
		setupTile(sp1,index--,x,y,"21225152");//81
		setupTile(sp1,index--,x,y,"30334043");//80
		setupTile(sp1,index--,x,y,"02035152");//79
		setupTile(sp1,index--,x,y,"41035152");//78
		setupTile(sp1,index--,x,y,"02425152");//77
		setupTile(sp1,index--,x,y,"41425152");//76
		setupTile(sp1,index--,x,y,"02331243");//75
		setupTile(sp1,index--,x,y,"02334243");//74
		setupTile(sp1,index--,x,y,"32331243");//74
		setupTile(sp1,index--,x,y,"32334243");//72
		setupTile(sp1,index--,x,y,"21221213");//71
		setupTile(sp1,index--,x,y,"21221232");//70
		setupTile(sp1,index--,x,y,"21223113");//69
		setupTile(sp1,index--,x,y,"21223132");//68
		setupTile(sp1,index--,x,y,"30034013");//67
		setupTile(sp1,index--,x,y,"30314013");//66
		setupTile(sp1,index--,x,y,"30034041");//65
		setupTile(sp1,index--,x,y,"30314041");//64
		setupTile(sp1,index--,x,y,"02031213");//64
		setupTile(sp1,index--,x,y,"31031213");//62
		setupTile(sp1,index--,x,y,"02321213");//61
		setupTile(sp1,index--,x,y,"31321213");//60
		setupTile(sp1,index--,x,y,"02031242");//59
		setupTile(sp1,index--,x,y,"31031242");//58
		setupTile(sp1,index--,x,y,"02321242");//57
		setupTile(sp1,index--,x,y,"31321242");//56
		setupTile(sp1,index--,x,y,"02034113");//55
		setupTile(sp1,index--,x,y,"31034113");//54
		setupTile(sp1,index--,x,y,"02324113");//53
		setupTile(sp1,index--,x,y,"31324113");//52
		setupTile(sp1,index--,x,y,"02034142");//51
		setupTile(sp1,index--,x,y,"31034142");//50
		setupTile(sp1,index--,x,y,"02324142");//49
		setupTile(sp1,index--,x,y,"31324142");//48
		tile[index] = tile[0];
	}
	//setup tile dặc biệt tại vị trí x, y
	public void buildTile(SpriteSheet sp, int index, int x, int y, boolean collision){
		this.collision = collision;
		tile[index] = new Tile();
		tile[index].image[0] = sp.spriteArray[x][y].image;
		tile[index].image[1] = sp.spriteArray[x][y+1].image;
		tile[index].image[2] = sp.spriteArray[x+1][y].image;
		tile[index].image[3] = sp.spriteArray[x+1][y+1].image;
	}
	public void setupTile(SpriteSheet sp, int index,int x, int y,String s){
		int x1 = Character.getNumericValue(s.charAt(0));
		int y1 = Character.getNumericValue(s.charAt(1));
		int x2 = Character.getNumericValue(s.charAt(2));
		int y2 = Character.getNumericValue(s.charAt(3));
		int x3 = Character.getNumericValue(s.charAt(4));
		int y3 = Character.getNumericValue(s.charAt(5));
		int x4 = Character.getNumericValue(s.charAt(6));
		int y4 = Character.getNumericValue(s.charAt(7));
		tile[index] = new Tile();
		tile[index].collision = this.collision;
		tile[index].image[0] = sp.spriteArray[x+x1][y+y1].image;
		tile[index].image[1] = sp.spriteArray[x+x2][y+y2].image;
		tile[index].image[2] = sp.spriteArray[x+x3][y+y3].image;
		tile[index].image[3] = sp.spriteArray[x+x4][y+y4].image;

	}

	public void setupTilesA1(String file){
		SpriteSheet ss = new SpriteSheet(file);
		buildGround_Tiles(ss,2094, 0, 0, true); //nước bờ cỏ
		buildGround_Tiles(ss,2142, 6, 0,true); // nước bờ sỏi đá
		buildGround_Tiles(ss,2190, 0, 12,true); //bèo
		buildGround_Tiles(ss,2238, 6, 12,true); //cỏ nước
		buildGround_Tiles(ss,2286, 0, 16,true); //nước bờ tuyết
		buildGround_Tiles(ss,2334, 0, 28,true); //thac
		setupTile(ss, 2291,0, 28,"00031013");//2 bờ
		setupTile(ss, 2290,0, 28,"02031213");//bờ phải
		setupTile(ss, 2289,0, 28,"00011011");//bờ trái
		setupTile(ss, 2288,0, 28,"01021112");//không bờ
		buildGround_Tiles(ss,2382, 6, 16,true); // nước
		buildGround_Tiles(ss,2430, 6, 28,true); //thac
		setupTile(ss, 2387,6, 28,"00031013");//2 bờ
		setupTile(ss, 2386,6, 28,"02031213");//bờ phải
		setupTile(ss, 2385,6, 28,"00011011");//bờ trái
		setupTile(ss, 2384,6, 28,"01021112");//không bờ
		buildGround_Tiles(ss,2478, 12, 0,true); //nước bờ cát
		buildGround_Tiles(ss,2526, 12, 12,true); //suoi
		setupTile(ss, 2483,12, 12,"00031013");//2 bờ
		setupTile(ss, 2482,12, 12,"02031213");//bờ phải
		setupTile(ss, 2481,12, 12,"00011011");//bờ trái
		setupTile(ss, 2481,12, 12,"01021112");//không bờ
		buildGround_Tiles(ss,2574, 18, 0,true); // nước bờ đất
		buildGround_Tiles(ss,2622, 18, 12,true); //suoi da
		setupTile(ss, 2579,18, 12,"00031013");//2 bờ
		setupTile(ss, 2578,18, 12,"02031213");//bờ phải
		setupTile(ss, 2577,18, 12,"00011011");//bờ trái
		setupTile(ss, 2576,18, 12,"01021112");//không bờ
		buildGround_Tiles(ss,2670, 12, 16,true); //nuoc
		buildGround_Tiles(ss,2718, 12, 28,true); //bong bong
		setupTile(ss, 2675,12, 28,"00031013");//2 bờ
		setupTile(ss, 2674,12, 28,"02031213");//bờ phải
		setupTile(ss, 2673,12, 28,"00011011");//bờ trái
		setupTile(ss, 2672,12, 28,"01021112");//không bờ
		buildGround_Tiles(ss,2766, 18, 16,true); //đầm độc
		buildGround_Tiles(ss,2814, 18, 28,true); //cay tren dam doc
		setupTile(ss, 2771,18, 28,"00031013");//2 bờ
		setupTile(ss, 2770,18, 28,"02031213");//bờ phải
		setupTile(ss, 2769,18, 28,"00011011");//bờ trái
		setupTile(ss, 2768,18, 28,"01021112");//không bờ

	}
	public void setupTilesA2(String file){
		SpriteSheet ss = new SpriteSheet(file);
		buildGround_Tiles(ss,2862, 0, 0,false); //cỏ non
		buildGround_Tiles(ss,2958, 0, 8,false); // đá trên cỏ
		buildGround_Tiles(ss,3246, 6, 0,false); // cát
		buildGround_Tiles(ss,3342, 6, 8,false); // đá trên cát
		buildGround_Tiles(ss,3630, 12, 0,false); // đất
		buildGround_Tiles(ss,3726, 12, 8,false); // đá trên đất
		buildGround_Tiles(ss,4014, 18, 0,false); //tuyết
		buildGround_Tiles(ss,4110, 18, 8,false); //đá trên tuyết

		buildGround_Tiles(ss,2910, 0, 4,false);// lớp cát trên cỏ
		buildGround_Tiles(ss,3006, 0, 12,false); //lớp đá trên cỏ
		buildGround_Tiles(ss,3054, 0, 16,false);// bụi cỏ non
		buildGround_Tiles(ss,3102, 0, 20,false);//khung kim loại
		buildGround_Tiles(ss,3150, 0, 24,true);//bờ rào gỗ
		buildGround_Tiles(ss,3198, 0, 28,false);//vết nứt

		buildGround_Tiles(ss,3294, 6, 4,false);// thảm cỏ
		buildGround_Tiles(ss,3390, 6, 12,false);// lớp đá trên cats
		buildGround_Tiles(ss,3438, 6, 16,false); // bụi cỏ vàng
		buildGround_Tiles(ss,3486, 6, 20,false); // khung kim loại có tuyết
		buildGround_Tiles(ss,3534, 6, 24,true); // bờ rào kim loại
		buildGround_Tiles(ss,3582, 6, 28,false);// bụi

		buildGround_Tiles(ss,3678, 12, 4,false); // thảm cỏ trên đất
		buildGround_Tiles(ss,3774, 12, 12,false); // nền gạch
		buildGround_Tiles(ss,3822, 12, 16,false);// bụi cỏ già
		buildGround_Tiles(ss,3870, 12, 20,true);// hố
		buildGround_Tiles(ss,3918, 12, 24,true);// bờ rào gỗ đẹp
		buildGround_Tiles(ss,3966, 12, 28,false);// rêu

		buildGround_Tiles(ss,4062, 16, 4,false);//cát trên tuyết
		buildGround_Tiles(ss,4158, 16, 12,false);//thảm
		buildGround_Tiles(ss,4206, 16, 16,false);//mây
		buildGround_Tiles(ss,4254, 16, 20,true);//đen
		buildGround_Tiles(ss,4302, 16, 24,true);//bờ rào có tuyết
		buildGround_Tiles(ss,4350, 16, 28,false);//bãi cát
	}
	public void setupTilesB_C(String file1, String file2) {
		SpriteSheet sp =new SpriteSheet(file1);
		int index = 0;
		for(int i = 0;i < 32; i+=2){
			for(int j = 0; j < 16; j+=2){
				buildTile(sp,index++,i,j,true);
			}
		}
		for(int i = 0; i< 32; i+=2){
			for(int j = 16; j < 32; j+=2){
				buildTile(sp,index++,i,j,true);
			}
		}
		sp = new SpriteSheet(file2);
		for(int i = 0;i < 32; i+=2){
			for(int j = 0; j < 16; j+=2){
				buildTile(sp,index++,i,j,true);
			}
		}
		for(int i = 0; i< 32; i+=2){
			for(int j = 16; j < 32; j+=2){
				buildTile(sp,index++,i,j,true);
			}
		}
	}
	public void setupTilesA3(String original){
		SpriteSheet file = new SpriteSheet(original);
		buildWall_Tiles(file, 4367,0,0, true);
		buildWall_Tiles(file, 4415,0,4, true);
		buildWall_Tiles(file, 4463,0,8, true);
		buildWall_Tiles(file, 4511,0,12, true);

		buildWall_Tiles(file, 4559,0,16, true);
		buildWall_Tiles(file, 4607,0,20, true);
		buildWall_Tiles(file, 4655,0,24, true);
		buildWall_Tiles(file, 4703,0,28, true);

		buildWall_Tiles(file, 4751,4,0, true);
		buildWall_Tiles(file, 4799,4,4, true);
		buildWall_Tiles(file, 4847,4,8, true);
		buildWall_Tiles(file, 4895,4,12, true);

		buildWall_Tiles(file, 4943,4,16, true);
		buildWall_Tiles(file, 4991,4,20, true);
		buildWall_Tiles(file, 5039,4,24, true);
		buildWall_Tiles(file, 5087,4,28, true);

		buildWall_Tiles(file, 5135,8,0, true);
		buildWall_Tiles(file, 5183,8,4, true);
		buildWall_Tiles(file, 5231,8,8, true);
		buildWall_Tiles(file, 5279,8,12, true);

		buildWall_Tiles(file, 5327,8,16, true);
		buildWall_Tiles(file, 5375,8,20, true);
		buildWall_Tiles(file, 5423,8,24, true);
		buildWall_Tiles(file, 5471,8,28, true);

		buildWall_Tiles(file, 5519,12,0, true);
		buildWall_Tiles(file, 5567,12,4, true);
		buildWall_Tiles(file, 5615,12,8, true);
		buildWall_Tiles(file, 5663,12,12, true);

		buildWall_Tiles(file, 5711,12,16, true);
		buildWall_Tiles(file, 5759,12,20, true);
		buildWall_Tiles(file, 5807,12,24, true);
		buildWall_Tiles(file, 5855,12,28, true);
	}
	public void setupTileA4(String original){
		SpriteSheet file = new SpriteSheet(original);
		buildGround_Tiles(file, 5934, 0,0,true);
		buildGround_Tiles(file, 5982, 0,4,true);
		buildGround_Tiles(file, 6030, 0,8,true);
		buildGround_Tiles(file, 6078, 0,12,true);
		buildGround_Tiles(file, 6126, 0,16,true);
		buildGround_Tiles(file, 6174, 0,20,true);
		buildGround_Tiles(file, 6222, 0,24,true);

		buildWall_Tiles(file, 6283, 6, 0, true);
		buildGround_Tiles(file, 6270, 0,28,true);

		buildWall_Tiles(file, 6331, 6, 4, true);
		buildWall_Tiles(file, 6379, 6, 8, true);
		buildWall_Tiles(file, 6427, 6, 12, true);
		buildWall_Tiles(file, 6475, 6, 16, true);
		buildWall_Tiles(file, 6523, 6, 20, true);
		buildWall_Tiles(file, 6571, 6, 24, true);
		buildWall_Tiles(file, 6619, 6, 28, true);

		buildGround_Tiles(file, 6702, 10,0,true);
		buildGround_Tiles(file, 6750, 10,4,true);
		buildGround_Tiles(file, 6798, 10,8,true);
		buildGround_Tiles(file, 6846, 10,12,true);
		buildGround_Tiles(file, 6894, 10,16,true);
		buildGround_Tiles(file, 6942, 10,20,true);
		buildGround_Tiles(file, 6990, 10,24,true);
		buildGround_Tiles(file, 7038, 10,28,true);

		buildWall_Tiles(file, 7055, 16, 0, true);
		buildWall_Tiles(file, 7103, 16, 4, true);
		buildWall_Tiles(file, 7151, 16, 8, true);
		buildWall_Tiles(file, 7199, 16, 12, true);
		buildWall_Tiles(file, 7247, 16, 16, true);
		buildWall_Tiles(file, 7295, 16, 20, true);
		buildWall_Tiles(file, 7343, 16, 24, true);
		buildWall_Tiles(file, 7391, 16, 28, true);

		buildGround_Tiles(file, 7470, 20,0,true);
		buildGround_Tiles(file, 7518, 20,4,true);
		buildGround_Tiles(file, 7566, 20,8,true);
		buildGround_Tiles(file, 7614, 20,12,true);
		buildGround_Tiles(file, 7662, 20,16,true);
		buildGround_Tiles(file, 7710, 20,20,true);
		buildGround_Tiles(file, 7758, 20,24,true);
		buildGround_Tiles(file, 7806, 20,28,true);

		buildWall_Tiles(file, 7823, 26, 0, true);
		buildWall_Tiles(file, 7871, 26, 4, true);
		buildWall_Tiles(file, 7919, 26, 8, true);
		buildWall_Tiles(file, 7967, 26, 12, true);
		buildWall_Tiles(file, 8015, 26, 16, true);
		buildWall_Tiles(file, 8063, 26, 20, true);
		buildWall_Tiles(file, 8111, 26, 24, true);
		buildWall_Tiles(file, 8159, 26, 28, true);
	}
	public void setupTileA5(String file){
		SpriteSheet sp =new SpriteSheet(file);
		int index = 1536;
		for(int i = 0;i < 32; i+=2){
			for(int j = 0; j < 16; j+=2){
				boolean bool = false;
				if(i >= 28) {
					bool = true;
					if(j >= 6 && j < 10) bool = false;
				}
				buildTile(sp,index++,i,j,bool);

			}
		}
	}

	//hoạt ảnh nước
	public void update(){

		spriteCounter++;
		if(spriteCounter>10	) {
			if(spriteNum == 0) {
				spriteNum =1;
			}
			else if(spriteNum ==1) {
				spriteNum = 2;
			}
			else if(spriteNum ==2) {
				spriteNum = 0;
			}
			spriteCounter = 0;
		}
//			buildTiles("tiles/"+mapType+"_A1.png",2094, 0, 4*spriteNum, true); //nước bờ cỏ
//			buildTiles("tiles/"+mapType+"_A1.png",2142, 6, 4*spriteNum,true); // nước bờ sỏi đá
//			buildTiles("tiles/"+mapType+"_A1.png",2286, 0, 16+4*spriteNum,true); //nước bờ tuyết
//			buildTiles("tiles/"+mapType+"_A1.png",2382, 6, 16+4*spriteNum,true); // nước
//			buildTiles("tiles/"+mapType+"_A1.png",2478, 12, 4*spriteNum,true); //nước bờ cát
//			buildTiles("tiles/"+mapType+"_A1.png",2574, 18, 4*spriteNum,true); // nước bờ đất
//			buildTiles("tiles/"+mapType+"_A1.png",2670, 12, 16+4*spriteNum,true); //nuoc
//			buildTiles("tiles/"+mapType+"_A1.png",2766, 18, 16+4*spriteNum,true); //đầm độc

	}
	//chuyển map
	public void update(String file){
		getTileImageInside();
		loadMap(file, layer0);
	}
}