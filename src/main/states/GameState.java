package main.states;

import main.Config;
import main.Game;
import main.entity.Crate;
import main.entity.Item;
import main.Vector2f;
import main.entity.enemy.Zombie;
import main.input.MouseManager;
import java.awt.*;
import java.awt.event.MouseEvent;

public class GameState extends State {

    private Game game;
    private Crate crate;
    private String imgPath = "/assets/sprites/world/crate/cratev1.png";
    private Item item;
    private String itemPath = "/assets/items/boosts/boosts.png";

    private Zombie zombie;
    private final MouseManager mouseManager;

    public GameState(Game game)
    {
        this.game = game;

        mouseManager = game.getMouseManager();

        zombie = new Zombie(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2),
                            new Vector2f(Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT));

        crate = new Crate(new Vector2f(300, 100), new Vector2f(78, 67));

        item = new Item(new Vector2f(300, 300),
                new Vector2f(Config.ITEMS_ASSET_WIDTH, Config.ITEMS_ASSET_HEIGHT), Item.Type.HEART);

    }

    double animationCounter = 0;
    @Override
    public void tick()
    {
        Vector2f mouse = new Vector2f(mouseManager.getMouseX(), mouseManager.getMouseY());

        zombie.follow(mouse, 2);

        animationCounter++;
        if(animationCounter % Config.ZOMBIE_ANIMATION_DELAY == 0)
        {
            zombie.animate();
        }

        if(animationCounter % Config.CRATE_ANIMATION_DELAY == 0)
        {
            //crate.animate();
        }


        if(crate.isInside(mouse.getX(),mouse.getY())){
            if (game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                item = crate.destroy();
                item.randomize();
                item.show();
            }
        }

        crate.update();
        if(item != null)
        {
            item.update();
        }
        zombie.update();
    }

    @Override
    public void render(Graphics g)
    {
        crate.draw(g);
        if(item != null)
        {
            item.draw(g);
        }
        zombie.draw(g);
    }
}
