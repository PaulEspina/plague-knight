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
    private final KeyManager keyManager;
    private final MouseManager mouseManager;

    private State menuState;
    private State gameState;
    private State survivalMenuState;
    private State pauseState;
    private State leaderBoardState;

    private Vector<Sound> sounds;
    private Sound backgroundMusic;
    private Sound inGameMusic;
    private Sound knifeSound;
    private Sound enterPressSound;
    private Sound buttonPressSound;
    private Sound zombie1BG;
    private Sound zombie2BG;
    private Sound zombie3BG;

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

        backgroundMusic = new Sound(AssetManager.getInstance().getMainMenuBG());
        backgroundMusic.setSound(-5);
        backgroundMusic.loop();

        inGameMusic = new Sound(AssetManager.getInstance().getInGameBG());
        inGameMusic.setSound(-80);
        inGameMusic.loop();

        knifeSound = new Sound(AssetManager.getInstance().getKnifeBG());


        enterPressSound = new Sound(AssetManager.getInstance().getEnterPressBG());
//        enterPressSound.setSound(-80);
//        enterPressSound.loop();

        buttonPressSound = new Sound(AssetManager.getInstance().getButtonPressBG());
//        buttonPressSound.setSound(-80);
//        buttonPressSound.loop();

        zombie1BG = new Sound(AssetManager.getInstance().getZombie1BG());
        zombie1BG.setSound(-80);
        zombie1BG.loop();

        zombie2BG = new Sound(AssetManager.getInstance().getZombie2BG());
        zombie2BG.setSound(-80);
        zombie2BG.loop();

        zombie3BG = new Sound(AssetManager.getInstance().getZombie3BG());
        zombie3BG.setSound(-80);
        zombie3BG.loop();

        menuState = new MenuState(this);
        gameState = new GameState(this);
        survivalMenuState = new SurvivalMenuState(this);
        pauseState = new PauseState(this);
        leaderBoardState = new LeaderBoardState(this);

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

    public Sound getBackgroundMusic() {
        return backgroundMusic;
    }

    public Sound getInGameMusic() {
        return inGameMusic;
    }

    public Sound getKnifeSound() {
        return knifeSound;
    }

    public Sound getEnterPressSound() {
        return enterPressSound;
    }

    public Sound getButtonPressSound() {
        return buttonPressSound;
    }

    public Sound getZombie1BG() {
        return zombie1BG;
    }

    public Sound getZombie2BG() {
        return zombie2BG;
    }

    public Sound getZombie3BG() {
        return zombie3BG;
    }
}
