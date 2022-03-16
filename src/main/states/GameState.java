package main.states;

import main.Config;
import main.Game;
import main.crop.Button;
import main.crop.YouDied;
import main.entity.player.Heart;
import main.crop.ImageText;
import main.crop.Pause;
import main.entity.Crate;
import main.entity.Item.Item;
import main.Vector2f;
import main.entity.enemy.FastZombie;
import main.entity.enemy.SlowZombie;
import main.entity.enemy.Zombie;
import main.gfx.AssetManager;
import main.input.KeyManager;
import main.input.MouseManager;
import main.entity.player.Player;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class GameState extends State
{
    private final Game game;
    private final KeyManager keyManager;
    private GameSetting settings;
    Random rand;
    double animationCounter = 0;

    public int score;

    private Player player;
    private BufferedImage map;

    private Vector<Zombie> zombies;
    private Vector<FastZombie> fastZombie;
    private Vector<SlowZombie> slowZombie;
    private final MouseManager mouseManager;

    private Vector<Crate> crates;

    private Vector<Item> items;

    private Pause pauseButton;

    private Button resumeButton;
    private Button mainMenuButton;
    private Button returnMenuButton;
    private Button retryButton;
    private ImageText pauseText;
    private YouDied youDiedImage;

    private Heart heartHUD;

    private Font ARCADECLASSIC;

    private final String[] zombieTypes = {"normal", "fast", "slow"};

    public GameState(Game game)
    {
        this.game = game;
        keyManager = game.getKeyManager();
        mouseManager = game.getMouseManager();
        rand = new Random();

        map = AssetManager.getInstance().getMap().getSubimage(100, 100, Config.MAP_WIDTH / 2, Config.MAP_HEIGHT / 2);

        settings = new GameSetting();

        pauseButton = new Pause(new Point(Config.SCREEN_WIDTH - 55, 5), new Point(50, 50), "pause");

        //character position and size
        player = new Player(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2),
                            new Vector2f(Config.PLAYER_SPRITE_WIDTH * settings.zoom, Config.PLAYER_SPRITE_HEIGHT * settings.zoom));
        player.setMovementSpeed(settings.playerMovementSpeed);
        player.setDamage(10);

        zombies = new Vector<>();
        for(int i = 0; i < settings.zombiePerSpawn; i++)
        {
            zombies.add(new Zombie(new Vector2f(rand.nextInt(Config.SCREEN_WIDTH), rand.nextInt(Config.SCREEN_HEIGHT)),
                                   new Vector2f(Config.ZOMBIE_ASSET_WIDTH * settings.zoom, Config.ZOMBIE_ASSET_HEIGHT * settings.zoom),
                                   zombieTypes[rand.nextInt(3)]));
        }

        fastZombie = new Vector<>();
        slowZombie = new Vector<>();

        crates = new Vector<>();
//        crates.add(new Crate(new Vector2f(300, 100), new Vector2f((float) Config.CRATE_ASSET_WIDTH / 2 * settings.zoom, (float) Config.CRATE_ASSET_HEIGHT / 2 * settings.zoom)));

        items = new Vector<>();

        //Coordinate in Frame
        resumeButton = new Button(new Point(470, 370), new Point(90, 55), 528, "resume");
        mainMenuButton = new Button(new Point(230, 370), new Point(90, 55), 440, "menu");
        pauseText = new ImageText(new Point(Config.SCREEN_WIDTH / 2 - Config.PAUSE_ASSET_WIDTH, 20), new Point(250, 100), "pause");

        heartHUD = new Heart(new Point(5, 5), new Point(50, 50), "heart");
        youDiedImage = new YouDied(new Point(Config.SCREEN_WIDTH / 2 - Config.PAUSE_ASSET_WIDTH, 20), new Point(250, 100), "dead");
        returnMenuButton = new Button(new Point(230, 370), new Point(90, 55), 440, "retry");
        retryButton = new Button(new Point(470, 370), new Point(90, 55), 616, "resume");
    }
    private boolean isPause = false;
    private boolean isDead = false;

    private boolean returnMenu = false;
    private boolean retryGame = false;

    private boolean returnMenuPressed = false;
    private boolean retryGamePressed = false;
    @Override
    public void tick()
    {
        animationCounter++;
        zombiesTick();
        cratesTick();
        itemsTick();
        playerTick();
        pauseImageTick();
        healthTick();
        if(isDead){
            returnMenuTick();
        }
        else{
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
        if(returnMenuPressed){
            if(animationCounter % Config.BUTTON_DELAY_ANIMATION == 0){
                returnMenu = true;
            }
        }
        if(retryGamePressed){
            if(animationCounter % Config.BUTTON_DELAY_ANIMATION == 0){
                retryGame = true;
            }
        }

        if(returnMenu){
            setState(new MenuState(game));
        }
        if(retryGame){
            setState(new GameState(game));
        }

    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(map, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
        for(int i = 0; i < crates.size(); i++)
        {
            crates.get(i).draw(g);
        }

        for(int i = 0; i < items.size(); i++)
        {
            if(items.size() >= 5){
                items.remove(i);
                break;
            }
            else{
                items.get(i).draw(g);
            }
        }

        for(int i = 0; i < zombies.size(); i++)
        {
            zombies.get(i).draw(g);
//            g.setColor(Color.RED);
//            g.fillRect((int) zombies.get(i).getPos().getX() - (int) zombies.get(i).getSize().getX() / 2,
//                    (int) zombies.get(i).getPos().getY() - (int) zombies.get(i).getSize().getY() / 2,
//                    (int) zombies.get(i).getSize().getX(), (int) zombies.get(i).getSize().getY());
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
//        g.setColor(Color.RED);
//        g.fillRect((int) player.getPos().getX() - (int) player.getSize().getX() / 2,
//                (int) player.getPos().getY() - (int) player.getSize().getY() / 2,
//                (int) player.getSize().getX(), (int) player.getSize().getY());
        pauseButton.draw(g);
        heartHUD.draw(g);

//        try{
//            ARCADECLASSIC = Font.createFont(Font.TRUETYPE_FONT, new File(Config.FONT_PATH)).deriveFont(72f);
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(Config.FONT_PATH)));
//        }
//        catch(IOException | FontFormatException e){
//        }
//        g.setFont(ARCADECLASSIC);
        g.setFont(new Font(null, Font.PLAIN,72));
//        g.setFont(new Font("Monospaced", Font.PLAIN, 72));
        g.drawString("Kills: " + score, 10, (Config.HEART_HEIGHT / 2) * 5);
        if(isPause){
            resumeButton.draw(g);
            mainMenuButton.draw(g);
            pauseText.draw(g);
        }
        if (isDead) {
            youDiedImage.draw(g);
            returnMenuButton.draw(g);
            retryButton.draw(g);
        }
    }

    private void returnMenuTick(){
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        if(returnMenuButton.isInside(x, y)){
            returnMenuButton.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                returnMenuButton.clickedImage();
                returnMenuPressed = true;try
                {
                    FileWriter file = new FileWriter("scores.txt", true);
                    file.write(LocalDate.now().toString() + " - SCORE: " + score + "\n");
                    file.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else{
            returnMenuButton.unhoveredImage();
        }

        if(retryButton.isInside(x, y)){
            retryButton.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                retryButton.clickedImage();
                retryGamePressed = true;
                try
                {
                    FileWriter file = new FileWriter("scores.txt", true);
                    file.write(LocalDate.now().toString() + " - SCORE: " + score + "\n");
                    file.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else{
            retryButton.unhoveredImage();
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
        int randInt = rand.nextInt(settings.itemChance);
        if(randInt % settings.itemChance == 0)
        {
            Item item = null;
            while(item == null || (!player.checkMaxHeart() && item.getType() == Item.Type.HEART))
            {
                item = new Item(new Vector2f(rand.nextInt(Config.SCREEN_WIDTH) , rand.nextInt(Config.SCREEN_HEIGHT)),
                                new Vector2f(Config.ITEMS_ASSET_WIDTH / 1.5f * settings.zoom, Config.ITEMS_ASSET_HEIGHT / 1.5f * settings.zoom),
                                Item.Type.values()[rand.nextInt(Item.Type.values().length)],
                                settings.boostDuration);
            }
            items.add(item);
        }
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).checkBounds(player))
            {
                player.pickup(items.get(i));
                items.get(i).hide();
            }
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

                zombies.add(new Zombie(new Vector2f(x, y),
                                       new Vector2f(Config.ZOMBIE_ASSET_WIDTH * settings.zoom, Config.ZOMBIE_ASSET_HEIGHT * settings.zoom),
                                       zombieTypes[rand.nextInt(3)]));
            }
        }
        for(int i = 0; i < zombies.size(); i++)
        {
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                player.setAttackAnimate(true);
                if(player.inRange(zombies.get(i)))
                {
                    player.attack(zombies.get(i));
                }
            }

            if(zombies.get(i).getHealthPoints() <= 0){
                zombies.remove(i);
                score += 1;
                continue;
            }

            zombies.get(i).follow(player.getPos());

            if(animationCounter % settings.normalZombieAttackDelay == 0) {
                if (zombies.get(i).inRange(player)) {
                    zombies.get(i).attack(player);
                }
            }
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

    private void healthTick(){
        heartHUD.setCurrentHeartCount(player.getCurrentHearts());
        heartHUD.setHeartCount(player.getHearts());
        if(player.getCurrentHearts() <= 0){
            System.out.println("YOU DEAD");
            System.out.println("SCORE - " + score);
            isDead = true;
//            GAMEOVERSTATE
        }
    }

    private void playerTick()
    {
        // Key Down
        if(keyManager.isKeyDown(KeyEvent.VK_W))
        {
            player.setVelY(-1f);
            player.setDirection("north");
        }
        if(keyManager.isKeyDown(KeyEvent.VK_S))
        {
            player.setVelY(1f);
            player.setDirection("south");
        }
        if(keyManager.isKeyDown(KeyEvent.VK_A))
        {
            player.setVelX(-1f);
            player.setDirection("west");
        }
        if(keyManager.isKeyDown(KeyEvent.VK_D))
        {
            player.setVelX(1f);
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

        if(animationCounter % Config.PLAYER_KNIFE_COOLDOWN_DELAY == 0) {
            if(player.isAttackAnimate()){
                player.setAttackAnimate(false);
            }
        }

        player.update();
    }
}
