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
    private Button startButton;
    private Button cancelButton;
    private Button survivalButton;
    private Button storyButton;
    private Button exitButton;


    public SurvivalMenuState(Game game){
        this.game = game;

//        Coordinate in Frame
        survivalButton = new Button(game, new Point(364, 465), new Point(85, 50), 0, "survival");
        storyButton = new Button(game, new Point(480, 465), new Point(85, 50), 88, "story");
        startButton = new Button(game, new Point(435, 385), new Point(85, 50), 176, "start");
        exitButton = new Button(game, new Point(606, 465), new Point(85, 50), 264, "exit");
        cancelButton = new Button(game, new Point(548, 385), new Point(85, 50), 352, "cancel");

    }

    private double cancelDelay = 0;
    private Boolean cancelClicked = false;
    private Boolean startClicked = false;

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        survivalButton.disabledImage();
        storyButton.disabledImage();
        exitButton.disabledImage();

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

                startClicked = true;
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
        storyButton.draw(g);
        survivalButton.draw(g);
        exitButton.draw(g);

        if(cancelClicked){
            setState(new MenuState(game));
        }

        if(startClicked){
            setState(new GameState(game));
        }
    }

}
