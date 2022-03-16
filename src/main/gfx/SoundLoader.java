package main.gfx;
import javafx.scene.shape.SVGPath;
import main.Config;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SoundLoader {

    public static void loopAudio(String filepath){
        try{
            File audioPath = new File(filepath);
            if(audioPath.exists()) {
                AudioInputStream audio = AudioSystem.getAudioInputStream(audioPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playAudio(String filepath) {
        InputStream audioFile;

        try {
            audioFile = new FileInputStream(new File(filepath));
            AudioStream audios = new AudioStream(audioFile);
            AudioPlayer.player.start(audios);
        } catch (Exception e) {

        }
    }


}


