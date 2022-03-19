package main.states;

import main.Config;
import main.Game;
import main.crop.Button;
import main.crop.Screen;
import main.crop.Speaker;
import main.gfx.AssetManager;
import main.gfx.Sound;
import main.states.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Vector;

import static java.lang.System.exit;

public class ExitState extends State {
    private final Game game;
    private final Button storyButton;
    private final Button survivalButton;
    private final Button exitButton;
    private final Button startButton;
    private final Button cancelButton;

    private final Speaker speaker;

    private final Vector<Sound> soundEffects;

    public ExitState(Game game)
    {
        this.game = game;
//        Coordinate in Frame
        survivalButton = new main.crop.Button(new Point(364, 465), new Point(85, 50), 0, "survival");
        storyButton = new main.crop.Button(new Point(480, 465), new Point(85, 50), 88, "story");
        exitButton = new main.crop.Button(new Point(606, 465), new Point(85, 50), 264, "exit");
        startButton = new main.crop.Button(new Point(435, 385), new Point(85, 50), 176, "start");
        cancelButton = new Button(new Point(548, 385), new Point(85, 50), 352, "cancel");

        speaker = new Speaker(new Point(Config.SCREEN_WIDTH - 55, Config.SCREEN_HEIGHT - 55), new Point(50, 50), 336, 192, "speaker");
        soundEffects = game.getSoundEffects();
    }

    private double startDelay = 0;
    private Boolean cancelClicked = false;
    private Boolean exitClicked = false;
    private Boolean exitIsClicked = false;

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        survivalButton.disabledImage();
        storyButton.disabledImage();
        exitButton.clickedImage();

        startDelay++;
        if(startDelay % Config.START_STORY_ANIMATION_DELAY == 0){
            exitClicked = true;
            startDelay = Config.START_STORY_ANIMATION_DELAY;
        }

        if(startButton.isInside(x, y)){
            startButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                startButton.clickedImage();
                exitIsClicked = true;
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
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
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);

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
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
            }
        }
        else{
            speaker.unhoveredImage();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(AssetManager.getInstance().getExitBGImage(), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
        startButton.draw(g);
        cancelButton.draw(g);
        storyButton.draw(g);
        survivalButton.draw(g);
        exitButton.draw(g);
        speaker.draw(g);

        if(cancelClicked){
            setState(new MenuState(game));
        }
        if(exitIsClicked){
            if(exitClicked){
                exit(0);
            }
        }
    }
}
