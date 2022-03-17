package main;

import main.display.Display;
import main.gfx.AssetManager;
import main.gfx.Sound;
import main.input.KeyManager;
import main.input.MouseManager;
import main.states.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Vector;

public class Game implements Runnable
{
    private Boolean running = false;
    private Display display;
    private BufferStrategy bs;
    private Graphics g;
    private Thread thread;
    private Thread garbageCollector;
    private final KeyManager keyManager;
    private final MouseManager mouseManager;

    private final Sound menuBGM;
    private final Sound survivalBGM;
    private final Vector<Sound> soundEffects;

    public Game()
    {
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        soundEffects = new Vector<>();
        menuBGM = new Sound(AssetManager.getInstance().getMenuBGM());
        menuBGM.setSound(-5);
        survivalBGM = new Sound(AssetManager.getInstance().getGameBGM());
        survivalBGM.setSound(-5);
    }

    private void init()
    {
        display = new Display();
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        State.setState(new MenuState(this));
    }

    private void tick()
    {
		if(State.getState() != null)
		{
			State.getState().tick();
		}

        keyManager.tick();
        mouseManager.tick();

    }


    private void render()
    {
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null)
        {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        g.clearRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        //Draw here

		if(State.getState() != null)
		{
			State.getState().render(g);
		}

        //End drawing here
        bs.show();
        g.dispose();
    }


    @Override
    public void run()
    {
        init();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;

            lastTime = now;
            while(delta >= 1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer+= 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public synchronized void start()
    {
		if(running)
		{
			return;
		}
        running = true;
        thread = new Thread(this);
        thread.start();
        garbageCollector = new Thread(new Runnable() {
            public void run()
            {
                while(true)
                {
                    System.out.println(soundEffects.toString());
                    soundEffects.removeIf(x -> !x.isPlaying());
                }
            }
        });
        garbageCollector.start();
    }

    public synchronized void stop()
    {
		if(!running)
		{
			return;
		}
        try
        {
            thread.join();
            garbageCollector.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public KeyManager getKeyManager()
    {
        return keyManager;
    }

    public MouseManager getMouseManager()
    {
        return mouseManager;
    }

    public Vector<Sound> getSoundEffects()
    {
        return soundEffects;
    }

    public Sound getMenuBGM()
    {
        return menuBGM;
    }

    public Sound getSurvivalBGM()
    {
        return survivalBGM;
    }
}
