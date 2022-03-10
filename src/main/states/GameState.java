package main.states;

import main.Config;
import main.Game;
import main.Vector2f;
import main.entity.enemy.Zombie;
import main.input.MouseManager;
import java.awt.*;

public class GameState extends State
{
    private Game game;

    private Zombie zombie;
    private final MouseManager mouseManager;

    public GameState(Game game)
    {
        this.game = game;

        mouseManager = game.getMouseManager();

        zombie = new Zombie(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2),
                            new Vector2f(Config.ZOMBIE_SPRITE_WIDTH, Config.ZOMBIE_SPRITE_HEIGHT));
    }


    private double maxFrame = 20;
    private double deltaCounter = 0;
    @Override
    public void tick()
    {
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
        zombie.draw(g);
    }

}
