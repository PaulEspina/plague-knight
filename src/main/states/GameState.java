package main.states;

import main.Config;
import main.Game;
import main.entity.Crate;
import main.entity.Item;
import main.Vector2f;
import main.entity.enemy.FastZombie;
import main.entity.enemy.SlowZombie;
import main.entity.enemy.Zombie;
import main.entity.map.Map;
import main.input.KeyManager;
import main.input.MouseManager;
import main.entity.player.Player;
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
    Random rand;
    double animationCounter = 0;

    private Player player;
    private Map map;

    private Vector<Zombie> zombies;
    private Vector<FastZombie> fastZombie;
    private Vector<SlowZombie> slowZombie;
    private final MouseManager mouseManager;

    private Vector<Crate> crates;

    private Vector<Item> items;

    public GameState(Game game)
    {
        this.game = game;
        keyManager = game.getKeyManager();
        mouseManager = game.getMouseManager();
        rand = new Random();

        settings = new GameSetting();

        //character position and size
        player = new Player(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2),
                            new Vector2f(Config.PLAYER_SPRITE_WIDTH * settings.zoom, Config.PLAYER_SPRITE_HEIGHT * settings.zoom));

        zombies = new Vector<>();
        for(int i = 0; i < settings.zombiePerSpawn; i++)
        {
            zombies.add(new Zombie(new Vector2f(rand.nextInt(Config.SCREEN_WIDTH), rand.nextInt(Config.SCREEN_HEIGHT)),
                                   new Vector2f(Config.ZOMBIE_ASSET_WIDTH * settings.zoom, Config.ZOMBIE_ASSET_HEIGHT * settings.zoom)));
        }

        fastZombie = new Vector<>();
        slowZombie = new Vector<>();

        crates = new Vector<>();
//        crates.add(new Crate(new Vector2f(300, 100), new Vector2f((float) Config.CRATE_ASSET_WIDTH / 2 * settings.zoom, (float) Config.CRATE_ASSET_HEIGHT / 2 * settings.zoom)));

        items = new Vector<>();
    }

    @Override
    public void tick()
    {
        animationCounter++;
        zombiesTick();
        cratesTick();
        itemsTick();
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
        for(int i = 0; i < fastZombie.size(); i++)
        {
            fastZombie.get(i).draw(g);
        }
        for(int i = 0; i < slowZombie.size(); i++)
        {
            slowZombie.get(i).draw(g);
        }
        player.draw(g);
    }

    private void cratesTick()
    {
        for(int i = 0; i < crates.size(); i++)
        {
            if(keyManager.isKeyDown(KeyEvent.VK_SPACE)){ // temp.. for testing only
                if(!crates.get(i).isDestroyed())
                {
                    items.add(crates.get(i).destroy());
                }
            }
            crates.get(i).update();
        }
    }

    private void itemsTick()
    {
        Random rand = new Random();
        int chance = 1000;
        int randInt = rand.nextInt(chance);
        if(randInt % chance == 0)
        {
            items.add(new Item(new Vector2f(rand.nextInt(Config.SCREEN_WIDTH) , rand.nextInt(Config.SCREEN_HEIGHT)), new Vector2f(Config.ITEMS_ASSET_WIDTH, Config.ITEMS_ASSET_HEIGHT), Item.Type.values()[rand.nextInt(Item.Type.values().length)]));
        }
        for(int i = 0; i < items.size(); i++)
        {
            items.get(i).update();
        }

    }

    private void zombiesTick()
    {
        //add zombie to the vector
        if(animationCounter % settings.zombieSpawnTimer == 0)
        {
            //randomize the spawn location of the zombie
            for(int i = 0; i < settings.zombiePerSpawn; i++)
            {
                //generates random x-axis location
                int xSign = rand.nextBoolean() ? 1 : -1;
                Integer x = null;
                while(x == null || (x >= 0 && x <= Config.SCREEN_WIDTH))
                {
                    x = rand.nextInt(Config.SCREEN_WIDTH + 100) * xSign;
                }
                //generates random y-axis location
                int ySign = rand.nextBoolean() ? 1 : -1;
                Integer y = null;
                while(y == null || (y >= 0 && y <= Config.SCREEN_HEIGHT))
                {
                    y = rand.nextInt(Config.SCREEN_WIDTH + 100) * ySign;
                }

                int randNum = rand.nextInt(100);
                if(randNum % 5 == 0)
                {
                    fastZombie.add(new FastZombie(new Vector2f(x, y),
                            new Vector2f(Config.ZOMBIE_ASSET_WIDTH * settings.zoom, Config.ZOMBIE_ASSET_HEIGHT * settings.zoom)));
                }
                else if(randNum % 7 == 0)
                {
                    slowZombie.add(new SlowZombie(new Vector2f(x, y),
                            new Vector2f(Config.ZOMBIE_ASSET_WIDTH * settings.zoom, Config.ZOMBIE_ASSET_HEIGHT * settings.zoom)));
                }
                else
                {
                    zombies.add(new Zombie(new Vector2f(x, y),
                            new Vector2f(Config.ZOMBIE_ASSET_WIDTH * settings.zoom, Config.ZOMBIE_ASSET_HEIGHT * settings.zoom)));
                }

            }
        }

        for(int i = 0; i < zombies.size(); i++)
        {
            zombies.get(i).follow(player.getPos());
            if(animationCounter % zombies.get(i).getAnimationSpeed() == 0)
            {
                zombies.get(i).animate();
            }
            zombies.get(i).update();
        }

        for(int i = 0; i < fastZombie.size(); i++)
        {
            fastZombie.get(i).follow(player.getPos());
            if(animationCounter % fastZombie.get(i).getAnimationSpeed() == 0)
            {
                fastZombie.get(i).animate();
            }
            fastZombie.get(i).update();
        }
        for(int i = 0; i < slowZombie.size(); i++)
        {
            slowZombie.get(i).follow(player.getPos());
            if(animationCounter % slowZombie.get(i).getAnimationSpeed() == 0)
            {
                slowZombie.get(i).animate();
            }
            slowZombie.get(i).update();
        }
    }

    private void playerTick()
    {
        // Key Down
        if(keyManager.isKeyDown(KeyEvent.VK_W))
        {
            player.setVelY(-1.2f);
            player.setDirection("north");
        }
        if(keyManager.isKeyDown(KeyEvent.VK_S))
        {
            player.setVelY(1.2f);
            player.setDirection("south");
        }
        if(keyManager.isKeyDown(KeyEvent.VK_A))
        {
            player.setVelX(-1.2f);
            player.setDirection("west");
        }
        if(keyManager.isKeyDown(KeyEvent.VK_D))
        {
            player.setVelX(1.2f);
            player.setDirection("east");
        }

        // Key Up
        if(!keyManager.isKeyDown(KeyEvent.VK_W) && !keyManager.isKeyDown(KeyEvent.VK_S))
        {
            player.setVelY(0);
        }
        if(!keyManager.isKeyDown(KeyEvent.VK_A) && !keyManager.isKeyDown(KeyEvent.VK_D))
        {
            player.setVelX(0);
        }

        if(animationCounter % player.getAnimationSpeed() == 0)
        {
            player.animate();
        }

        player.update();
    }
}
