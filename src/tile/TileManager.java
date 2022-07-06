package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Graphics.SpriteSheet;
import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;  // lưu các ô của bản đồ
	public int[][] mapTileNum; // vẽ theo tọa đồ Oxy, ví dụ (0,1) là tường thì chèn ảnh tường vaò vị trí (0,1)
	public int[][] layer1;// lớp phủ trên bề mặt
	private boolean collision = false;
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10000];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		layer1 = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/map01.txt");
	}
	
	// nhập các ảnh wall, water, grass vào mảng tile
	public void getTileImage() {

		tile[0] = new Tile();
		buildTile(new SpriteSheet("tiles/Tile_0.png",24,24),0, 0,0,false); //ảnh trống
		for(int i = 1; i < 10000; i++){
			tile[i] = tile[0];
		}
		setupTilesA1("tiles/World_A1.png");
		setupTilesA2("tiles/World_A2.png");
		setupTilesA3("tiles/World_A3.png");
		setupTilesB_C("tiles/World_B.png","tiles/World_C.png");
		setupTileA4("tiles/World_A4.png");
		setupTileA5("tiles/World_A5.png");
	}
	
	//tải map 01. quy định 0 là cỏ, 1 là tường 2 là nước, xem res/map01.txt
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col =0;
			int row =0;
			while(col<gp.maxWorldCol && row<2*gp.maxWorldRow) {
				String line = br.readLine();
				while(col < gp.maxWorldCol) {
						String[] numbers = line.split(" ");
						int num = Integer.parseInt(numbers[col]);
						if(row < gp.maxWorldRow)  mapTileNum[col][row] = num; // đọc mảng cho lớp nền
						else layer1[col][row - gp.maxWorldRow] =  num; //đọc cho lớp phủ 1
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
			int tileNum = mapTileNum[worldCol][worldRow];
			int layer1Num = layer1[worldCol][worldRow];
			int worldX = worldCol*gp.tileSize;
			int worldY = worldRow*gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			if(worldX + gp.tileSize  > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize  < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize  > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize  < gp.player.worldY + gp.player.screenY) {
				//vẽ nền
				g2.drawImage(tile[tileNum].image[0], screenX, screenY, gp.tileSize/2 , gp.tileSize/2 , null);
				g2.drawImage(tile[tileNum].image[1], screenX + 24, screenY, gp.tileSize/2 , gp.tileSize/2 , null);
				g2.drawImage(tile[tileNum].image[2], screenX , screenY+24, gp.tileSize/2 , gp.tileSize/2 , null);
				g2.drawImage(tile[tileNum].image[3], screenX+24 , screenY+24, gp.tileSize/2 , gp.tileSize/2 , null);
				//vẽ lớp thứ 2
				g2.drawImage(tile[layer1Num].image[0], screenX, screenY, gp.tileSize/2 , gp.tileSize/2 , null);
				g2.drawImage(tile[layer1Num].image[1], screenX + 24, screenY, gp.tileSize/2 , gp.tileSize/2 , null);
				g2.drawImage(tile[layer1Num].image[2], screenX , screenY+24, gp.tileSize/2 , gp.tileSize/2 , null);
				g2.drawImage(tile[layer1Num].image[3], screenX+24 , screenY+24, gp.tileSize/2 , gp.tileSize/2 , null);
			}
			
			worldCol++;
				
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
	public void buildTileWalls(String file, int index, int x, int y, boolean collision){
		SpriteSheet sp1 = new SpriteSheet(file,24,24);
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
	public void buildTiles(String file, int index, int x, int y, boolean collision){
		SpriteSheet sp1 = new SpriteSheet(file,24,24);
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
		buildTiles(file,2094, 0, 0, false); //nước bờ cỏ
		buildTiles(file,2142, 6, 0,false); // nước bờ sỏi đá
		buildTiles(file,2190, 0, 12,false); //bèo
		buildTiles(file,2238, 6, 12,false); //cỏ nước
		buildTiles(file,2286, 0, 16,false); //nước bờ tuyết
		buildTiles(file,2334, 0, 28,false); //thac
		setupTile(new SpriteSheet(file,24,24), 2291,0, 28,"00031013");//2 bờ
		setupTile(new SpriteSheet(file,24,24), 2290,0, 28,"02031213");//bờ phải
		setupTile(new SpriteSheet(file,24,24), 2289,0, 28,"00011011");//bờ trái
		setupTile(new SpriteSheet(file,24,24), 2288,0, 28,"01021112");//không bờ
		buildTiles(file,2382, 6, 16,false); // nước
		buildTiles(file,2430, 6, 28,false); //thac
		setupTile(new SpriteSheet(file,24,24), 2387,6, 28,"00031013");//2 bờ
		setupTile(new SpriteSheet(file,24,24), 2386,6, 28,"02031213");//bờ phải
		setupTile(new SpriteSheet(file,24,24), 2385,6, 28,"00011011");//bờ trái
		setupTile(new SpriteSheet(file,24,24), 2384,6, 28,"01021112");//không bờ
		buildTiles(file,2478, 12, 0,false); //nước bờ cát
		buildTiles(file,2526, 12, 12,false); //suoi
		setupTile(new SpriteSheet(file,24,24), 2483,12, 12,"00031013");//2 bờ
		setupTile(new SpriteSheet(file,24,24), 2482,12, 12,"02031213");//bờ phải
		setupTile(new SpriteSheet(file,24,24), 2481,12, 12,"00011011");//bờ trái
		setupTile(new SpriteSheet(file,24,24), 2481,12, 12,"01021112");//không bờ
		buildTiles(file,2574, 18, 0,false); // nước bờ đất
		buildTiles(file,2622, 18, 12,false); //suoi da
		setupTile(new SpriteSheet(file,24,24), 2579,18, 12,"00031013");//2 bờ
		setupTile(new SpriteSheet(file,24,24), 2578,18, 12,"02031213");//bờ phải
		setupTile(new SpriteSheet(file,24,24), 2577,18, 12,"00011011");//bờ trái
		setupTile(new SpriteSheet(file,24,24), 2576,18, 12,"01021112");//không bờ
		buildTiles(file,2670, 12, 16,false); //bong bong
		buildTiles(file,2718, 12, 28,true); //thac
		setupTile(new SpriteSheet(file,24,24), 2675,12, 28,"00031013");//2 bờ
		setupTile(new SpriteSheet(file,24,24), 2674,12, 28,"02031213");//bờ phải
		setupTile(new SpriteSheet(file,24,24), 2673,12, 28,"00011011");//bờ trái
		setupTile(new SpriteSheet(file,24,24), 2672,12, 28,"01021112");//không bờ
		buildTiles(file,2766, 18, 16,false); //đầm độc
		buildTiles(file,2814, 18, 28,false); //cay tren dam doc
		setupTile(new SpriteSheet(file,24,24), 2771,18, 28,"00031013");//2 bờ
		setupTile(new SpriteSheet(file,24,24), 2770,18, 28,"02031213");//bờ phải
		setupTile(new SpriteSheet(file,24,24), 2769,18, 28,"00011011");//bờ trái
		setupTile(new SpriteSheet(file,24,24), 2768,18, 28,"01021112");//không bờ

	}
	public void setupTilesA2(String file){
		buildTiles(file,2862, 0, 0,false); //cỏ non
		buildTiles(file,2958, 0, 8,false); // cỏ già
		buildTiles(file,3246, 6, 0,false); // sa mạc 1
		buildTiles(file,3342, 6, 8,false); // sa mạc 2
		buildTiles(file,3630, 12, 0,false); // sa mạc 3
		buildTiles(file,3726, 12, 8,false); // đá
		buildTiles(file,4014, 18, 0,false); //tuyết
		buildTiles(file,4110, 18, 8,false); //băng tuyết

		buildTiles(file,2910, 0, 4,false);// thảm cỏ non
		buildTiles(file,3006, 0, 12,false); //thảm cỏ già
		buildTiles(file,3054, 0, 16,false);// cây
		buildTiles(file,3102, 0, 20,false);//cây thông
		buildTiles(file,3150, 0, 24,false);//núi xanh
		buildTiles(file,3198, 0, 28,false);//núi đá

		buildTiles(file,3294, 6, 4,false);// hạn hán
		buildTiles(file,3390, 6, 12,false);// cát
		buildTiles(file,3438, 6, 16,false); // cây gai
		buildTiles(file,3486, 6, 20,false); // cát 3
		buildTiles(file,3534, 6, 24,false); // núi đá 2
		buildTiles(file,3582, 6, 28,false);// núi đá 3

		buildTiles(file,3678, 12, 4,false); // cát 4
		buildTiles(file,3774, 12, 12,false); // dung nham
		buildTiles(file,3822, 12, 16,false);//cây dừa
		buildTiles(file,3870, 12, 20,false);//nền gạch
		buildTiles(file,3918, 12, 24,false);//núi đá
		buildTiles(file,3966, 12, 28,false);//núi lửa

		buildTiles(file,4062, 16, 4,false);//núi tuyết
		buildTiles(file,4158, 16, 12,false);//mây
		buildTiles(file,4206, 16, 16,false);//cây thông phủ tuyết
		buildTiles(file,4254, 16, 20,false);//hố
		buildTiles(file,4302, 16, 24,false);//núi
		buildTiles(file,4350, 16, 28,false);//núi tuyết
	}
	public void setupTilesB_C(String file1, String file2) {
		SpriteSheet sp =new SpriteSheet(file1, 24, 24);
		int index = 0;
		for(int i = 0;i < 32; i+=2){
			for(int j = 0; j < 16; j+=2){
				buildTile(sp,index++,i,j,false);
			}
		}
		for(int i = 0; i< 32; i+=2){
			for(int j = 16; j < 32; j+=2){
				buildTile(sp,index++,i,j,false);
			}
		}
		sp = new SpriteSheet(file2, 24,24);
		for(int i = 0;i < 32; i+=2){
			for(int j = 0; j < 16; j+=2){
				buildTile(sp,index++,i,j,false);
			}
		}
		for(int i = 0; i< 32; i+=2){
			for(int j = 16; j < 32; j+=2){
				buildTile(sp,index++,i,j,false);
			}
		}
	}
	public void setupTilesA3(String file){
		buildTileWalls(file, 4367,0,0, false);
		buildTileWalls(file, 4415,0,4, false);
		buildTileWalls(file, 4463,0,8, false);
		buildTileWalls(file, 4511,0,12, false);

		buildTileWalls(file, 4559,0,16, false);
		buildTileWalls(file, 4607,0,20, false);
		buildTileWalls(file, 4655,0,24, false);
		buildTileWalls(file, 4703,0,28, false);

		buildTileWalls(file, 4751,4,0, false);
		buildTileWalls(file, 4799,4,4, false);
		buildTileWalls(file, 4847,4,8, false);
		buildTileWalls(file, 4895,4,12, false);

		buildTileWalls(file, 4943,4,16, false);
		buildTileWalls(file, 4991,4,20, false);
		buildTileWalls(file, 5039,4,24, false);
		buildTileWalls(file, 5087,4,28, false);

		buildTileWalls(file, 5135,8,0, false);
		buildTileWalls(file, 5183,8,4, false);
		buildTileWalls(file, 5231,8,8, false);
		buildTileWalls(file, 5279,8,12, false);

		buildTileWalls(file, 5327,8,16, false);
		buildTileWalls(file, 5375,8,20, false);
		buildTileWalls(file, 5423,8,24, false);
		buildTileWalls(file, 5471,8,28, false);

		buildTileWalls(file, 5519,12,0, false);
		buildTileWalls(file, 5567,12,4, false);
		buildTileWalls(file, 5615,12,8, false);
		buildTileWalls(file, 5663,12,12, false);

		buildTileWalls(file, 5711,12,16, false);
		buildTileWalls(file, 5759,12,20, false);
		buildTileWalls(file, 5807,12,24, false);
		buildTileWalls(file, 5855,12,28, false);
	}
	public void setupTileA4(String file){
		buildTiles(file, 5934, 0,0,false);
		buildTiles(file, 5982, 0,4,false);
		buildTiles(file, 6030, 0,8,false);
		buildTiles(file, 6078, 0,12,false);
		buildTiles(file, 6126, 0,16,false);
		buildTiles(file, 6174, 0,20,false);
		buildTiles(file, 6222, 0,24,false);

		buildTileWalls(file, 6283, 6, 0, false);
		buildTiles(file, 6270, 0,28,false);

		buildTileWalls(file, 6331, 6, 4, false);
		buildTileWalls(file, 6379, 6, 8, false);
		buildTileWalls(file, 6427, 6, 12, false);
		buildTileWalls(file, 6475, 6, 16, false);
		buildTileWalls(file, 6523, 6, 20, false);
		buildTileWalls(file, 6571, 6, 24, false);
		buildTileWalls(file, 6619, 6, 28, false);

		buildTiles(file, 6702, 10,0,false);
		buildTiles(file, 6750, 10,4,false);
		buildTiles(file, 6798, 10,8,false);
		buildTiles(file, 6846, 10,12,false);
		buildTiles(file, 6894, 10,16,false);
		buildTiles(file, 6942, 10,20,false);
		buildTiles(file, 6990, 10,24,false);
		buildTiles(file, 7038, 10,28,false);

		buildTileWalls(file, 7055, 16, 0, false);
		buildTileWalls(file, 7103, 16, 4, false);
		buildTileWalls(file, 7151, 16, 8, false);
		buildTileWalls(file, 7199, 16, 12, false);
		buildTileWalls(file, 7247, 16, 16, false);
		buildTileWalls(file, 7295, 16, 20, false);
		buildTileWalls(file, 7343, 16, 24, false);
		buildTileWalls(file, 7391, 16, 28, false);

		buildTiles(file, 7470, 20,0,false);
		buildTiles(file, 7518, 20,4,false);
		buildTiles(file, 7566, 20,8,false);
		buildTiles(file, 7614, 20,12,false);
		buildTiles(file, 7662, 20,16,false);
		buildTiles(file, 7710, 20,20,false);
		buildTiles(file, 7758, 20,24,false);
		buildTiles(file, 7806, 20,28,false);

		buildTileWalls(file, 7823, 26, 0, false);
		buildTileWalls(file, 7871, 26, 4, false);
		buildTileWalls(file, 7919, 26, 8, false);
		buildTileWalls(file, 7967, 26, 12, false);
		buildTileWalls(file, 8015, 26, 16, false);
		buildTileWalls(file, 8063, 26, 20, false);
		buildTileWalls(file, 8111, 26, 24, false);
		buildTileWalls(file, 8159, 26, 28, false);
	}
	public void setupTileA5(String file){
		SpriteSheet sp =new SpriteSheet(file, 24, 24);
		int index = 1536;
		for(int i = 0;i < 32; i+=2){
			for(int j = 0; j < 16; j+=2){
				buildTile(sp,index++,i,j,false);
			}
		}
	}
}
