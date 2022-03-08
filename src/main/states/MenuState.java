package main.states;

import main.Game;
import main.button.Button;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

public class MenuState extends State
{
    private Game game;
    private SurvivalMenuState survivalMenuState;
    private Button storyButton;
    private Button survivalButton;
    private Button exitButton;
    private String path = "/assets/menu/menubuttons/menubuttonsv3.png";
    // TODO Auto-generated method stub


    public MenuState(Game game)
    {
        this.game = game;
//        Coordinate in Frame
        survivalButton = new Button(new Point(300, 250), new Point(180, 100));
        storyButton = new Button(new Point(300, 370), new Point(180, 100));
        exitButton = new Button(new Point(300, 490), new Point(180, 100));

//        Coordinate in Photos
        survivalButton.loadTexture(new Point(0, 0), new Point(173, 87), game.getMENUBUTTONPATH());
        storyButton.loadTexture(new Point(522, 0), new Point(173, 87), game.getMENUBUTTONPATH());
        exitButton.loadTexture(new Point(1044, 0), new Point(173, 87), game.getMENUBUTTONPATH());

    }

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        if(game.isInside(x, y, storyButton, "story") || game.isInside(x, y, survivalButton, "survival") || game.isInside(x, y, exitButton, "exit")){
//            MenuState.setState(survivalMenuState);
        }

    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(survivalButton.getImage(), (int) survivalButton.getPos().getX(), (int) survivalButton.getPos().getY(),
                (int) survivalButton.getSize().getX(), (int) survivalButton.getSize().getY(), null);

        g.drawImage(storyButton.getImage(), (int) storyButton.getPos().getX(), (int) storyButton.getPos().getY(),
                (int) storyButton.getSize().getX(), (int) storyButton.getSize().getY(), null);

        g.drawImage(exitButton.getImage(), (int) exitButton.getPos().getX(), (int) exitButton.getPos().getY(),
                (int) exitButton.getSize().getX(), (int) exitButton.getSize().getY(), null);
    }



}
