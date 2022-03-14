package main.states;

import main.Config;
import main.Game;
import main.crop.Button;
import main.crop.ImageText;
import main.crop.Pause;
import main.entity.Crate;
import main.entity.Item.Item;
import main.Vector2f;
import main.entity.enemy.Zombie;
import main.entity.map.Map;
import main.input.KeyManager;
import main.input.MouseManager;
import main.entity.player.Player;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
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
    private final MouseManager mouseManager;

    private Vector<Crate> crates;

    private Vector<Item> items;

    private Pause pauseButton;

    private Button resumeButton;
    private Button mainMenuButton;
    private ImageText pauseText;


    public GameState(Game game)
    {
        this.game = game;
        keyManager = game.getKeyManager();
        mouseManager = game.getMouseManager();
        rand = new Random();

        settings = new GameSetting();

        pauseButton = new Pause(game, new Point(5, 5), new Point(50, 50), "pause");

        //character position and size
        player = new Player(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2),
                            new Vector2f(Config.PLAYER_SPRITE_WIDTH * settings.zoom, Config.PLAYER_SPRITE_HEIGHT * settings.zoom));

        zombies = new Vector<>();
        for(int i = 0; i < settings.zombiePerSpawn; i++)
        {
            zombies.add(new Zombie(new Vector2f(rand.nextInt(Config.SCREEN_WIDTH), rand.nextInt(Config.SCREEN_HEIGHT)),
                                   new Vector2f(Config.ZOMBIE_ASSET_WIDTH * settings.zoom, Config.ZOMBIE_ASSET_HEIGHT * settings.zoom)));
        }

        crates = new Vector<>();
//        crates.add(new Crate(new Vector2f(300, 100), new Vector2f((float) Config.CRATE_ASSET_WIDTH / 2 * settings.zoom, (float) Config.CRATE_ASSET_HEIGHT / 2 * settings.zoom)));

        items = new Vector<>();

        //Coordinate in Frame
        resumeButton = new Button(game, new Point(450, 370), new Point(90, 55), 528, "resume");
        mainMenuButton = new Button(game, new Point(210, 370), new Point(90, 55), 440, "menu");
        pauseText = new ImageText(game, new Point(Config.SCREEN_WIDTH / 2 - Config.PAUSE_ASSET_WIDTH, 20), new Point(250, 100), "pause");

    }
    private boolean isPause = false;
    private boolean returnMenu = false;
    @Override
    public void tick()
    {
        animationCounter++;
        pauseImageTick();
        if(returnMenu){
            setState(new MenuState(game));
        }
        if(isPause){
            pauseStateTick();
        }
        else{
            cratesTick();
            itemsTick();
            zombiesTick();
            playerTick();
        }
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
        pauseButton.draw(g);
        if(isPause){
            resumeButton.draw(g);
            mainMenuButton.draw(g);
            pauseText.draw(g);
        }
    }
    private void pauseImageTick()
    {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        if(game.getKeyManager().isKeyDown(KeyEvent.VK_ESCAPE)){
            pauseButton.resumeImage();
            if(isPause)
                isPause = false;
            else
                isPause = true;
        }

        if(pauseButton.isInside(x, y)){
            pauseButton.hoverImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                pauseButton.resumeImage();
                isPause = true;
            }
        }
        else{
            pauseButton.pausedImage();
        }
    }

    private void pauseStateTick(){
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();
        pauseText.showPausedImage();

        if(resumeButton.isInside(x, y)){
            resumeButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                resumeButton.clickedImage();
                isPause = false;
            }
        }
        else{
            resumeButton.unhoveredImage();
        }

        if(mainMenuButton.isInside(x, y)){
            mainMenuButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                //Animate button
                mainMenuButton.clickedImage();
                returnMenu = true;
            }
        }
        else{
            mainMenuButton.unhoveredImage();
        }
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
            items.add(new Item(new Vector2f(rand.nextInt(Config.SCREEN_WIDTH) , rand.nextInt(Config.SCREEN_HEIGHT)),
                               new Vector2f(Config.ITEMS_ASSET_WIDTH / 1.5f * settings.zoom, Config.ITEMS_ASSET_HEIGHT / 1.5f * settings.zoom),
                    Item.Type.values()[rand.nextInt(Item.Type.values().length)]));
        }
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).checkBounds(player))
            {
                player.pickup(items.get(i).getType());
                items.get(i).hide();
            }
            items.get(i).update();
        }

    }

    private void zombiesTick()
    {
        if(animationCounter % settings.zombieSpawnTimer == 0)
        {
            for(int i = 0; i < settings.zombiePerSpawn; i++)
            {
                int xSign = rand.nextBoolean() ? 1 : -1;
                Integer x = null;
                while(x == null || (x >= 0 && x <= Config.SCREEN_WIDTH))
                {
                    x = rand.nextInt(Config.SCREEN_WIDTH + 100) * xSign;
                }
                int ySign = rand.nextBoolean() ? 1 : -1;
                Integer y = null;
                while(y == null || (y >= 0 && y <= Config.SCREEN_HEIGHT))
                {
                    y = rand.nextInt(Config.SCREEN_WIDTH + 100) * ySign;
                }

                zombies.add(new Zombie(new Vector2f(x, y),
                                       new Vector2f(Config.ZOMBIE_ASSET_WIDTH * settings.zoom, Config.ZOMBIE_ASSET_HEIGHT * settings.zoom)));
            }
        }

        for(int i = 0; i < zombies.size(); i++)
        {
            if(player.inRange(zombies.get(i)))
            {
                player.attack(zombies.get(i));
            }
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
