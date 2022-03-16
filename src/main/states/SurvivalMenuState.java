package main.states;

import main.Config;
import main.Game;
import main.crop.Button;
import main.crop.Difficulty;
import main.crop.Speaker;
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

    private Difficulty easyButton;
    private Difficulty hardButton;

    private Speaker speaker;

    private double startDelay = 0;
    private Boolean cancelClicked = false;
    private Boolean startClicked = false;
    private Boolean survivalIsClicked = false;
    private Boolean changeEasyToClicked;
    private Boolean changeHardToClicked = false;

    public SurvivalMenuState(Game game){
        this.game = game;

        changeEasyToClicked = true;

    //        Coordinate in Frame
        survivalButton = new Button(new Point(364, 465), new Point(85, 50), 0, "survival");
        storyButton = new Button(new Point(480, 465), new Point(85, 50), 88, "story");
        startButton = new Button(new Point(435, 385), new Point(85, 50), 176, "start");
        exitButton = new Button(new Point(606, 465), new Point(85, 50), 264, "exit");
        cancelButton = new Button(new Point(548, 385), new Point(85, 50), 352, "cancel");

        easyButton = new Difficulty(new Point(300, 310), new Point(85, 50), 0, "easy");
        hardButton = new Difficulty(new Point(410, 310), new Point(85, 50), 88, "hard");

        speaker = new Speaker(new Point(Config.SCREEN_WIDTH - 55, Config.SCREEN_HEIGHT - 55), new Point(50, 50), 336, 192, "speaker");

    }

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

        if(easyButton.isInside(x, y)){
            easyButton.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                easyButton.clickedImage();
//                game.getButtonPressSound().play();
                GameSetting.gameDifficulty = false;
                game.getButtonPressSound().setSound(-10);
                game.getButtonPressSound().play();
                game.getButtonPressSound().setFramePosition(0);
                changeEasyToClicked = true;
                changeHardToClicked = false;
            }
        }
        else{
            if(changeEasyToClicked){
                easyButton.clickedImage();
            }
            else{
                easyButton.unhoveredImage();
            }
        }

        if(hardButton.isInside(x, y)){
            hardButton.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                hardButton.clickedImage();
                game.getButtonPressSound().setSound(-10);
                game.getButtonPressSound().play();
                game.getButtonPressSound().setFramePosition(0);
                GameSetting.gameDifficulty = true;
                changeHardToClicked = true;
                changeEasyToClicked = false;
            }
        }
        else{
            if(changeHardToClicked){
                hardButton.clickedImage();
            }
            else{
                hardButton.unhoveredImage();
            }
        }

        if(startButton.isInside(x, y)){
            startButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                game.getButtonPressSound().setSound(-10);
                game.getButtonPressSound().play();
                game.getButtonPressSound().setFramePosition(0);
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
                game.getButtonPressSound().setSound(-10);
                game.getButtonPressSound().play();
                game.getButtonPressSound().setFramePosition(0);
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
                game.getButtonPressSound().setSound(-10);
                game.getButtonPressSound().play();
                game.getButtonPressSound().setFramePosition(0);
                if(game.getBackgroundMusic().isPlaying())
                {
                    game.getBackgroundMusic().stop();
                }
                else
                {
                    game.getBackgroundMusic().play();
                }
            }
        }
        else{
            if(game.getBackgroundMusic().isPlaying())
            {
                speaker.unhoveredImage();
            }
            else
            {
                speaker.clickedImage();
            }
        }
        if(survivalIsClicked && startClicked)
        {
            setState(new GameState(game));
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

        easyButton.draw(g);
        hardButton.draw(g);
        if(cancelClicked){
            setState(new MenuState(game));
        }

        //        Broken survival BG
        if(survivalIsClicked){
            game.getEnterPressSound().setSound(-10);
            game.getEnterPressSound().play();
            game.getEnterPressSound().setFramePosition(0);
            g.drawImage(AssetManager.getInstance().getSurvivalBrokenBGImage(), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
        }
    }

}
