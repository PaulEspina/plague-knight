package main.states;

import main.Config;
import main.Game;
import main.crop.*;
import main.crop.Button;
import main.entity.enemy.Boss;
import main.entity.player.Heart;
import main.entity.Crate;
import main.entity.Item.Item;
import main.Vector2f;
import main.entity.enemy.Zombie;
import main.gfx.AssetManager;
import main.gfx.Sound;
import main.input.KeyManager;
import main.input.MouseManager;
import main.entity.player.Player;

import javax.sound.sampled.AudioInputStream;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class GameState extends State
{
    private final Game game;
    private final KeyManager keyManager;
    private GameSetting settings;
    Random rand;
    double animationCounter = 0;

    public int score;

    private final Player player;
    private final BufferedImage map;

    private final Vector<Zombie> zombies;
    private final Vector<Boss> boss;

    private final MouseManager mouseManager;

    private Vector<Crate> crates;

    private Vector<Item> items;

    private final Pause pauseButton;
    private final Button resumeButton;
    private final Button mainMenuButton;
    private final Button returnMenuButton;
    private final Button retryButton;
    private final ImageText pauseText;
    private final YouDied youDiedImage;
    private final Heart heartHUD;
    private final Speaker speaker;

    private final String[] zombieTypes = {"normal", "fast", "slow"};

    private boolean isPause = false;
    private boolean isDead = false;

    private boolean returnMenu = false;
    private boolean retryGame = false;

    private boolean returnMenuPressed = false;
    private boolean retryGamePressed = false;

    private final Vector<Sound> soundEffects;

    public GameState(Game game)
    {
        this.game = game;
        keyManager = game.getKeyManager();
        mouseManager = game.getMouseManager();
        soundEffects = game.getSoundEffects();
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

        boss = new Vector<>();
//        for(int i = 0; i < settings.bossPerSpawn; i++)
//        {
//            boss.add(new Boss(new Vector2f(rand.nextInt(Config.SCREEN_WIDTH), rand.nextInt(Config.SCREEN_HEIGHT)),
//                    new Vector2f(Config.BOSS_1_ASSET_WIDTH * settings.zoom, Config.BOSS_1_ASSET_HEIGHT * settings.zoom)));
//        }


        crates = new Vector<>();
//        crates.add(new Crate(new Vector2f(300, 100), new Vector2f((float) Config.CRATE_ASSET_WIDTH / 2 * settings.zoom, (float) Config.CRATE_ASSET_HEIGHT / 2 * settings.zoom)));

        items = new Vector<>();

        //Coordinate in Frame
        pauseText = new ImageText(new Point(Config.SCREEN_WIDTH / 2 - Config.PAUSE_ASSET_WIDTH, 120), new Point(250, 100), "pause");
        resumeButton = new Button(new Point(590, 465), new Point(90, 55), 528, "resume");
        mainMenuButton = new Button(new Point(410, 465), new Point(90, 55), 440, "menu");

        heartHUD = new Heart(new Point(5, 5), new Point(50, 50), "heart");

        youDiedImage = new YouDied(new Point(Config.SCREEN_WIDTH / 2 - Config.PAUSE_ASSET_WIDTH, 120), new Point(250, 100), "dead");
        returnMenuButton = new Button(new Point(410, 465), new Point(90, 55), 440, "retry");
        retryButton = new Button(new Point(590, 465), new Point(90, 55), 616, "resume");

        speaker = new Speaker(new Point(Config.SCREEN_WIDTH - 55, Config.SCREEN_HEIGHT - 55), new Point(50, 50), 336, 192, "speaker");
        game.getMenuBGM().stop();
        game.getSurvivalBGM().loop();
    }

    @Override
    public void tick()
    {
        animationCounter++;
        pauseImageTick();
        healthTick();
        speakerTick();
        if(isDead){
            returnMenuTick();
            pauseButton.hideImage();
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
                bossTick();
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
            setState(new LeaderBoardState(game));
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
            items.get(i).draw(g);
        }

        for(int i = 0; i < zombies.size(); i++)
        {
            zombies.get(i).draw(g);
        }

        for(int i = 0; i < boss.size(); i++)
        {
            boss.get(i).draw(g);
        }

        player.draw(g);

        heartHUD.draw(g);

        g.setFont(AssetManager.getInstance().getArcadeClassicSmall());
        g.drawString("Kills !" + score, 10, Config.SCREEN_HEIGHT - 10);
        if(isPause){
            g.drawImage(AssetManager.getInstance().getYouPausedBG(), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
            resumeButton.draw(g);
            mainMenuButton.draw(g);
            pauseText.draw(g);
        }
        if (isDead) {
            g.drawImage(AssetManager.getInstance().getYouDiedBG(), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
            youDiedImage.draw(g);
            returnMenuButton.draw(g);
            retryButton.draw(g);

        }
        pauseButton.draw(g);
        speaker.draw(g);
    }

    private void speakerTick(){
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        if(speaker.isInside(x, y)){
            speaker.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                if(game.getSurvivalBGM().isPlaying())
                {
                    game.getSurvivalBGM().pause();
                }
                else
                {
                    game.getSurvivalBGM().play();
                }
            }
        }
        else{
            if(game.getSurvivalBGM().isPlaying())
            {
                speaker.unhoveredImage();
            }
            else
            {
                speaker.clickedImage();
            }
        }
    }
    private void returnMenuTick(){
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        if(returnMenuButton.isInside(x, y)){
            returnMenuButton.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                returnMenuButton.clickedImage();
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
                returnMenuPressed = true;
                scoreWriter(score);
            }
        }
        else{
            returnMenuButton.unhoveredImage();
        }

        if(retryButton.isInside(x, y)){
            retryButton.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                retryButton.clickedImage();
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
                retryGamePressed = true;
                scoreWriter(score);
            }
        }
        else{
            retryButton.unhoveredImage();
        }
    }

    private void scoreWriter(int score){
        try
        {

            FileWriter file = new FileWriter("scores.txt", true);
            file.write(score + "\n");
            file.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void pauseImageTick()
    {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        if(pauseButton.isInside(x, y)){
            pauseButton.hoverImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                pauseButton.resumeImage();
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
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
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
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
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
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
        if(items.size() < 5)
        {
            Random rand = new Random();
            int randInt = rand.nextInt(settings.itemChance);
            if(randInt % settings.itemChance == 0)
            {
                Item item = null;
                while(item == null || (!player.checkMaxHeart() && item.getType() == Item.Type.HEART))
                {
                    item = new Item(new Vector2f(rand.nextInt(Config.SCREEN_WIDTH), rand.nextInt(Config.SCREEN_HEIGHT)),
                                    new Vector2f(Config.ITEMS_ASSET_WIDTH / 1.5f * settings.zoom, Config.ITEMS_ASSET_HEIGHT / 1.5f * settings.zoom),
                                    Item.Type.values()[rand.nextInt(Item.Type.values().length)],
                                    settings.boostDuration);
                }
                items.add(item);
            }
        }
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).checkBounds(player))
            {
                items.get(i).hide();
                player.pickup(items.remove(i));
                continue;
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

                int type = rand.nextInt(3);
                zombies.add(new Zombie(new Vector2f(x, y),
                                       new Vector2f(Config.ZOMBIE_ASSET_WIDTH * settings.zoom, Config.ZOMBIE_ASSET_HEIGHT * settings.zoom),
                                       zombieTypes[type]));
            }
        }

//        if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1))
//        {
//            Sound buttonPressed = new Sound(AssetManager.getInstance().getKnifeFX());
//            buttonPressed.setSound(-10);
//            buttonPressed.play();
//            soundEffects.add(buttonPressed);
//            for(int i = 0; i < zombies.size(); i++)
//            {
//                player.setAttackAnimate(true);
//                if(player.inRange(zombies.get(i)))
//                {
//                    player.attack(zombies.get(i));
//                }
//            }
//        }

        for(int i = 0; i < zombies.size(); i++)
        {
            if(zombies.get(i).getHealthPoints() <= 0){
                zombies.get(i).die();
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
    }

    private void bossTick(){
        if(animationCounter % settings.bossSpawnTimer == 0)
        {

            for(int i = 0; i < settings.bossPerSpawn; i++)
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

                boss.add(new Boss(new Vector2f(x, y), new Vector2f(Config.BOSS_1_ASSET_WIDTH * settings.zoom, Config.BOSS_1_ASSET_HEIGHT * settings.zoom)));
            }
        }

//        if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1))
//        {
//            Sound buttonPressed = new Sound(AssetManager.getInstance().getKnifeFX());
//            buttonPressed.setSound(-10);
//            buttonPressed.play();
//            soundEffects.add(buttonPressed);
//            for(int i = 0; i < boss.size(); i++)
//            {
//                player.setAttackAnimate(true);
//                if(player.inRange(boss.get(i)))
//                {
//                    player.attack(boss.get(i));
//                }
//            }
//        }

        for(int i = 0; i < boss.size(); i++)
        {
            if(boss.get(i).getHealthPoints() <= 0){
//                boss.get(i).die();
                boss.get(i).setDeadAnimate(true);
                boss.remove(i);
                score += 2;
                continue;
            }

            boss.get(i).follow(player.getPos());

            if (boss.get(i).inRange(player)) {
                if(animationCounter % settings.bossAttackDelay == 0) {
                    boss.get(i).setAttackAnimate(true);
                    boss.get(i).attack(player);
                }
            }

            if(animationCounter % settings.bossAttackCooldownDelay == 0) {
                if(boss.get(i).isAttackAnimate()){
                    boss.get(i).setAttackAnimate(false);
                }
            }

            if(animationCounter % boss.get(i).getAnimationSpeed() == 0)
            {
                boss.get(i).animate();
            }
            boss.get(i).update();
        }
    }

    private void healthTick(){
        heartHUD.setCurrentHeartCount(player.getCurrentHearts());
        heartHUD.setHeartCount(player.getHearts());
        if(player.getCurrentHearts() <= 0){
            isDead = true;
            for(int i = 0; i < zombies.size(); i++)
            {
                zombies.get(i).die();
            }
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
        if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1))
        {
            Sound buttonPressed = new Sound(AssetManager.getInstance().getKnifeFX());
            buttonPressed.setSound(-10);
            buttonPressed.play();
            soundEffects.add(buttonPressed);
            player.setAttackAnimate(true);
            for(int i = 0; i < boss.size(); i++)
            {
                if(player.inRange(boss.get(i)))
                {
                    player.attack(boss.get(i));
                }
            }
            for(int i = 0; i < zombies.size(); i++)
            {
                if(player.inRange(zombies.get(i)))
                {
                    player.attack(zombies.get(i));
                }
            }
        }

        player.update();
    }
}
