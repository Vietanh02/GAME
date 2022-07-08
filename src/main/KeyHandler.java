package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
	public boolean upPressed,downPressed,leftPressed,rightPressed, enterPressed;

	public  KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		//title state
		if(gp.gameState == gp.titleState) {
			tittleState(code);
		}
		if(gp.gameState == gp.playState) {
			playState(code);
		 }
		//pause state
		else if(gp.gameState == gp.pauseState){
			pauseState(code);
		}
		//dialogue state
		else if(gp.gameState == gp.dialogueState){
			dialogueState(code);
		}
		//character state
		else if(gp.gameState == gp.charaterState){
			characterState(code);
		}
		//OPTIONS STATE
	}

	public void tittleState(int code){
		if (gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) gp.ui.commandNum = 2;
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) gp.ui.commandNum = 0;
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
					//gp.playMusic(0);
				}
				if (gp.ui.commandNum == 1) {

				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
		} else if (gp.ui.titleScreenState == 1) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) gp.ui.commandNum = 3;
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 3) gp.ui.commandNum = 0;
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					System.out.print("Do something");
					gp.gameState = gp.playState;
					//gp.playMusic(0);
				}
				if (gp.ui.commandNum == 1) {
					System.out.print("Do something");
					gp.gameState = gp.playState;
				}
				if (gp.ui.commandNum == 2) {
					System.out.print("Do something");
					gp.gameState = gp.playState;
				}
				if (gp.ui.commandNum == 3) {
					gp.ui.titleScreenState = 0;
				}
			}
		}
	}
	public void playState(int code){
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if(code == KeyEvent.VK_ENTER){
			enterPressed = true;
		}
		if(code == KeyEvent.VK_C){
			gp.gameState = gp.charaterState;
		}
	}
	// Cancel state
	public void pauseState( int code){
		if(code == KeyEvent.VK_P){
			gp.gameState = gp.playState;
		}
	}
	public void dialogueState (int code){
		if(code == KeyEvent.VK_ENTER){
			gp.gameState = gp.playState;
		}
	}
	public void characterState(int code){
		if(code == KeyEvent.VK_C){
			gp.gameState = gp.playState;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		// cac phim W A S D
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
	}

}
