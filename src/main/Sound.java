package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;
    public Sound(){
        soundURL[0] = getClass().getResource("/sound/themeSound.wav");
        soundURL[1] = getClass().getResource("/sound/cursor.wav");
        soundURL[2] = getClass().getResource("/sound/coin.wav");
        soundURL[3] = getClass().getResource("/sound/power.wav");

        //them sound danh nhau
    }
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc =(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop((Clip.LOOP_CONTINUOUSLY));
    }
    public void stop(){
        clip.stop();
    }
    public void checkVolume(){
        switch (volumeScale){
            case 1: volume = -80f;break;
            case 2:volume = -20f;break;
            case 3:volume = -12f;break;
            case 4:volume = -5f;break;
            case 5:volume = 1f;break;
            case 6:volume = 6f;break;
        }
        fc.setValue(volume);
    }
}
