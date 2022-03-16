package main.gfx;

import main.Config;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.io.IOException;

public class Sound
{
    private Clip clip;
    private boolean playing;

    public Sound(AudioInputStream audio)
    {
        try
        {
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(LineUnavailableException | IOException e)
        {
            e.printStackTrace();
        }
    }

    public void play()
    {
        playing = true;
        clip.start();
    }

    public void stop()
    {
        playing = false;
        clip.stop();
    }

    public boolean isPlaying()
    {
        return playing;
    }
}
