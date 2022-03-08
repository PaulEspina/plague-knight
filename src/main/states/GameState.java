package main.states;

import main.Game;
import main.entity.Crate;

import java.awt.*;

public class GameState extends State
{
    private Game game;
    private Crate crate;
    private String imgPath ="/assets/sprites/world/crate/cratev1.png";

    public GameState(Game game)
    {
        this.game = game;
        crate = new Crate(new Point(300,100), new Point(100,100));
        crate.loadTexture(new Point(0,0), new Point(78,67),imgPath);
    }

    @Override
    public void tick()
    {

    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(crate.getImage(),
                (int)crate.getPos().getX(),
                (int)crate.getPos().getY(),
                (int)crate.getSize().getX(),
                (int)crate.getSize().getY(),
                null);
    }

}
