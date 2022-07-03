package main;
import javax.swing.JFrame;
public class Main {
	public static void main(String[] args) {
		//commit dc ko vay  1
		// tạo 1 cửa sổ game
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D advanture");
		
		// đưa vào các hình ảnh của game
		GamePanel gamePanel = new GamePanel();
		gamePanel.setupGame();
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		
		gamePanel.startGameThread();	
	}

}
