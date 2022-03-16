package main.states;

import main.Config;
import main.Game;
import main.crop.Button;
import main.crop.Speaker;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.event.MouseEvent;

public class StoryMenuState extends State{

    private Game game;
    private Button startButton;
    private Button cancelButton;
    private Button storyButton;
    private Button survivalButton;
    private Button exitButton;
    private Speaker speaker;


    public StoryMenuState(Game game){
        this.game = game;

//        Coordinate in Frame
        survivalButton = new Button(new Point(364, 465), new Point(85, 50), 0, "survival");
        startButton = new Button(new Point(435, 385), new Point(85, 50), 176, "start");
        storyButton = new Button(new Point(480, 465), new Point(85, 50), 88, "story");
        exitButton = new Button(new Point(606, 465), new Point(85, 50), 264, "exit");
        cancelButton = new Button(new Point(548, 385), new Point(85, 50), 352, "cancel");

        speaker = new Speaker(new Point(Config.SCREEN_WIDTH - 55, Config.SCREEN_HEIGHT - 55), new Point(50, 50), 336, 192, "speaker");

    }

    private double startDelay = 0;
    private Boolean cancelClicked = false;
    private Boolean startClicked = false;
    private Boolean storyIsClicked = false;

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        survivalButton.disabledImage();
        storyButton.clickedImage();
        exitButton.disabledImage();

        startDelay++;
        if(startDelay % Config.START_STORY_ANIMATION_DELAY == 0){
            startClicked = true;
            startDelay = Config.START_STORY_ANIMATION_DELAY;
        }

        if(startButton.isInside(x, y)){
            startButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                startButton.clickedImage();
                storyIsClicked = true;
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

        if(speaker.isInside(x, y)){
            speaker.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                speaker.clickedImage();
            }
        }
        else{
            speaker.unhoveredImage();
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
        speaker.draw(g);

        if(cancelClicked){
            setState(new MenuState(game));
        }
        if(storyIsClicked){
            g.drawImage(AssetManager.getInstance().getStoryBrokenBGImage(), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
            if(startClicked){
                setState(new MenuState(game));
            }
        }
    }
}
