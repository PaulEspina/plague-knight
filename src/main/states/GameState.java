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

        zombie = new Zombie(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2), new Vector2f(36, 48));
        System.out.println(zombie.getPos().getX() + "," + zombie.getPos().getY());
    }

    @Override
    public void tick()
    {
        Vector2f mouse = new Vector2f(mouseManager.getMouseX(), mouseManager.getMouseY());

        zombie.follow(mouse, 1);
        zombie.update();

    }

    @Override
    public void render(Graphics g)
    {
        zombie.draw(g);
    }

}
