package main.states;

import main.Game;
import main.button.Button;

import java.awt.*;

public class PauseState extends State{
    private Game game;
    private Button resumeButton;
    private Button mainMenuButton;
    private Button pauseButton;

    public PauseState(Game game){
        this.game = game;
//        Coordinate in Frame
        resumeButton = new Button(new Point(420, 370), new Point(180, 100));
        mainMenuButton = new Button(new Point(180, 370), new Point(180, 100));
        pauseButton = new Button(new Point(360, 200), new Point(50,50));

//        Coordinate in Photos
        resumeButton.loadTexture(new Point(0, 88), new Point(173, 87), game.getMENUBUTTONPATH());
        mainMenuButton.loadTexture(new Point(522, 88), new Point(173, 87), game.getMENUBUTTONPATH());

        pauseButton.loadTexture(new Point(1044, 88), new Point(130, 135), game.getMENUBUTTONPATH());

    }

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();
        if(game.isInside(x, y, resumeButton, "resume") || game.isInside(x, y, mainMenuButton, "menu")){
//            MenuState.setState(survivalMenuState);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawString("PAUSE", 370, 100);
        g.drawImage(resumeButton.getImage(), (int) resumeButton.getPos().getX(), (int) resumeButton.getPos().getY(),
                (int) resumeButton.getSize().getX(), (int) resumeButton.getSize().getY(), null);

        g.drawImage(mainMenuButton.getImage(), (int) mainMenuButton.getPos().getX(), (int) mainMenuButton.getPos().getY(),
                (int) mainMenuButton.getSize().getX(), (int) mainMenuButton.getSize().getY(), null);

        g.drawImage(pauseButton.getImage(), (int) pauseButton.getPos().getX(), (int) pauseButton.getPos().getY(),
                (int) pauseButton.getSize().getX(), (int) pauseButton.getSize().getY(), null);
    }
}
