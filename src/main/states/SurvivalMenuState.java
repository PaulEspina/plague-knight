package main.states;

import main.Config;
import main.Game;
import main.button.Button;

import java.awt.*;
import java.awt.event.MouseEvent;


public class SurvivalMenuState extends State{

    private Game game;
    private String path;
    private Button startButton;
    private Button cancelButton;


    public SurvivalMenuState(Game game){
        this.game = game;
        path = Config.MENUBUTTONPATH;
//        Coordinate in Frame
        startButton = new Button(game, new Point(605, 490), new Point(180, 100), "start");
        cancelButton = new Button(game, new Point(10, 490), new Point(180, 100), "cancel");

//        Coordinate in Photos
        startButton.loadTexture(new Point(0, 176), new Point(173, 87), path);
        cancelButton.loadTexture(new Point(522, 176), new Point(173, 87), path);
    }
    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

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
            }
        }
        else{
            cancelButton.unhoveredImage();
        }
    }

    @Override
    public void render(Graphics g) {

        startButton.draw(g);
        cancelButton.draw(g);
    }

}
