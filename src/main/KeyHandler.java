package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
	public boolean upPressed,downPressed,leftPressed,rightPressed, enterPressed, shotKeyPressed;

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
		else if(gp.gameState == gp.optionsState){
			optionsState(code);
		}//Game over STATE
		else if(gp.gameState == gp.gameOverState){
			gameOverState(code);
		}
		else if(gp.gameState == gp.gameFinishedState){
			if(code == KeyEvent.VK_ENTER){
				gp.gameState = gp.titleState;
			}
		}
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
					gp.gameState = gp.playState;
					//gp.playMusic(0);
				}
				if (gp.ui.commandNum == 1) {

				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
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
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (code == KeyEvent.VK_C){
			gp.gameState = gp.charaterState;
		}
		if(code == KeyEvent.VK_J){
			shotKeyPressed = true;
		}
		if (code == KeyEvent.VK_ESCAPE){
			gp.gameState = gp.optionsState;
  }
		if(code == KeyEvent.VK_J){
			shotKeyPressed = true;
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
			gp.playSE(1);
		}
		if(code == KeyEvent.VK_W){
			if(gp.ui.slotRow != 0) {
				gp.ui.slotRow--;
				gp.playSE(1);
			}
		}
		if(code == KeyEvent.VK_A){
			if(gp.ui.slotCol != 0 ) {
				gp.ui.slotCol--;
				gp.playSE(1);
			}
		}
		if(code == KeyEvent.VK_S){
			if(gp.ui.slotRow != 5) {
				gp.ui.slotRow++;
				gp.playSE(1);
			}
		}
		if(code == KeyEvent.VK_D){
			if(gp.ui.slotCol != 7) {
				gp.ui.slotCol++;
				gp.playSE(1);
			}
		}
		if(code == KeyEvent.VK_ENTER){
			gp.player.sellectItem();
		}

	}
	public void optionsState (int code){
		int maxCommandNum = 0;
		switch (gp.ui.subState){
			case 0: maxCommandNum = 5; break;
			case 3: maxCommandNum = 1; break;
		}
		if(code == KeyEvent.VK_ESCAPE){
			gp.gameState = gp.playState;
			gp.playSE(1);
		}
		if(code == KeyEvent.VK_W){
			gp.ui.commandNum--;
			gp.playSE(1);
			if (gp.ui.commandNum < 0){
				gp.ui.commandNum = maxCommandNum ;
			}
		}
		if(code == KeyEvent.VK_S){
			gp.ui.commandNum++;
			gp.playSE(1);
			if (gp.ui.commandNum > maxCommandNum){
				gp.ui.commandNum = 0 ;
			}
		}
		if(code == KeyEvent.VK_ENTER){
			enterPressed = true;
		}
		if(code == KeyEvent.VK_A){
			if (gp.ui.subState == 0){
				if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
					gp.music.volumeScale --;
					gp.music.checkVolume();
					gp.playSE(1);
				}
				if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0){
					gp.se.volumeScale --;
					gp.playSE(1);
				}
			}
		}
		if(code == KeyEvent.VK_D){
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(1);
				}
				if (gp.ui.commandNum == 2&& gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
					gp.playSE(1);
				}
			}
		}
	}
	public void gameOverState(int code){
		if(code == KeyEvent.VK_W){
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			gp.playSE(1);
		}
		if(code == KeyEvent.VK_S){
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(1);
		}
		if(code == KeyEvent.VK_ENTER){
			gp.ui.commandNum++;
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
			}
			else {
				gp.gameState = gp.titleState;
			}
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
		if(code == KeyEvent.VK_J) {
			shotKeyPressed = false;
		}
	}

}
