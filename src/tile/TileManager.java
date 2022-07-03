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
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10000];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
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
		buildTiles("tiles/World_A1.png",2094, 0, 0);
		buildTiles("tiles/World_A1.png",2286, 0, 16);
		buildTiles("tiles/World_A1.png",2382, 6, 16);
		buildTiles("tiles/World_A1.png",2478, 12, 0);
		buildTiles("tiles/World_A1.png",2574, 18, 0);
		buildTiles("tiles/World_A1.png",2670, 12, 16);
		buildTiles("tiles/World_A1.png",2766, 18, 16);
		buildTiles("tiles/World_A1.png",2094, 0, 0);
		buildTiles("tiles/World_A1.png",2286, 0, 16);
		buildTiles("tiles/World_A1.png",2382, 6, 16);
		buildTiles("tiles/World_A1.png",2478, 12, 0);
		buildTiles("tiles/World_A1.png",2574, 18, 0);
		buildTiles("tiles/World_A1.png",2670, 12, 16);
		buildTiles("tiles/World_A1.png",2766, 18, 16);
	}
	
	//tải map 01. quy định 0 là cỏ, 1 là tường 2 là nước, xem res/map01.txt
	public void loadMap(String filePath) {
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
					mapTileNum[col][row] = num;
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
			int worldX = worldCol*gp.tileSize;
			int worldY = worldRow*gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			if(worldX + gp.tileSize  > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize  < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize  > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize  < gp.player.worldY + gp.player.screenY) {

				g2.drawImage(tile[tileNum].image[0], screenX, screenY, gp.tileSize/2 , gp.tileSize/2 , null);
				g2.drawImage(tile[tileNum].image[1], screenX + 24, screenY, gp.tileSize/2 , gp.tileSize/2 , null);
				g2.drawImage(tile[tileNum].image[2], screenX , screenY+24, gp.tileSize/2 , gp.tileSize/2 , null);
				g2.drawImage(tile[tileNum].image[3], screenX+24 , screenY+24, gp.tileSize/2 , gp.tileSize/2 , null);
			}
			
			worldCol++;
				
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
	public void buildTiles(String file, int index, int x, int y){
		SpriteSheet sp1 = new SpriteSheet(file,24,24);
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
		setupTile(sp1,index--,x,y,"02321213");//54
		setupTile(sp1,index--,x,y,"02324113");//53
		setupTile(sp1,index--,x,y,"31324113");//52
		setupTile(sp1,index--,x,y,"02034142");//51
		setupTile(sp1,index--,x,y,"31034142");//50
		setupTile(sp1,index--,x,y,"02324142");//49
		setupTile(sp1,index--,x,y,"31324142");//48
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
		tile[index].image[0] = sp.spriteArray[x+x1][y+y1].image;
		tile[index].image[1] = sp.spriteArray[x+x2][y+y2].image;
		tile[index].image[2] = sp.spriteArray[x+x3][y+y3].image;
		tile[index].image[3] = sp.spriteArray[x+x4][y+y4].image;
	}
}
