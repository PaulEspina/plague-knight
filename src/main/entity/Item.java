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
        this.player = player;
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

    public void itemIsInRadius(Player player){
            System.out.println("item x = " + (int) pos.getX());
            System.out.println("item y = " + (int) pos.getY());
            System.out.println("Player x = " + (int) player.getPos().getX());
            System.out.println("Player Y = " + (int) player.getPos().getY());
//            Need dynamic array for items to check location of the item since item's pos will change every new spawn
//        if(((int) player.getPos().getX() == (int) getPos().getX()) &&
//                ((int) player.getPos().getY() == (int) getPos().getY())){
//            return true;
//        }
//        return false;
    }
    @Override
    public void update()
    {
        itemIsInRadius(player);
//        if(itemIsInRadius(player)){
//            System.out.println("PICKED UP");
//            pickUp();
//        }
    }

    @Override
    public void draw(Graphics g)
    {
        if(shouldShow)
        {
            g.drawImage(images[type.getValue()],(int) pos.getX() - (int) size.getX() / 2, (int) pos.getY() - (int) size.getY() / 2, (int) size.getX(), (int) size.getY(), null);
//            System.out.println("item x = " + pos.getX());
//            System.out.println("item y = " + pos.getY());
//            System.out.println("Player x = " + player.getPos().getX());
//            System.out.println("Player Y = " + player.getPos().getY());
        }
    }
}
