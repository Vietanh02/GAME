package main;

import javax.swing.*;

public class Main {
	public static JFrame window;
	public static void main(String[] args) {
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D advanture");
		window.setUndecorated(true);

		// đưa vào các hình ảnh của game
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}
