package main.gfx;

import main.Config;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;

public class Sound
{
    private Clip clip;
    private boolean playing;

    private final AudioInputStream audio;

    public Sound(AudioInputStream audio)
    {
        this.audio = audio;
        playing = false;
        try
        {
            clip = AudioSystem.getClip();
            clip.open(audio);
        }
        catch(LineUnavailableException | IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loop()
    {
        playing = true;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play()
    {
        playing = true;
        clip.start();
    }

    public void stop()
    {
        playing = false;
        clip.setMicrosecondPosition(0);
        clip.stop();
    }

    public void pause()
    {
        playing = false;
        clip.stop();
    }

    public boolean isPlaying()
    {
        if(clip.getMicrosecondPosition() == clip.getMicrosecondLength())
        {
            stop();
        }
        return playing;
    }

    public void setSound(float db){
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(db);
    }

    public void dispose()
    {
        clip.stop();
        clip.flush();
        clip.close();
        try
        {
            audio.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
