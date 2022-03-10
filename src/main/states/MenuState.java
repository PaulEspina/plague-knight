package main.states;

import main.Config;
import main.Game;
import main.button.Button;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.lang.Thread.sleep;

public class MenuState extends State
{
    private Game game;
//    Get the path from config.java
    private String buttonPath;
    private String backgroundPath;
    private BufferedImage backgroundImage;

//     Create Buttons
    private Button storyButton;
    private Button survivalButton;
    private Button exitButton;
    private Button disableStartButton;
    private Button disableCancelButton;

//    For Blinking Lights
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    // TODO Auto-generated method stub


    public MenuState(Game game)
    {
        this.game = game;
        buttonPath = Config.MENUBUTTONPATH;
        backgroundPath = Config.MENUBACKGROUNDPATH;
        backgroundImage = ImageLoader.loadImage(backgroundPath);
//        Coordinate in Frame
        survivalButton = new Button(game, new Point(364, 465), new Point(85, 50), "survival");
        storyButton = new Button(game, new Point(480, 465), new Point(85, 50), "story");
        exitButton = new Button(game, new Point(606, 465), new Point(85, 50), "exit");
        disableStartButton = new Button(game, new Point(435, 385), new Point(85, 50), "start");
        disableCancelButton = new Button(game, new Point(548, 385), new Point(85, 50), "cancel");

//        Coordinate in Photos
        survivalButton.loadTexture(new Point(0, 0), new Point(173, 87), buttonPath);
        storyButton.loadTexture(new Point(522, 0), new Point(173, 87), buttonPath);
        exitButton.loadTexture(new Point(1044, 0), new Point(173, 87), buttonPath);
        disableStartButton.loadTexture(new Point(522, 264), new Point(173, 87), buttonPath);
        disableCancelButton.loadTexture(new Point(348, 264), new Point(173, 87), buttonPath);

//    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//        @Override
//        public void run() {
//            repaint();
//        }
//    })
    }

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        disableStartButton.unhoveredImage();
        disableCancelButton.unhoveredImage();

        if(survivalButton.isInside(x, y)){
            survivalButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                survivalButton.clickedImage();
            }
        }
        else{
            survivalButton.unhoveredImage();
        }

        if(storyButton.isInside(x, y)){
            storyButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                storyButton.clickedImage();
            }
        }
        else{
            storyButton.unhoveredImage();
        }

        if(exitButton.isInside(x, y)){
            exitButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                exitButton.clickedImage();
            }
        }
        else{
            exitButton.unhoveredImage();
        }

    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(backgroundImage, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
        survivalButton.draw(g);
        storyButton.draw(g);
        exitButton.draw(g);
        disableStartButton.draw(g);
        disableCancelButton.draw(g);
        delay(g, game.getDeltaPlease());
    }

//    Ito mga binago ko :D
    private double maxFrame = 20;
    private double deltaCounter = 0;
    public void delay(Graphics g, double delta){

        deltaCounter += delta;
        System.out.println(delta);
        if(deltaCounter >= maxFrame){
//            blink(g);
            deltaCounter = 0;
        }
        else{
//            blink(g);
        }
    }

//    UNTIL HERE


    public void blink(Graphics g){
        g.setColor(new Color(74, 185, 0));
        g.fillRect(169, 69, 463, 222);
    }

}
