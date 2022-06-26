package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

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
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize*maxScreenCol; //768 pixel
	public final int screenHeight =  tileSize*maxScreenRow; // 576 // pixel
	
	//WORLD SETTING 
	public final int maxWorldCol = 32;
	public final int maxWorldRow = 21;
	public final int WorldWidth = tileSize * maxWorldCol;
	public final int WorldHeight = tileSize * maxWorldRow;
	
	
	//FPS
	int FPS = 60;
	
	// bản đồ và cỏ, tường nước ( tile
	TileManager tileM = new TileManager(this);
	
	// keyHandle -- các nút điều khiển
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	// xác định va chạm của nhân vật với tường và nước
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Player player = new Player(this,keyH);
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// hàm run trong thư viện giúp lặp lại việc vẽ. Vẽ 60 bức trong 1s để tạo chuyển động
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long  timer = 0;
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
				System.out.println("FPS:"+drawCount);
				drawCount++;
				drawCount =0;
				timer =0;
			}
			
		}
	}
/* public void run() {
		
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			update();
			repaint();
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				if(remainingTime <0) {
					remainingTime =0;
				}
				
				Thread.sleep((long)remainingTime);
				nextDrawTime += drawInterval;
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}*/	
	
	// vẽ nhân vật
	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.draw(g2);
		player.draw(g2);
		g2.dispose();
	}
	
	
}
