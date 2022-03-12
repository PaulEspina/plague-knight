package main.states;

import main.Config;
import main.Game;
import main.button.Button;
import main.gfx.AssetManager;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class SurvivalMenuState extends State{

    private Game game;
    private String buttonPath;
    private String backgroundPath;
    private Button startButton;
    private Button cancelButton;
    private Button survivalButton;
    private Button disableStoryButton;
    private Button disableExitButton;


    public SurvivalMenuState(Game game){
        this.game = game;
        buttonPath = Config.MENU_BUTTON_ASSET_PATH;
        backgroundPath = Config.MENU_BACKGROUND_ASSET_PATH;

//        Coordinate in Frame
        startButton = new Button(game, new Point(435, 385), new Point(85, 50), "start");
        cancelButton = new Button(game, new Point(548, 385), new Point(85, 50), "cancel");
        survivalButton = new Button(game, new Point(364, 465), new Point(85, 50), "survival");
        disableStoryButton = new Button(game, new Point(480, 465), new Point(85, 50), "story");
        disableExitButton = new Button(game, new Point(606, 465), new Point(85, 50), "exit");


//        Coordinate in Photos
        startButton.loadTexture(new Point(0, 176), new Point(173, 87), buttonPath);
        cancelButton.loadTexture(new Point(522, 176), new Point(173, 87), buttonPath);
        survivalButton.loadTexture(new Point(348, 0), new Point(173, 87), buttonPath);
        disableStoryButton.loadTexture(new Point(174, 264), new Point(173, 87), buttonPath);
        disableExitButton.loadTexture(new Point(348, 264), new Point(173, 87), buttonPath);

    }

    private double cancelDelay = 0;
    private Boolean cancelClicked = false;

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        survivalButton.unhoveredImage();
        disableStoryButton.unhoveredImage();
        disableExitButton.unhoveredImage();

        cancelDelay++;
        if(cancelDelay % Config.CANCEL_ANIMATION_DELAY == 0){
            cancelDelay = Config.CANCEL_ANIMATION_DELAY;
        }

        if(startButton.isInside(x, y)){
            startButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                startButton.clickedImage();
            }
        }
        else{
            startButton.unhoveredImage();
        }
        if(cancelButton.isInside(x, y)){
            cancelButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                cancelButton.clickedImage();

                cancelClicked = true;
            }
        }
        else{
            cancelButton.unhoveredImage();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(AssetManager.getInstance().getDefaultBGImage(), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
        startButton.draw(g);
        cancelButton.draw(g);
        disableStoryButton.draw(g);
        survivalButton.draw(g);
        disableExitButton.draw(g);

        if(cancelClicked){
            setState(new MenuState(game));
        }
    }

}
