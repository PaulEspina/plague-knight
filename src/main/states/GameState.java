package main.states;

import main.Config;
import main.Game;
import main.entity.Crate;
import main.entity.Item;
import main.Vector2f;
import main.entity.enemy.Zombie;
import main.input.KeyManager;
import main.input.MouseManager;
import main.entity.Player;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameState extends State
{
    private Game game;
    private Crate crate;
    private String imgPath = "/assets/sprites/world/crate/cratev1.png";
    private Item item;
    private String itemPath = "/assets/items/boosts/boosts.png";
    private KeyManager keyManager;

    private Player player;
    private int counter = 0;
    private int velX = 0, velY = 0;

    private Zombie zombie;
    private final MouseManager mouseManager;

    public GameState(Game game)
    {
        this.game = game;
        keyManager = game.getKeyManager();

        //character position and size
        player = new Player(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2),
                            new Vector2f(Config.PLAYER_SPRITE_WIDTH, Config.PLAYER_SPRITE_HEIGHT));

        //character facing forward
//        player.loadTexture(new Point(56,2), new Point(47, 47), player.path);

        mouseManager = game.getMouseManager();

        zombie = new Zombie(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2),
                            new Vector2f(Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT));

        crate = new Crate(new Vector2f(300, 100), new Vector2f(78, 67));
    }

    double animationCounter = 0;
    @Override
    public void tick()
    {
        // Key Down
        if(keyManager.isKeyDown(KeyEvent.VK_W))
        {
            player.setVelY(-10);
        }
        if(keyManager.isKeyDown(KeyEvent.VK_S))
        {
            player.setVelY(10);
        }
        if(keyManager.isKeyDown(KeyEvent.VK_A))
        {
            player.setVelX(-10);
        }
        if(keyManager.isKeyDown(KeyEvent.VK_D))
        {
            player.setVelX(10);
        }

        // Key Up
        if(keyManager.isKeyUp(KeyEvent.VK_W))
        {
            player.setVelY(0);
        }
        if(keyManager.isKeyUp(KeyEvent.VK_S))
        {
            player.setVelY(0);
        }
        if(keyManager.isKeyUp(KeyEvent.VK_A))
        {
            player.setVelX(0);
        }
        if(keyManager.isKeyUp(KeyEvent.VK_D))
        {
            player.setVelX(0);
        }

        Vector2f mouse = new Vector2f(mouseManager.getMouseX(), mouseManager.getMouseY());

        zombie.follow(player.getPos(), 2);

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
            }
        }

        player.update();
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
        // TODO move to player.draw()
        player.draw(g);
        crate.draw(g);
        if(item != null)
        {
            item.draw(g);
        }
        zombie.draw(g);
    }

}
