package main;

import entity.Entity;
import entity.object.OBJ_Heart;
import entity.object.OBJ_ManaCrystal;

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
    BufferedImage crystal_full, crystal_half, crystal_blank;
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int slotCol = 0;
    public int slotRow = 0;
    int subState = 0;


    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN,40);
        arial_80B = new Font("Arial", Font.BOLD,80);

        //CREATE HUB OBJ
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.stay[0];


        heart_half = heart.stay[1];
        heart_blank = heart.stay[2];

        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.stay[0];
        crystal_half = crystal.stay[1];
        crystal_blank = crystal.stay[2];

       // heart_half = heart.setup("/objects/heart_half", gp.tileSize, gp.tileSize);
       // heart_blank = heart.setup("/objects/heart_blank", gp.tileSize, gp.tileSize);

       // Entity crystal = new OBJ_ManaCrystal(gp);
       // crystal_full = crystal.stay[0];
       // crystal_blank = crystal.setup("/objects/crystal_blank", gp.tileSize, gp.tileSize);

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

            //title State
            if(gp.gameState == gp.titleState){
                drawTitleScreen();
            }

            //play State
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
            // pause State
            if(gp.gameState == gp.pauseState){
                drawPlayerLife();
                drawPauseScreen();
            }
            // dialogue State
            if(gp.gameState == gp.dialogueState){
                drawPlayerLife();
                drawDialogueScreen();

            }
            // chararacter State
            if(gp.gameState == gp.charaterState){
                drawCharacterScreen();
                drawInventory();
            }
            //options State
            if (gp.gameState == gp.optionsState){
                drawOptionsScreen();
            }
            //game over State
            if (gp.gameState == gp.gameOverState){
                drawGameOverScreen();
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
        x = gp.tileSize/2;
        y = gp.tileSize*2;
        i = 0;
        //draw current life


        while(i < gp.player.maxMana/2){
            g2.drawImage(crystal_blank,x,y,null);
            i++;
            x += gp.tileSize;

        // while(i < gp.player.maxMana){
        //    g2.drawImage(crystal_blank,x,y,null);
        //    i++;
        //   x += 35;

        }
        //reset
        x = gp.tileSize/2;
        y = gp.tileSize*2;
        i = 0;
        //draw current life
        while(i< gp.player.mana){
            g2.drawImage(crystal_half,x,y,null);
            i++;
            if(i<gp.player.mana){
                g2.drawImage(crystal_full,x,y,null);
            }
            i++;
            x+= gp.tileSize;

           // g2.drawImage(crystal_full,x,y,null);
           // i++;
           // x+= 35;
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
        g2.drawString("Mana", textX, textY);
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
        g2.drawString("Next Level Exp", textX, textY);
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

        value = String.valueOf(gp.player.getLevel() );
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.life + "/" + gp.player.maxLife;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.mana + "/" + gp.player.maxMana;
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getAtk());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getDef());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getEXP());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getNextLevelExp());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getCoin());
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        if(gp.player.currentWeapon != null){
            g2.drawImage(gp.player.currentWeapon.stay[0], tailX - gp.tileSize, textY - 25, null);
        }
            textY += lineHeight + 20;
        if(gp.player.currentShield != null){
            g2.drawImage(gp.player.currentShield.stay[0], tailX - gp.tileSize, textY - 25, null);
        }
}

    public void drawInventory(){
        int frameWidth = gp.tileSize * 9;
        int frameHeight = gp.tileSize *7 ;
        int frameX = gp.screenWidth - frameWidth - gp.tileSize;
        int frameY =gp.tileSize;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize ;

        //draw player's items
        for (int i = 0 ; i< gp.player.inventory.size(); i++){

            //equip cursor
            if( gp.player.inventory.get(i) == gp.player.currentWeapon ||
                gp.player.inventory.get(i) == gp.player.currentShield){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX, slotY,gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down[0], slotX, slotY, null);
            g2.drawImage(gp.player.inventory.get(i).stay[0], slotX, slotY, null);
            slotX +=  slotSize;

            if( i == 7 || i == 15 || i == 23 || i == 31 || i == 39 || i == 47 ){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //cursor
        int cursorX = slotXstart + (gp.tileSize * slotCol);
        int cursorY = slotYstart + (gp.tileSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //draw cursor
        g2.setColor(Color.white);
        g2. setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //discription frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeigth = gp.tileSize * 3;

        //draw discription

        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot();

        if (itemIndex< gp.player.inventory.size()){
            drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeigth);
            for (String line: gp.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }

    }
    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        //shadow
        text = "Game Over";
        g2.setColor(Color.BLACK);
        x = gp.tileSize + getXforCenteredText(text);
        y = 4 * gp.tileSize;
        g2.drawString(text, x, y);
        //main
        g2.setColor(Color.white);
        g2.drawString(text, x-5, y-5);
        //retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = gp.tileSize + getXforCenteredText(text);
        y += 7 * gp.tileSize;
        if(commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        g2.drawString(text, x, y);
        // Quit
        text = "Quit";
        y += gp.tileSize + 15;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }

    }
    public void drawOptionsScreen(){
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        //sub window
        int frameWidth = 12 * gp.tileSize;
        int frameHeight = 9 * gp.tileSize;
        int frameX = 7 * gp.tileSize;
        int frameY = 2 * gp.tileSize;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState){
            case 0: option_top(frameX, frameY); break;

            case 1: options_fullScreenNotification(frameX, frameY); break;

            case 2: options_control(frameX, frameY); break;

            case 3: options_endGameConfirmation(frameX,frameY); break;
        }
        gp.keyH.enterPressed = false;

    }

    public void option_top(int frameX, int frameY){

        int textX;
        int textY;

        //Title
        String text = "Options";
        textX = gp.tileSize + getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // full screen on/off
        textX = frameX +  gp.tileSize;
        textY = frameY + 2 * gp.tileSize;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25,textY);
            if(gp.keyH.enterPressed == true){
                if(gp.fullScreen == false){gp.fullScreen =true;}
                else {gp.fullScreen = false;}
                subState = 1;
            }
        }

        // Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25,textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25,textY);
        }

        //Control
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25,textY);
            if (gp.keyH.enterPressed == true) {
                subState = 2;
                commandNum = 0;
            }
        }

        // End Game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25,textY);
            if(gp.keyH.enterPressed == true) {
                subState = 3;
                commandNum = 0;
            }
        }

        //Back
        textY += 2 * gp.tileSize;
        g2.drawString("Back", textX, textY);
        if (commandNum == 5) {
            g2.drawString(">", textX - 25,textY);
            if (gp.keyH.enterPressed == true){
                gp.gameState = gp.playState;
            }
        }

        // Full Screen Check Box
        textX = frameX + gp.tileSize * 8;
        textY = frameY + gp.tileSize + 25;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 25, 25);
        if(gp.fullScreen == true){
            g2.fillRect(textX, textY, 25, 25);
        }

        //Music Volume
        textY += gp.tileSize;
        g2.drawRect(textX,textY, 125, 25);
        int volumeWidth = 25 *gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 25);


        //SE Volume
        textY += gp.tileSize;
        g2.drawRect(textX,textY, 125, 25);
        volumeWidth = 25 *gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 25);

    }
    public void options_fullScreenNotification(int frameX, int frameY){

        int textX = frameX + gp.tileSize;
        int textY = frameY + 2 * gp.tileSize;

        currentDialogue = "The change will take effect after\n restarting the game.";
        for (String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
            textX += 2 *gp.tileSize;
        }

        // Back
        textX = frameX + gp.tileSize;
        textY = frameY + 8 * gp.tileSize;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 0;
            }
        }

    }
    public void options_control(int frameX, int frameY){
        int textX;
        int textY;

        //title
        String text = "Control";
        textX = gp.tileSize + getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;

        textX = frameX + 8 * gp.tileSize;
        textY = frameY + 2 * gp.tileSize;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;

        // Back
        textX = frameX + gp.tileSize;
        textY = frameY + 8 * gp.tileSize;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 3;
            }
        }
    }
    public void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + 2 * gp.tileSize;

        currentDialogue = "Quit the game and return to the\n title screen?";
        for (String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
            textX += 3 * gp.tileSize;
        }
        //Yes
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += 2 * gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }

        //No
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public int getItemIndexOnSlot(){
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
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


    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
