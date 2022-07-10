package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	/**
	 * lưu các thuộc tính, các giá trị mặc định cho game
	 */
	private static final long serialVersionUID = 1L;
	//SCREEN SETTING
	final int originalTileSize = 16; //16x16 pixel
	final int scale = 3;
	public final int tileSize = originalTileSize*scale;//48x48 title
	public final int maxScreenCol = 24;
	public final int maxScreenRow = 14;
	public final int screenWidth = tileSize*maxScreenCol; // 960 pixel
	public final int screenHeight =  tileSize*maxScreenRow; // 576 // pixel

	// FOR FULL SCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreen = false;


	//WORLD SETTING 
	public final int maxWorldCol = 200;
	public final int maxWorldRow = 200;
	public final int WorldWidth = tileSize * maxWorldCol;
	public final int WorldHeight = tileSize * maxWorldRow;
	public EventHandler eHandler = new EventHandler(this);


	//FPS
	int FPS = 60;
	long  timer = 0;
	//SYSTEM

	//Sound
	public Sound music = new Sound();
	public Sound se = new Sound();
	// bản đồ và cỏ, tường nước ( tile
	TileManager tileM = new TileManager(this);

	// keyHandle -- các nút điều khiển
	public KeyHandler keyH = new KeyHandler(this);
	//UI
	public UI ui = new UI(this);


	Thread gameThread;

	// xác định va chạm của nhân vật với tường và nước
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public Player player = new Player(this,keyH);
	int event = player.hasKey;
	//Game State
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int charaterState = 4;
	public final int optionsState = 5;
	public final int gameOverState = 6;

	public Entity[] obj = new Entity[20];
	public Entity[] NPC = new Entity[10];
	public Entity[] monster = new Entity[20];

	public ArrayList<Entity> projectileList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame(){
		gameState = titleState;
		aSetter.setNPC();
		aSetter.setObject();
		aSetter.setMonster();
		playMusic(0);

		//ADD FULL SCREEN IN SET UP
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
		//setFullScreen();
	}
	public void setFullScreen(){

		//GET LOCAL SCREEN DEVICE
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Window.window);

		//GET FULLSCREEN HEIGHT AND WIDTH
		screenWidth2 = Window.window.getWidth();
		screenHeight2 = Window.window.getHeight();
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// hàm run trong thư viện giúp lặp lại việc vẽ. Vẽ 60 bức trong 1s để tạo chuyển động
	@Override
	public void run() {

		double drawInterval = 1000000000f/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		int drawCount = 0;
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) /drawInterval;
			timer  += (currentTime - lastTime);
			lastTime = currentTime;
			if(delta>=1) {

				//1 UPDATE: update information
				update();
				//2 DRAW: draw the screen
//			repaint();
				drawToTempScreen();	// draw everything to the buf image
				drawToScreen();	//	draw buf image to the screen
				delta--;
				drawCount++;
			}

			if(timer >= 1000000000) {
				drawCount++;
				drawCount =0;
				timer =0;
			}

		}
	}

	public void update() {
		if (gameState == playState) {
			// update player
			player.update();
			// update obj
			for(int i= 0; i< obj.length ; i++){
				if(obj[i]!=null){
					obj[i].update();
				}
			}
			// update NPC
			for(int i= 0; i< NPC.length ; i++){
				if(NPC[i]!=null){
					NPC[i].update();
				}
			}
			// update Monster
			for(int i= 0; i< monster.length ; i++){
				if(monster[i]!=null){
					if(monster[i].alive&&!monster[i].dying) monster[i].update();
					if(!monster[i].alive) {
						monster[i].checkDrop();
						monster[i] = null;
					}
				}
			}
			for(int i= 0; i< projectileList.size() ; i++){
				if(projectileList.get(i) !=null){
					if(projectileList.get(i).alive) projectileList.get(i).update();
					if(!projectileList.get(i).alive) projectileList.remove(i);
				}
			}
			for(int i= 0; i< projectileList.size() ; i++){
				if(projectileList.get(i) !=null){
					if(projectileList.get(i).alive) projectileList.get(i).update();
					if(!projectileList.get(i).alive) projectileList.remove(i);
				}
			}
			for(int i= 0; i< projectileList.size() ; i++){
				if(projectileList.get(i) !=null){
					if(projectileList.get(i).alive) projectileList.get(i).update();
					if(!projectileList.get(i).alive) projectileList.remove(i);
				}
			}
			tileM.update();
			if(event < player.hasKey) {
				//tileM.update("/maps/map01.txt");
				event = player.hasKey;
			}

		}
	}

	public void drawToTempScreen(){

		//title screen
		if(gameState == titleState){
			ui.draw(g2);
		}else{
			//tile
			tileM.draw(g2);
			//ADD entities to the List
			for (Entity item : NPC) {
				if (item != null) {
					entityList.add(item);
				}
			}
			for (Entity value : obj) {
				if (value != null) {
					entityList.add(value);
				}
			}
			for (Entity entity : monster) {
				if (entity != null) {
					entityList.add(entity);
				}
			}
			for(int i=0; i<projectileList.size(); i++){
				if(projectileList.get(i) != null){
					entityList.add(projectileList.get(i));
				}
			}
			entityList.add(player);

			//Sort
			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY,e2.worldY);
					return result;
				}
			});
			//Draw Entities
			for(int i = 0; i <entityList.size(); i++){
				entityList.get(i).draw(g2);
			}
			tileM.draw2(g2);
			// Empty Entity List
			entityList.clear();
			//layer2

			//UI
			ui.draw(g2);
		}
	}
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		Graphics2D g2 = (Graphics2D)g;
//		//title screen
//		if(gameState == titleState){
//			ui.draw(g2);
//		}else{
//			//tile
//			tileM.draw(g2);
//			//ADD entities to the List
//			for(int i=0; i< NPC.length; i++){
//				if(NPC[i]!= null){
//					entityList.add(NPC[i]);
//				}
//			}
//			for(int i=0; i<obj.length; i++){
//				if(obj[i]!=null){
//					entityList.add(obj[i]);
//				}
//			}
//			for(int i=0; i<monster.length; i++){
//				if(monster[i] != null){
//					entityList.add(monster[i]);
//				}
//			}
//			entityList.add(player);
//
//			//Sort
//			Collections.sort(entityList, new Comparator<Entity>() {
//				@Override
//				public int compare(Entity e1, Entity e2) {
//					int result = Integer.compare(e1.worldY,e2.worldY);
//					return result;
//				}
//			});
//			//Draw Entities
//			for(int i = 0; i <entityList.size(); i++){
//				entityList.get(i).draw(g2);
//			}
//			tileM.draw2(g2);
//			// Empty Entity List
//			entityList.clear();
//			//layer2
//
//			//UI
//			ui.draw(g2);
//		}
//		g2.dispose();
//	}


	// vẽ Màn hình mới
	public void drawToScreen(){
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}

	public void playMusic(int i){
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic(){
		music.stop();
	}
	public void playSE(int i){
		se.setFile(i);
		se.play();
	}
}
