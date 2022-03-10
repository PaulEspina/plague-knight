package main.states;

import main.Config;
import main.Game;
import main.Vector2f;
import main.entity.enemy.Zombie;
import main.input.MouseManager;
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

    private Zombie zombie;
    private final MouseManager mouseManager;

    public GameState(Game game)
    {
        this.game = game;

        //character position and size
        player = new Player(new Point(player.xAxis, player.yAxis), new Point(50,50), game);

        //character facing forward
        player.loadTexture(new Point(56,2), new Point(47, 47), player.path);

        mouseManager = game.getMouseManager();

        zombie = new Zombie(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2),
                            new Vector2f(Config.ZOMBIE_SPRITE_WIDTH, Config.ZOMBIE_SPRITE_HEIGHT));
    }



    private double maxFrame = 20;
    private double deltaCounter = 0;
    @Override
    public void tick()
    {
        player.tick();

        Vector2f mouse = new Vector2f(mouseManager.getMouseX(), mouseManager.getMouseY());
        zombie.follow(mouse, 2);

        deltaCounter += game.getDeltaPlease();
        if(deltaCounter >= maxFrame){
            zombie.animate();
            deltaCounter = 0;
        }
        zombie.update();

    }

    @Override
    public void render(Graphics g)
    {
        // TODO move to player.draw()
        g.drawImage(player.getNewCharacter(),
                (int)player.getPos().getX(),
                (int)player.getPos().getY(),
                (int)player.getSize().getX(),
                (int)player.getSize().getY(),
                null);
        zombie.draw(g);
    }

}
