package main.gfx;

import main.Config;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AssetManager
{
    private BufferedImage player;
    private BufferedImage zombie;
    private BufferedImage crate;
    private BufferedImage item;

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
            item = ImageLoader.loadImage(Config.ITEMS_ASSET_PATH);
        }
        catch(IllegalArgumentException e)
        {
            System.err.println("Failed to load assets.");
            return false;
        }
        return true;
    }

    public BufferedImage getPlayer(){ return player; }
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
}
