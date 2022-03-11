package main.states;

import main.Config;
import main.Game;
import main.button.Button;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MenuState extends State
{
    private Game game;
//    private SurvivalMenuState survivalMenuState;
//    Get the path from config.java
    private String buttonPath;
    private String backgroundPath;
    private String dottedPath;
    private String brokenSurvivalPath;
    private String brokenStoryPath;

    private BufferedImage backgroundImage;
    private BufferedImage brokenSurvivalImage;
    private BufferedImage brokenStoryImage;

//     Create Buttons
    private Button storyButton;
    private Button survivalButton;
    private Button exitButton;
    private Button disableStartButton;
    private Button disableCancelButton;

//    For Blinking Lights
    private Button defaultBG;
    private Button dottedBG;
    private Button brokenScreenBG;
    // TODO Auto-generated method stub


    public MenuState(Game game)
    {
        this.game = game;
        buttonPath = Config.MENU_BUTTON_ASSET_PATH;
        backgroundPath = Config.MENU_BACKGROUND_ASSET_PATH;
        dottedPath = Config.DOTTED_BACKGROUND_ASSET_PATH;
        brokenSurvivalPath = Config.BROKEN_SURVIVAL_BACKGROUND_ASSET_PATH;
        brokenStoryPath = Config.BROKEN_SOTRY_BACKGROUND_ASSET_PATH;

        backgroundImage = ImageLoader.loadImage(backgroundPath);
        brokenSurvivalImage = ImageLoader.loadImage(brokenSurvivalPath);
        brokenStoryImage = ImageLoader.loadImage(brokenStoryPath);

//        Coordinate in Frame
        survivalButton = new Button(game, new Point(364, 465), new Point(85, 50), "survival");
        storyButton = new Button(game, new Point(480, 465), new Point(85, 50), "story");
        exitButton = new Button(game, new Point(606, 465), new Point(85, 50), "exit");
        disableStartButton = new Button(game, new Point(435, 385), new Point(85, 50), "start");
        disableCancelButton = new Button(game, new Point(548, 385), new Point(85, 50), "cancel");
        dottedBG = new Button(game, new Point(169, 69), new Point(463, 222), "dot");
        defaultBG = new Button(game, new Point(169, 69), new Point(463, 222), "default");


//        Coordinate in Photos
        survivalButton.loadTexture(new Point(0, 0), new Point(173, 87), buttonPath);
        storyButton.loadTexture(new Point(522, 0), new Point(173, 87), buttonPath);
        exitButton.loadTexture(new Point(1044, 0), new Point(173, 87), buttonPath);
        disableStartButton.loadTexture(new Point(522, 264), new Point(173, 87), buttonPath);
        disableCancelButton.loadTexture(new Point(348, 264), new Point(173, 87), buttonPath);

        dottedBG.loadScreen(new Point(151, 55), new Point(419, 178), dottedPath);
        defaultBG.loadScreen(new Point(151, 55), new Point(419, 178), backgroundPath);


    }
    private double maxFrame1 = 20;
    private double deltaCounter1 = 0;
//    private boolean flag = false;
    private Random rand = new Random();
    private int random;

    private double maxFrame2 = 100;
    private double deltaCounter2 = 0;
    private boolean survivalIsClicked = false;
    private boolean storyIsClicked = false;

    private double maxFrame3 = 1000;
    private double deltaCounter3 = 0;
    private boolean storyNext = false;
    private boolean survivalNext = false;


    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();
//        int random = rand.nextInt(100);

        dottedBG.unhoveredImage();
        defaultBG.unhoveredImage();


//        deltaCounter1 += game.getDeltaPlease();
        if(deltaCounter1 >= maxFrame1){
            random = rand.nextInt(100);
//            flag = true;
            deltaCounter1 = 0;
        }
//        else{
//            flag = false;
//        }

        disableStartButton.unhoveredImage();
        disableCancelButton.unhoveredImage();

        if(survivalButton.isInside(x, y)){
            survivalButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {

                //Animate button
                survivalButton.clickedImage();
                survivalIsClicked = true;

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
                storyIsClicked = true;
            }
        }
        else{
            storyButton.unhoveredImage();
        }
//        System.out.println(game.getDeltaPlease());
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

//      Animation in main menu
        if(random % 2 == 0){
            dottedBG.draw(g);
        }
        else{
            defaultBG.draw(g);
        }


////        Broken survival BG
//        if(survivalIsClicked){
//            deltaCounter2 += game.getDeltaPlease();
//            if(deltaCounter2 > maxFrame2){
//                g.drawImage(brokenSurvivalImage, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
//                survivalNext = true;
//                deltaCounter2 = maxFrame2;
//            }
//        }
//
////        Broken story BG
//        if(storyIsClicked){
//            deltaCounter2 += game.getDeltaPlease();
//            if(deltaCounter2 > maxFrame2){
//                g.drawImage(brokenStoryImage, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
//                storyNext = true;
//                deltaCounter2 = maxFrame2;
//            }
//        }
//
////        Next state
//        if(survivalNext){
//            deltaCounter3 += game.getDeltaPlease();
//            if(deltaCounter3 > maxFrame3){
//                setState(new SurvivalMenuState(game));
//                deltaCounter3 = maxFrame3;
//            }
//        }
//        if(storyNext){
//            deltaCounter3 += game.getDeltaPlease();
//            if(deltaCounter3 > maxFrame3){
//                setState(new StoryMenuState(game));
//                deltaCounter3 = maxFrame3;
//            }
//        }
    }



//    public void delay(Graphics g, double delta){
//
//        deltaCounter += delta;
//        if(deltaCounter >= maxFrame){
//            blink(g);
//        }
//
//    }


//    public void blink(Graphics g){
//        g.drawImage(brokenImage, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
//    }

}
