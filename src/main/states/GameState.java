package main.states;

import main.Game;
import main.entity.Player;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameState extends State
{
    private Game game;
    private Player player;
    private int counter = 0;
    private int velX = 0, velY = 0;

    public GameState(Game game)
    {
        this.game = game;

        //character position and size
        player = new Player(new Point(player.xAxis, player.yAxis), new Point(50,50), game);

        //character facing forward
        player.loadTexture(new Point(56,2), new Point(47, 47), player.path);
    }


    @Override
    public void tick()
    {
        player.tick();
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(player.getNewCharacter(),
                (int)player.getPos().getX(),
                (int)player.getPos().getY(),
                (int)player.getSize().getX(),
                (int)player.getSize().getY(),
                null);
    }

}
