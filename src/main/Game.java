package main;

import main.display.Display;
import main.input.KeyManager;
import main.input.MouseManager;
import main.states.*;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable
{
    public State menuState;
    public State gameState;

    public State survivalMenuState;
    public State pauseState;

    private Boolean running = false;
    private Display display;
    private BufferStrategy bs;
    private Graphics g;
    private Thread thread;
    private final KeyManager keyManager;
    private final MouseManager mouseManager;


    public Game()
    {
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    private void init()
    {
        display = new Display();
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

//    DINAGDAG KO TONG VARIABLE
    private double deltaPlease;

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

//            LINAGAY KO TO LINE 108
            this.deltaPlease = delta;

//            System.out.println("delta: " + delta);
//            System.out.println("LAST TIME: " + lastTime);
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
//                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

//    I MADE THIS FUNCTION
    public double getDeltaPlease(){
        return deltaPlease;
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
