package main.states;

import main.Game;
import main.button.Button;

import java.awt.*;


public class SurvivalMenuState extends State{

    private Game game;
    private Button startButton;
    private Button cancelButton;


    public SurvivalMenuState(Game game){
        this.game = game;
//        Coordinate in Frame
        startButton = new Button(new Point(605, 490), new Point(180, 100));
        cancelButton = new Button(new Point(10, 490), new Point(180, 100));

//        Coordinate in Photos
        startButton.loadTexture(new Point(0, 176), new Point(173, 87), game.getMENUBUTTONPATH());
        cancelButton.loadTexture(new Point(522, 176), new Point(173, 87), game.getMENUBUTTONPATH());
    }
    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        if(game.isInside(x, y, startButton, "start") || game.isInside(x, y, cancelButton, "cancel")){
        }
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(startButton.getImage(), (int) startButton.getPos().getX(), (int) startButton.getPos().getY(),
                (int) startButton.getSize().getX(), (int) startButton.getSize().getY(), null);

        g.drawImage(cancelButton.getImage(), (int) cancelButton.getPos().getX(), (int) cancelButton.getPos().getY(),
                (int) cancelButton.getSize().getX(), (int) cancelButton.getSize().getY(), null);
    }

}
