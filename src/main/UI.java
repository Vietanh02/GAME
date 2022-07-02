package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    Font arial_80B;

    public int commandNum = 0;
    public int titleScreenState = 0;

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN,40);
        arial_80B = new Font("Arial", Font.BOLD,80);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        //titlestate
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //playstate
        if(gp.gameState == gp.playState){
            g2.drawString("Key = "+ gp.player.hasKey, 30, 30);
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }

    }
    public void drawTitleScreen(){
        //titlename
        if(titleScreenState == 0){
            g2.setColor(new Color(70,120,80));
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
            String text = "KIWI Adventure";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            //Shadow
            g2.setColor(Color.black);
            g2.drawString(text,x+5,y+5);
            //Main Color
            g2.setColor(Color.white);
            g2.drawString(text,x,y);
            //kiwi image
            x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down[1], x, y,gp.tileSize*2, gp.tileSize*2, null);
            //Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
            text = "New Game";
            x = getXforCenteredText(text);
            y += gp.tileSize*3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">",x - gp.tileSize,y);
            }

            text = "Load Game";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">",x - gp.tileSize,y);
            }

            text = "Quit";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">",x - gp.tileSize,y);
            }
        }else if(titleScreenState == 1){
                //class selection screen
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text,x,y);

            text = "Fighter";
            x = getXforCenteredText(text);
             y += gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNum == 0){
                g2.drawString(">", x -gp.tileSize,y);
            }

            text = "Thief";
             x = getXforCenteredText(text);
             y += gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNum == 1){
                g2.drawString(">", x -gp.tileSize,y);
            }

             text = "Sorcerer";
             x = getXforCenteredText(text);
             y += gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNum == 2){
                g2.drawString(">", x -gp.tileSize,y);
            }

             text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text,x,y);
            if(commandNum == 3){
                g2.drawString(">", x -gp.tileSize,y);
            }
        }
    }
    public void drawPauseScreen(){
            String text = "PAUSED";
            int x = getXforCenteredText(text);
            int y = gp.screenHeight/2;
            g2.drawString(text,x,y);
    }
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
