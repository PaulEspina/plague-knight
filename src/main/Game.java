package main;

import main.button.Button;
import main.display.Display;
import main.input.KeyManager;
import main.input.MouseManager;
import main.states.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class Game implements Runnable
{
    public State menuState;
    public State gameState;
    public State survivalMenuState;
    public State pauseState;
    private final String title;
    private final int width;
	private final int height;
    private Boolean running = false;
    private Display display;
    private BufferStrategy bs;
    private Graphics g;
    private Thread thread;
    private final KeyManager keyManager;
    private final MouseManager mouseManager;

    public Game(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;

        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    private void init()
    {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        menuState = new MenuState(this);
        gameState = new GameState(this);
        survivalMenuState = new SurvivalMenuState(this);
        pauseState = new PauseState(this);
        State.setState(menuState);
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
        g.clearRect(0, 0, width, height);

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


}
