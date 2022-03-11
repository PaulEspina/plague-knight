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
import java.security.Key;
import java.util.Random;
import java.util.Vector;

public class GameState extends State
{
    private final Game game;
    private final KeyManager keyManager;
    private GameSetting settings;
    double animationCounter = 0;

    private Player player;

    private Vector<Zombie> zombies;
    private final MouseManager mouseManager;

    private Vector<Crate> crates;

    private Vector<Item> items;

    public GameState(Game game)
    {
        this.game = game;
        keyManager = game.getKeyManager();
        mouseManager = game.getMouseManager();

        settings = new GameSetting();

        //character position and size
        player = new Player(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2),
                            new Vector2f(Config.PLAYER_SPRITE_WIDTH, Config.PLAYER_SPRITE_HEIGHT));

        Random rand = new Random();
        zombies = new Vector<>();
        for(int i = 0; i < settings.zombiePerSpawn; i++)
        {
            zombies.add(new Zombie(new Vector2f(rand.nextInt(Config.SCREEN_WIDTH), rand.nextInt(Config.SCREEN_HEIGHT)),
                                   new Vector2f(Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT)));
        }

        crates = new Vector<>();
        crates.add(new Crate(new Vector2f(300, 100), new Vector2f((float) Config.CRATE_ASSET_WIDTH / 2, (float) Config.CRATE_ASSET_HEIGHT / 2)));

        items = new Vector<>();
    }

    @Override
    public void tick()
    {
        animationCounter++;
        cratesTick();
        itemsTick();
        zombiesTick();
        playerTick();
    }

    @Override
    public void render(Graphics g)
    {
        for(int i = 0; i < crates.size(); i++)
        {
            crates.get(i).draw(g);
        }

        for(int i = 0; i < items.size(); i++)
        {
            items.get(i).draw(g);
        }

        for(int i = 0; i < zombies.size(); i++)
        {
            zombies.get(i).draw(g);
        }
        player.draw(g);
    }

    private void cratesTick()
    {
        for(int i = 0; i < crates.size(); i++)
        {
            if(keyManager.isKeyDown(KeyEvent.VK_SPACE)){ // temp.. for testing only
                items.add(crates.get(i).destroy());
            }
            crates.get(i).update();
        }
    }

    private void itemsTick()
    {
        for(int i = 0; i < items.size(); i++)
        {
            items.get(i).update();
        }
    }

    private void zombiesTick()
    {
        for(int i = 0; i < zombies.size(); i++)
        {
            zombies.get(i).follow(player.getPos());
            if(animationCounter % zombies.get(i).getAnimationSpeed() == 0)
            {
                zombies.get(i).animate();
            }
            zombies.get(i).update();
        }
    }

    private void playerTick()
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
        player.update();
    }
}
