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

public class AudioLoader
{

    public static File loadAudio(String filepath){
        try
        {
            return new File(filepath);
        }
        catch(NullPointerException e)
        {
            System.err.println("Audio file not found.");
            return null;
        }
    }
}


