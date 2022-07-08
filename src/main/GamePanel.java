package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize*maxScreenCol; //768 pixel
	public final int screenHeight =  tileSize*maxScreenRow; // 576 // pixel

	//WORLD SETTING 
	public final int maxWorldCol = 120;
	public final int maxWorldRow = 120;
	public final int WorldWidth = tileSize * maxWorldCol;
	public final int WorldHeight = tileSize * maxWorldRow;
	public EventHandler eHandler = new EventHandler(this);


	//FPS
	int FPS = 60;
	long  timer = 0;
	//SYSTEM
	// bản đồ và cỏ, tường nước ( tile
	TileManager tileM = new TileManager(this);

	// keyHandle -- các nút điều khiển
	public KeyHandler keyH = new KeyHandler(this);
	Sound sound = new Sound();
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

	public Entity[] obj = new Entity[10];
	public Entity[] NPC = new Entity[10];
	public Entity[] monster = new Entity[20];
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
				repaint();
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
					monster[i].update();
				}
			}
			tileM.update();
			if(event < player.hasKey) {
				//tileM.update("/maps/map01.txt");
				event = player.hasKey;
			}

		}
		if(gameState == pauseState) {

		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		//title screen
		if(gameState == titleState){
			ui.draw(g2);
		}else{
			//tile
			tileM.draw(g2);
			//ADD entities to the List
			for(int i=0; i< NPC.length; i++){
				if(NPC[i]!= null){
					entityList.add(NPC[i]);
				}
			}
			for(int i=0; i<obj.length; i++){
				if(obj[i]!=null){
					entityList.add(obj[i]);
				}
			}
			for(int i=0; i<monster.length; i++){
				if(monster[i] != null){
					entityList.add(monster[i]);
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
			// Empty Entity List
			for(int i = 0; i < entityList.size(); i++){
				entityList.remove(i);
			}
			//UI
			ui.draw(g2);
		}
		g2.dispose();
	}

	public void playMusic(int i){
		sound.setFile(i);
		sound.play();
		sound.loop();
	}

	public void stopMusic(){
		sound.stop();
	}
	public void playSE(int i){
		sound.setFile(i);
		sound.play();
	}
}
