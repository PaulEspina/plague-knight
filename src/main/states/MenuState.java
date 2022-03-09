package main.states;

import main.Config;
import main.Game;
import main.button.Button;

import java.awt.*;
import java.awt.event.MouseEvent;

import static java.lang.Thread.sleep;

public class MenuState extends State
{
    private Game game;
    private String buttonPath;
    private String backgroundPath;
    private Button storyButton;
    private Button survivalButton;
    private Button exitButton;
    // TODO Auto-generated method stub


    public MenuState(Game game)
    {
        this.game = game;
        buttonPath = Config.MENUBUTTONPATH;
        backgroundPath = Config.MENUBACKGROUNDPATH;
//        Coordinate in Frame
        survivalButton = new Button(game, new Point(300, 250), new Point(180, 100), "survival");
        storyButton = new Button(game, new Point(300, 370), new Point(180, 100), "story");
        exitButton = new Button(game, new Point(300, 490), new Point(180, 100), "exit");

//        Coordinate in Photos
        survivalButton.loadTexture(new Point(0, 0), new Point(173, 87), buttonPath);
        storyButton.loadTexture(new Point(522, 0), new Point(173, 87), buttonPath);
        exitButton.loadTexture(new Point(1044, 0), new Point(173, 87), buttonPath);

    }

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        if(survivalButton.isInside(x, y)){
            survivalButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                survivalButton.clickedImage();
            }
        }
        else{
            survivalButton.unhoveredImage();
        }

        if(storyButton.isInside(x, y)){
            storyButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                storyButton.clickedImage();
            }
        }
        else{
            storyButton.unhoveredImage();
        }

        if(exitButton.isInside(x, y)){
            exitButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                exitButton.clickedImage();
            }
        }
        else{
            exitButton.unhoveredImage();
        }

    }

    @Override
    public void render(Graphics g)
    {

        survivalButton.draw(g);
        storyButton.draw(g);
        exitButton.draw(g);
    }



}
