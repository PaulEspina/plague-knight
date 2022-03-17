package main.gfx;

import main.Config;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AssetManager
{
    private BufferedImage map;
    private BufferedImage player;
    private BufferedImage HUD;
    private BufferedImage zombie;
    private BufferedImage crate;
    private BufferedImage item;
    private BufferedImage menuButtonImage;
    private BufferedImage difficultyImage;
    private BufferedImage defaultBGImage;
    private BufferedImage dottedBGImage;
    private BufferedImage exitBGImage;
    private BufferedImage survivalBrokenBGImage;
    private BufferedImage storyBrokenBGImage;
    private BufferedImage pausedImage;
    private BufferedImage youDiedImage;
    private BufferedImage treeObstacle;
    private File menuBGM;
    private File gameBGM;
    private File knifeFX;
    private File enterPressFX;
    private File buttonPressFX;
    private File zombie1FX;
    private File zombie2FX;
    private File zombie3FX;
    private BufferedImage youDiedBG;
    private BufferedImage youPausedBG;

    private Font arcadeClassic;
    private Font arcadeClassicSmall;
    private static AssetManager instance = null;

    private AssetManager()
    {
    }

    public static AssetManager getInstance()
    {
        if(instance == null)
        {
            instance = new AssetManager();
        }
        return instance;
    }

    public boolean load()
    {
        try
        {
            map = ImageLoader.loadImage(Config.MAP_PATH);
            player = ImageLoader.loadImage(Config.PLAYER_SPRITE_PATH);
            zombie = ImageLoader.loadImage(Config.ZOMBIE_ASSET_PATH);
            crate = ImageLoader.loadImage(Config.CRATE_ASSET_PATH);
            HUD = ImageLoader.loadImage(Config.HUD_ASSET_PATH);
            item = ImageLoader.loadImage(Config.ITEMS_ASSET_PATH);
            menuButtonImage = ImageLoader.loadImage(Config.MENU_BUTTON_ASSET_PATH);
            difficultyImage = ImageLoader.loadImage(Config.DIFFICULTY_ASSET_PATH);
            defaultBGImage = ImageLoader.loadImage(Config.MENU_BACKGROUND_ASSET_PATH);
            dottedBGImage = ImageLoader.loadImage(Config.DOTTED_BACKGROUND_ASSET_PATH);
            exitBGImage = ImageLoader.loadImage(Config.EXIT_MENU_ASSET_PATH);
            survivalBrokenBGImage = ImageLoader.loadImage(Config.BROKEN_SURVIVAL_BACKGROUND_ASSET_PATH);
            storyBrokenBGImage = ImageLoader.loadImage(Config.BROKEN_STORY_BACKGROUND_ASSET_PATH);
            pausedImage = ImageLoader.loadImage(Config.PAUSED_TEXT_ASSET_PATH);
            youDiedImage = ImageLoader.loadImage(Config.YOU_DIED_ASSET_PATH);
            youDiedBG = ImageLoader.loadImage(Config.DIED_BG_PATH);
            youPausedBG = ImageLoader.loadImage(Config.PAUSE_BG_PATH);

            menuBGM = AudioLoader.loadAudio(Config.MAIN_MENU_BG_MUSIC_PATH);
            gameBGM = AudioLoader.loadAudio(Config.IN_GAME_MUSIC_PATH);
            knifeFX = AudioLoader.loadAudio(Config.KNIFE_STAB_MUSIC_PATH);
            enterPressFX = AudioLoader.loadAudio(Config.ENTER_GAME_PATH);
            buttonPressFX = AudioLoader.loadAudio(Config.BUTTON_PRESS_PATH);
            zombie1FX = AudioLoader.loadAudio(Config.ZOMBIE_1_MUSIC_PATH);
            zombie2FX = AudioLoader.loadAudio(Config.ZOMBIE_2_MUSIC_PATH);
            zombie3FX = AudioLoader.loadAudio(Config.ZOMBIE_3_MUSIC_PATH);

            arcadeClassicSmall = Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.TTF")).deriveFont(30f);


            arcadeClassic = Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.TTF")).deriveFont(72f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.TTF")));
//            treeObstacle = ImageLoader.loadImage(Config.TREE_OBSTACLE_ASSET_PATH);
        }
        catch(IllegalArgumentException | IOException | FontFormatException e)
        {
            System.err.println("Failed to load assets.");
            return false;
        }
        return true;
    }

    public BufferedImage getPlayer()
    {
        return player;
    }

    public BufferedImage getZombie()
    {
        return zombie;
    }

    public BufferedImage getCrate()
    {
        return crate;
    }

    public BufferedImage getItem()
    {
        return item;
    }

    public BufferedImage getMenuButtonImage() {
        return menuButtonImage;
    }

    public BufferedImage getDifficultyImage() {
        return difficultyImage;
    }

    public BufferedImage getDefaultBGImage() {
        return defaultBGImage;
    }

    public BufferedImage getDottedBGImage() {
        return dottedBGImage;
    }

    public BufferedImage getSurvivalBrokenBGImage() {
        return survivalBrokenBGImage;
    }

    public BufferedImage getStoryBrokenBGImage() {
        return storyBrokenBGImage;
    }

    public BufferedImage getExitBGImage() {
        return exitBGImage;
    }

    public BufferedImage getPausedImage(){
        return pausedImage;
    }

    public BufferedImage getHUD() {
        return HUD;
    }

    public BufferedImage getYouDiedImage() {
        return youDiedImage;
    }

    public BufferedImage getTreeObstacle()
    {
        return treeObstacle;
    }

    public BufferedImage getMap()
    {
        return map;
    }

    public AudioInputStream getMenuBGM()
    {
        try
        {
            return AudioSystem.getAudioInputStream(menuBGM);
        }
        catch(UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public AudioInputStream getGameBGM() {
        try
        {
            return AudioSystem.getAudioInputStream(gameBGM);
        }
        catch(UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public AudioInputStream getKnifeFX() {
        try
        {
            return AudioSystem.getAudioInputStream(knifeFX);
        }
        catch(UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public AudioInputStream getEnterPressFX() {
        try
        {
            return AudioSystem.getAudioInputStream(enterPressFX);
        }
        catch(UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public AudioInputStream getButtonPressFX() {
        try
        {
            return AudioSystem.getAudioInputStream(buttonPressFX);
        }
        catch(UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public AudioInputStream getZombie1FX() {
        try
        {
            return AudioSystem.getAudioInputStream(zombie1FX);
        }
        catch(UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public AudioInputStream getZombie2FX() {
        try
        {
            return AudioSystem.getAudioInputStream(zombie2FX);
        }
        catch(UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public AudioInputStream getZombie3FX() {
        try
        {
            return AudioSystem.getAudioInputStream(zombie3FX);
        }
        catch(UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Font getArcadeClassicSmall() {
        return arcadeClassicSmall;
    }

    public Font getArcadeClassic() {
        return arcadeClassic;
    }

    public BufferedImage getYouDiedBG() {
        return youDiedBG;
    }

    public BufferedImage getYouPausedBG() {
        return youPausedBG;
    }
}
