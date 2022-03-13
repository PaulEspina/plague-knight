package main.states;

import main.Config;
import main.Game;
import main.button.Button;
import main.button.ImageText;
import main.button.Pause;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PauseState extends State{
    private Game game;
    private Button resumeButton;
    private Button mainMenuButton;
    private ImageText pauseText;

    public PauseState(Game game){
        this.game = game;

//        Coordinate in Frame
        resumeButton = new Button(game, new Point(450, 370), new Point(90, 55), 528, "resume");
        mainMenuButton = new Button(game, new Point(210, 370), new Point(90, 55), 440, "menu");
        pauseText = new ImageText(game, new Point(Config.SCREEN_WIDTH / 2 - Config.PAUSE_ASSET_WIDTH, 20), new Point(250, 100), "pause");

//        Coordinate in Photos
//        resumeButton.loadTexture(new Point(0, 88), new Point(173, 87), path);
//        mainMenuButton.loadTexture(new Point(522, 88), new Point(173, 87), path);

//        pauseButton.loadTexture(new Point(1044, 88), new Point(130, 135), game.getMENUBUTTONPATH());

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
