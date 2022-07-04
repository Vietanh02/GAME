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
	/*	SpriteSheet world_a1 = new SpriteSheet("tiles/World_A1.png",24,24);
		tile[2] = new Tile();
		tile[2].image[0] = world_a1.spriteArray[3][1].image;
		tile[2].image[1] = world_a1.spriteArray[3][2].image;
		tile[2].image[2] = world_a1.spriteArray[4][1].image;
		tile[2].image[3] = world_a1.spriteArray[4][2].image;
		SpriteSheet world_a2 = new SpriteSheet("tiles/World_A2.png",24,24);
		tile[0] = new Tile();
		tile[0].image[0] = world_a2.spriteArray[3][1].image;
		tile[0].image[1] = world_a2.spriteArray[3][2].image;
		tile[0].image[2] = world_a2.spriteArray[4][1].image;
		tile[0].image[3] = world_a2.spriteArray[4][2].image;
		SpriteSheet world_b = new SpriteSheet("tiles/World_B.png",24,24);
		tile[1] = new Tile();
		tile[1].image[0] = world_b.spriteArray[2][0].image;
		tile[1].image[1] = world_b.spriteArray[2][1].image;
		tile[1].image[2] = world_b.spriteArray[3][0].image;
		tile[1].image[3] = world_b.spriteArray[3][1].image;*/
		buildTiles("tiles/World_A1.png",2094, 0, 0, false); //nước
		buildTiles("tiles/World_A1.png",2286, 0, 16,true); //đầm lầy độc
		buildTiles("tiles/World_A1.png",2382, 6, 16,true); //dung nham
		buildTiles("tiles/World_A1.png",2478, 12, 0,true); //nước 2
		buildTiles("tiles/World_A1.png",2574, 18, 0,true); // băng
		buildTiles("tiles/World_A1.png",2670, 12, 16,true); // hố
		buildTiles("tiles/World_A1.png",2766, 18, 16,true); //hố băng

		buildTiles("tiles/World_A2.png",2862, 0, 0,false); //cỏ non
		buildTiles("tiles/World_A2.png",2958, 0, 8,false); // cỏ già
		buildTiles("tiles/World_A2.png",3246, 6, 0,false); // sa mạc 1
		buildTiles("tiles/World_A2.png",3342, 6, 8,false); // sa mạc 2
		buildTiles("tiles/World_A2.png",3630, 12, 0,false); // sa mạc 3
		buildTiles("tiles/World_A2.png",3726, 12, 8,false); // đá
		buildTiles("tiles/World_A2.png",4014, 18, 0,false); //tuyết
		buildTiles("tiles/World_A2.png",4110, 18, 8,false); //băng tuyết

		buildTiles("tiles/World_A1.png",2142, 6, 0,false); //nước sâu
		buildTiles("tiles/World_A1.png",2190, 0, 12,false); //bãi đá
		buildTiles("tiles/World_A1.png",2238, 6, 12,false); //bãi băng đá
		//setup 1 số tile đặc biệt
		buildTile("tiles/World_A1.png",2289, 0, 28,false); // đầm lầy độc
		buildTile("tiles/World_A1.png",2384, 6, 28,false); // dung nham
		buildTile("tiles/World_A1.png",2480, 12, 12,false); //đá trên nước
		buildTile("tiles/World_A1.png",2576, 18, 12,false); //xoáy nước
		buildTile("tiles/World_A1.png",2672, 12, 28,false); //thác
		buildTile("tiles/World_A1.png",2770, 18, 28,false); //mây
		//
		buildTiles("tiles/World_A2.png",2910, 0, 4,false);// thảm cỏ non
		buildTiles("tiles/World_A2.png",3006, 0, 12,false); //thảm cỏ già
		buildTiles("tiles/World_A2.png",3054, 0, 16,false);// cây
		buildTiles("tiles/World_A2.png",3102, 0, 20,false);//cây thông
		buildTiles("tiles/World_A2.png",3150, 0, 24,false);//núi xanh
		buildTiles("tiles/World_A2.png",3198, 0, 28,false);//núi đá

		buildTiles("tiles/World_A2.png",3294, 6, 4,false);// hạn hán
		buildTiles("tiles/World_A2.png",3390, 6, 12,false);// cát
		buildTiles("tiles/World_A2.png",3438, 6, 16,false); // cây gai
		buildTiles("tiles/World_A2.png",3486, 6, 20,false); // cát 3
		buildTiles("tiles/World_A2.png",3534, 6, 24,false); // núi đá 2
		buildTiles("tiles/World_A2.png",3582, 6, 28,false);// núi đá 3

		buildTiles("tiles/World_A2.png",3678, 12, 4,false); // cát 4
		buildTiles("tiles/World_A2.png",3774, 12, 12,false); // dung nham
		buildTiles("tiles/World_A2.png",3822, 12, 16,false);//cây dừa
		buildTiles("tiles/World_A2.png",3870, 12, 20,false);//nền gạch
		buildTiles("tiles/World_A2.png",3918, 12, 24,false);//núi đá
		buildTiles("tiles/World_A2.png",3966, 12, 28,false);//núi lửa

		buildTiles("tiles/World_A2.png",4062, 16, 4,false);//núi tuyết
		buildTiles("tiles/World_A2.png",4158, 16, 12,false);//mây
		buildTiles("tiles/World_A2.png",4206, 16, 16,false);//cây thông phủ tuyết
		buildTiles("tiles/World_A2.png",4254, 16, 20,false);//hố
		buildTiles("tiles/World_A2.png",4302, 16, 24,false);//núi
		buildTiles("tiles/World_A2.png",4350, 16, 28,false);//núi tuyết

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
	public void buildTiles(String file, int index, int x, int y, boolean collision){
		SpriteSheet sp1 = new SpriteSheet(file,24,24);
		this.collision = collision;
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
	}
	//setup tile dặc biệt tại vị trí x, y
	public void buildTile(String file, int index, int x, int y, boolean collision){
		this.collision = collision;
		tile[index] = new Tile();
		tile[index].image[0] = new SpriteSheet(file,24,24).spriteArray[x][y].image;
		tile[index].image[1] = new SpriteSheet(file,24,24).spriteArray[x][y+1].image;
		tile[index].image[2] = new SpriteSheet(file,24,24).spriteArray[x+1][y].image;
		tile[index].image[3] = new SpriteSheet(file,24,24).spriteArray[x+1][y+1].image;
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
}
