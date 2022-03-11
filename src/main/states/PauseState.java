package main.states;

import main.Config;
import main.Game;
import main.button.Button;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PauseState extends State{
    private Game game;
    private String path;
    private Button resumeButton;
    private Button mainMenuButton;
    private Button pauseButton;

    public PauseState(Game game){
        this.game = game;
        path = Config.MENU_BUTTON_ASSET_PATH;
//        Coordinate in Frame

        resumeButton = new Button(game, new Point(420, 370), new Point(180, 100), "resume");
        mainMenuButton = new Button(game, new Point(180, 370), new Point(180, 100), "menu");

//        pauseButton = new Button(new Point(360, 200), new Point(50,50));

//        Coordinate in Photos
        resumeButton.loadTexture(new Point(0, 88), new Point(173, 87), path);
        mainMenuButton.loadTexture(new Point(522, 88), new Point(173, 87), path);

//        pauseButton.loadTexture(new Point(1044, 88), new Point(130, 135), game.getMENUBUTTONPATH());

    }

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

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
        g.drawString("PAUSE", 370, 100);
        resumeButton.draw(g);
        mainMenuButton.draw(g);
    }
}
