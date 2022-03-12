package main.entity;


import main.Config;
import main.Vector2f;
import main.gfx.AssetManager;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item extends Entity{

    public enum Type {
        HEART,
        APPLE,
        BOOTS,
        ATTACK_BOOST,
        DEFENSE_BOOST
    }

    private Vector2f pos;
    private Vector2f size;
    private Vector2f imagePos;
    private BufferedImage asset;
    private BufferedImage image;
    private BufferedImage[] items;
    private Type type;
    private int randomize;

    private Boolean shouldShow;

    public Item(){
        pos = new Vector2f(0, 0);
        size = new Vector2f(0, 0);
        items = new BufferedImage[5];
        shouldShow = true;
    }

    public Item(Type type){
        this();
        this.type = type;

        this.imagePos = imagePos;
        // TODO get images (waiting for jyron)
        asset = ImageLoader.loadImage(Config.ITEMS_ASSET_PATH);
        image = AssetManager.getInstance().getItem();
        image = asset.getSubimage(0, 0,Config.ITEMS_ASSET_WIDTH, Config.ITEMS_ASSET_HEIGHT);

        items[0] = asset.getSubimage(0,
                                     0,
                                    Config.ITEMS_ASSET_WIDTH,
                                    Config.ITEMS_ASSET_HEIGHT);
        items[1] = asset.getSubimage(Config.ITEMS_ASSET_WIDTH * 11,
                                     0,
                                    Config.ITEMS_ASSET_WIDTH,
                                    Config.ITEMS_ASSET_HEIGHT);
        items[2] = asset.getSubimage(Config.ITEMS_ASSET_WIDTH * 12,
                                     0,
                                    Config.ITEMS_ASSET_WIDTH,
                                    Config.ITEMS_ASSET_HEIGHT);
        items[3] = asset.getSubimage(Config.ITEMS_ASSET_WIDTH* 13,
                                     0,
                                    Config.ITEMS_ASSET_WIDTH,
                                    Config.ITEMS_ASSET_HEIGHT);
        items[4] = asset.getSubimage(Config.ITEMS_ASSET_WIDTH * 14,
                                     0,
                                    Config.ITEMS_ASSET_WIDTH,
                                    Config.ITEMS_ASSET_HEIGHT);


    }

    public Item(Vector2f pos, Vector2f size, Type type){
        this(type);
        this.pos = pos;
        this.size = size;
    }

    public void show(){
        shouldShow = true;
    }

    public void hide(){
        shouldShow = false;
    }

    public void randomize()
    {
        randomize = (randomize + 1) % 4;
    }

    @Override
    public void update()
    {

    }

    @Override
    public void draw(Graphics g)
    {
        if(shouldShow)
        {
            g.drawImage(items[randomize], (int) pos.getX(), (int) pos.getY(), (int) size.getX(), (int) size.getY(), null);
        }
    }
}
