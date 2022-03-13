package main.entity;


import main.Config;
import main.Vector2f;
import main.entity.player.Player;
import main.gfx.AssetManager;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item extends Entity{

    public enum Type {
        HEART(0),
        APPLE(1),
        BOOTS(2),
        ATTACK_BOOST(3),
        DEFENSE_BOOST(4);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Vector2f pos;
    private Vector2f size;
    private BufferedImage asset;
    private BufferedImage[] images;
    private Type type;
    private int randomize;

    private Boolean shouldShow;

//pickup items beta
    private Player player;
    private String name;

    public Item(){
        pos = new Vector2f(0, 0);
        size = new Vector2f(0, 0);
        images = new BufferedImage[5];
        shouldShow = true;
    }

    public Item(Type type){
        this();

        this.type = type;
        asset = AssetManager.getInstance().getItem();
        images[Type.ATTACK_BOOST.getValue()] = asset.getSubimage(Config.ITEMS_ASSET_WIDTH * 11, 0, Config.ITEMS_ASSET_WIDTH, Config.ITEMS_ASSET_HEIGHT);
        images[Type.DEFENSE_BOOST.getValue()] = asset.getSubimage(Config.ITEMS_ASSET_WIDTH * 12, 0, Config.ITEMS_ASSET_WIDTH, Config.ITEMS_ASSET_HEIGHT);
        images[Type.BOOTS.getValue()] = asset.getSubimage(Config.ITEMS_ASSET_WIDTH * 13, 0, Config.ITEMS_ASSET_WIDTH, Config.ITEMS_ASSET_HEIGHT);
        images[Type.HEART.getValue()] = asset.getSubimage(Config.ITEMS_ASSET_WIDTH * 14, 0, Config.ITEMS_ASSET_WIDTH, Config.ITEMS_ASSET_HEIGHT);
        images[Type.APPLE.getValue()] = asset.getSubimage(Config.ITEMS_ASSET_WIDTH * 15, 0, Config.ITEMS_ASSET_WIDTH, Config.ITEMS_ASSET_HEIGHT);
    }

    public Item(Player player, Vector2f pos, Vector2f size, Type type){
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

    public void pickUp(){
        System.out.println("Picked up " + name);
        player.addItem(this);
    }
    @Override
    public void update()
    {
//        If may naapakan
//        pickUp();
    }

    @Override
    public void draw(Graphics g)
    {
        if(shouldShow)
        {
            g.drawImage(images[type.getValue()],(int) pos.getX() - (int) size.getX() / 2, (int) pos.getY() - (int) size.getY() / 2, (int) size.getX(), (int) size.getY(), null);
        }
    }
}
