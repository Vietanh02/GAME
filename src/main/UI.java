package main;

import entity.Entity;
import entity.object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.round;

public class UI {
    public String currentDialogue = "";
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    Font arial_80B;

    public boolean messageOn = false;
    public ArrayList<String> message = new ArrayList<>();

    public ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;

    double playTime;

    double startTime = System.nanoTime()/1000000000;

    DecimalFormat dFormat = new DecimalFormat("#0.00");

    BufferedImage heart_full, heart_half, heart_blank;
    public int commandNum = 0;
    public int titleScreenState = 0;

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN,40);
        arial_80B = new Font("Arial", Font.BOLD,80);

        //CREATE HUB OBJ
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.stay[0];
        heart_half = heart.stay[1];
        heart_blank = heart.stay[2];
    }
    public void addMessage(String text){
//        message = text;
//        messageOn = true;
        message.add(text);
        messageCounter.add(0);
    }
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    public void draw(Graphics2D g2){
        if(gameFinished){
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 -(gp.tileSize)*3;
             g2.drawString(text,x,y);

            text = "You time is: "+ dFormat.format(playTime);
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize)*4;
            g2.drawString(text,x,y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulation !!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize)*2;
            g2.drawString(text,x,y);

            gp.gameThread =null;
        }else{
            this.g2 = g2;
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            //titlestate
            if(gp.gameState == gp.titleState){
                drawTitleScreen();
            }

            //playstate
            if(gp.gameState == gp.playState){
                drawPlayerLife();
                drawMessage();
                //g2.drawString("Key = "+ gp.player.hasKey, 30, 30);
                //TIME
                playTime = (double)System.nanoTime()/1000000000-startTime;
                g2.drawString("Time:" + dFormat.format(playTime), gp.tileSize*11, 65);

               //Message
//                if(messageOn){
//                    g2.setFont(g2.getFont().deriveFont(30F));
//                    g2.drawString(message,gp.tileSize/2,gp.tileSize*5);
//                    messageCounter++;
//                    if(messageCounter>120){
//                        messageCounter = 0;
//                        messageOn = false;
//                    }
//                }
            }
            // pausestate
            if(gp.gameState == gp.pauseState){
                drawPlayerLife();
                drawPauseScreen();
            }
            // dialoguestate
            if(gp.gameState == gp.dialogueState){
                drawPlayerLife();
                drawDialogueScreen();

            }
            if(gp.gameState == gp.charaterState){
                drawCharacterScreen();
                //drawInventory();
            }
        }
    }

    private void drawMessage() {
        int messageX =  gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        for(int i = 0; i < message.size(); i++){
            if(message.get(i)!= null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i),messageX+2,messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i),messageX,messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 30;
                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    private void drawDialogueScreen(){//window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*5;
        drawSubWindow(x,y,width,height);
        x+=gp.tileSize;
        y+=gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
//        g2.drawString(currentDialogue,x,y);
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y+=40;
        }
    }


    // vẽ Thanh máu
    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        //draw maxlife
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x += gp.tileSize;
        }
        //reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        //draw current life
        while(i< gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i<gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+= gp.tileSize;
        }
    }
    public void drawTitleScreen(){
        //titlename
        if(titleScreenState == 0){
            g2.setColor(new Color(68, 123, 78));
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
            g2.drawImage(gp.player.getDownImage(), x, y,gp.tileSize*2, gp.tileSize*2, null);
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
            final int frameX = gp.screenWidth/2 - 120;
            final int frameY = gp.screenHeight/2 - 60;
            final int frameWidth = gp.tileSize * 5 ;
            final int frameHeight = gp.tileSize * 2;
            drawSubWindow(frameX, frameY, frameWidth, frameHeight);
            g2.drawString(text,x,y);
    }

    public void drawCharacterScreen(){

        //Create a frame
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 6 ;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //Text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        //Name
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("ATK", textX, textY);
        textY += lineHeight;
        g2.drawString("DEF", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 20 ;
        g2.drawString("Sheild", textX, textY);
        textY += lineHeight;

        // Values
        int tailX = (frameX + frameWidth) - 30;
        // reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getAttack());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getDefense());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getEXP());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getCoin());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down[0], tailX - gp.tileSize, textY-15, null );
        textY += lineHeight+20;
        g2.drawImage(gp.player.currentShield.down[0], tailX - gp.tileSize, textY-15, null );

}

    public void drawSubWindow(int x,int y,int width,int height){
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

//    public int getXforCenteredText(String text){
//        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//        int x = gp.screenWidth/2 - length/2;
//        return x;
//    }

    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
