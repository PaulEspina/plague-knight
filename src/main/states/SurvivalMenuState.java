package main.states;

import main.Game;
import main.button.Button;
import java.awt.*;
import java.awt.event.MouseEvent;

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

        if(isInside(x, y, startButton, "start") || isInside(x, y, cancelButton, "cancel")){
        }
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(startButton.getImage(), (int) startButton.getPos().getX(), (int) startButton.getPos().getY(),
                (int) startButton.getSize().getX(), (int) startButton.getSize().getY(), null);

        g.drawImage(cancelButton.getImage(), (int) cancelButton.getPos().getX(), (int) cancelButton.getPos().getY(),
                (int) cancelButton.getSize().getX(), (int) cancelButton.getSize().getY(), null);
    }

    public boolean isInside(float x, float y, Button butt, String buttonName){

        Point getPos = butt.getImagePos();
//        x <= image.width + image.x && x >= image.x
//        y <= image.width + image.y && y >= image.y
        if((x <= butt.getSize().getX() + butt.getPos().getX() && x >=  butt.getPos().getX()) &&
                (y <= butt.getSize().getY() + butt.getPos().getY() && y >= butt.getPos().getY())){

            butt.setFrame(new Point((int) (butt.getImagePos().getX() + 174), 0), new Point(173, 87));

//            If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
//              Animate button
                butt.setFrame(new Point((int) (butt.getImagePos().getX() + 174), 0), new Point(173, 87));
                butt.setImagePos(getPos);
                if(buttonName == "story"){
                    System.out.println("Game Mode is Disable");
                }
                else if(buttonName == "survival"){
                    System.out.println("Survival Game");
                    return true;
                }
                else if(buttonName == "start"){
                    System.out.println("Start Game");
                    return true;
                }
                else if(buttonName == "cancel"){
                    System.out.println("Back to Menu");
                    return true;
                }
                else{
                    butt.exitGameNotification();
                }
                return true;
            }
            butt.setImagePos(getPos);
            return false;
        }

        butt.setFrame(new Point((int) (butt.getImagePos().getX()), 0), new Point(173, 87));
        butt.setImagePos(getPos);
        return false;
    }

}
