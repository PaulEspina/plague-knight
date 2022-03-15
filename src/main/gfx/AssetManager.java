package main.gfx;

import main.Config;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AssetManager
{
    private BufferedImage player;
    private BufferedImage HUD;
    private BufferedImage zombie;
    private BufferedImage crate;
    private BufferedImage item;
    private BufferedImage menuButtonImage;
    private BufferedImage defaultBGImage;
    private BufferedImage dottedBGImage;
    private BufferedImage survivalBrokenBGImage;
    private BufferedImage storyBrokenBGImage;
    private BufferedImage pausedImage;
    private BufferedImage youDiedImage;

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
            player = ImageLoader.loadImage(Config.PLAYER_SPRITE_PATH);
            zombie = ImageLoader.loadImage(Config.ZOMBIE_ASSET_PATH);
            crate = ImageLoader.loadImage(Config.CRATE_ASSET_PATH);
            HUD = ImageLoader.loadImage(Config.HUD_ASSET_PATH);
            item = ImageLoader.loadImage(Config.ITEMS_ASSET_PATH);
            menuButtonImage = ImageLoader.loadImage(Config.MENU_BUTTON_ASSET_PATH);
            defaultBGImage = ImageLoader.loadImage(Config.MENU_BACKGROUND_ASSET_PATH);
            dottedBGImage = ImageLoader.loadImage(Config.DOTTED_BACKGROUND_ASSET_PATH);
            survivalBrokenBGImage = ImageLoader.loadImage(Config.BROKEN_SURVIVAL_BACKGROUND_ASSET_PATH);
            storyBrokenBGImage = ImageLoader.loadImage(Config.BROKEN_STORY_BACKGROUND_ASSET_PATH);
            pausedImage = ImageLoader.loadImage(Config.PAUSED_TEXT_ASSET_PATH);
            youDiedImage = ImageLoader.loadImage(Config.YOU_DIED_ASSET_PATH);
        }
        catch(IllegalArgumentException e)
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

    public BufferedImage getPausedImage(){
        return pausedImage;
    }

    public BufferedImage getHUD() {
        return HUD;
    }

    public BufferedImage getYouDiedImage() {
        return youDiedImage;
    }
}
