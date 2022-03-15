package main.states;

import main.Config;
import main.Game;
import main.crop.Button;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.event.MouseEvent;


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
        survivalButton = new Button(new Point(364, 465), new Point(85, 50), 0, "survival");
        storyButton = new Button(new Point(480, 465), new Point(85, 50), 88, "story");
        startButton = new Button(new Point(435, 385), new Point(85, 50), 176, "start");
        exitButton = new Button(new Point(606, 465), new Point(85, 50), 264, "exit");
        cancelButton = new Button(new Point(548, 385), new Point(85, 50), 352, "cancel");

    }

    private double startDelay = 0;
    private Boolean cancelClicked = false;
    private Boolean startClicked = false;
    private Boolean survivalIsClicked = false;

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        survivalButton.clickedImage();
        storyButton.disabledImage();
        exitButton.disabledImage();

        startDelay++;
        if(startDelay % Config.START_SURVIVAL_ANIMATION_DELAY == 0){
            startClicked = true;
            startDelay = Config.START_SURVIVAL_ANIMATION_DELAY;
        }

        if(startButton.isInside(x, y)){
            startButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                startButton.clickedImage();
                survivalIsClicked = true;

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

        //        Broken survival BG
        if(survivalIsClicked){
            g.drawImage(AssetManager.getInstance().getSurvivalBrokenBGImage(), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
            if(startClicked){
                setState(new GameState(game));
            }
        }

    }

}
