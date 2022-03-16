package main.gfx;

import main.Config;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;

public class Sound
{
    private Clip clip;
    private boolean playing;
    private AudioInputStream audio;

    public Sound(AudioInputStream audio)
    {
        this.audio = audio;
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
    public void setFramePosition(int framePosition){
        clip.setFramePosition(framePosition);
    }
    public void loop()
    {
        playing = true;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void play()
    {
        playing = true;
        if(!clip.isOpen()){
            try {
                clip.open(audio);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void setSound(float db){
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(db);
    }
}
