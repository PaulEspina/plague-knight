package main.states;

import main.Config;
import main.Game;
import main.crop.Button;
import main.crop.ImageText;
import main.gfx.AssetManager;
import main.gfx.Sound;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class PauseState extends State{
    private final Game game;
    private final Button resumeButton;
    private final Button mainMenuButton;
    private final ImageText pauseText;

    private final Vector<Sound> soundEffects;

    public PauseState(Game game){
        this.game = game;

//        Coordinate in Frame
        resumeButton = new Button(new Point(450, 370), new Point(90, 55), 528, "resume");
        mainMenuButton = new Button(new Point(210, 370), new Point(90, 55), 440, "menu");
        pauseText = new ImageText(new Point(Config.SCREEN_WIDTH / 2 - Config.PAUSE_ASSET_WIDTH, 20), new Point(250, 100), "pause");

        soundEffects = game.getSoundEffects();
    }

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        pauseText.showPausedImage();
        if(resumeButton.isInside(x, y)){
            resumeButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                resumeButton.clickedImage();
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
            }
        }
        else{
            resumeButton.unhoveredImage();
        }

        if(mainMenuButton.isInside(x, y)){
            mainMenuButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                mainMenuButton.clickedImage();
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);

            }
        }
        else{
            mainMenuButton.unhoveredImage();
        }
    }

    @Override
    public void render(Graphics g) {
        pauseText.draw(g);
        resumeButton.draw(g);
        mainMenuButton.draw(g);
    }
}
