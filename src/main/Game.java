package main;

import main.button.Button;
import main.display.Display;
import main.input.KeyManager;
import main.input.MouseManager;
import main.states.GameState;
import main.states.MenuState;
import main.states.State;
import main.states.SurvivalMenuState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class Game implements Runnable
{
    private final String MENUBUTTONPATH = "/assets/menu/menubuttons/menubuttonsv3.png";

    public State menuState;
    public State gameState;
    public State survivalMenuState;
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
        State.setState(survivalMenuState);
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

    public String getMENUBUTTONPATH() { return MENUBUTTONPATH; }

    public boolean isInside(float x, float y, Button butt, String buttonName) {

        Point getPos = butt.getImagePos();
//        x <= image.width + image.x && x >= image.x
//        y <= image.width + image.y && y >= image.y
        if((x <= butt.getSize().getX() + butt.getPos().getX() && x >=  butt.getPos().getX()) &&
                (y <= butt.getSize().getY() + butt.getPos().getY() && y >= butt.getPos().getY())){

            butt.setFrame(new Point((int) (butt.getImagePos().getX() + 174), (int) butt.getImagePos().getY()), new Point(173, 87));

//            If button clicked
            if(this.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
//              Animate button
                butt.setFrame(new Point((int) (butt.getImagePos().getX() + 174), (int) butt.getImagePos().getY()), new Point(173, 87));
                butt.setImagePos(getPos);
                if(buttonName == "story"){
                    System.out.println("Game Mode is Disable");
                    return false;
                }
                else if(buttonName == "survival"){
                    System.out.println("Survival Game");
                    return true;
                }
                else if(buttonName == "start"){
                    System.out.println("Start Game");
                    return true;
                }
                else if(buttonName == "cancel"){
                    System.out.println("Back to Menu");
                    return true;
                }
                else if(buttonName == "exit"){
                    butt.exitGameNotification();
                }
                return true;
            }
            butt.setImagePos(getPos);
            return false;
        }

        butt.setFrame(new Point((int) (butt.getImagePos().getX()), (int) butt.getImagePos().getY()), new Point(173, 87));
        butt.setImagePos(getPos);
        return false;
    }
}
