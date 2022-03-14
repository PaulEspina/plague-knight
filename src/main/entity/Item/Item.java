package main.entity.Item;


import main.Config;
import main.Vector2f;
import main.entity.Entity;
import main.entity.player.Player;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item extends Entity {

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

    private BufferedImage asset;
    private BufferedImage[] images;
    private Type type;
    private int randomize;

    private Boolean shouldShow;

//pickup items beta

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

    public Item(Vector2f pos, Vector2f size, Type type){
        this(type);
        this.pos = pos;
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public void show(){
        shouldShow = true;
    }

    public void hide(){
        shouldShow = false;
    }

    public boolean checkBounds(Player player){
        int playerPosX = (int) player.getPos().getX();  //30
        int playerPosY = (int) player.getPos().getY();  //30
        int playerSizeX = (int) player.getSize().getX();    //30
        int playerSizeY = (int) player.getSize().getY();    //20
        int itemPosX = (int) pos.getX();    //50
        int itemPosY = (int) pos.getY();    //50
        int itemSizeX = (int) size.getX();  //30
        int itemSizeY = (int) size.getY();  //30
//            System.out.println("item x = " + ((int) pos.getX() - (int) size.getX() / 2));
//            System.out.println("item y = " + ((int) pos.getY() + (int) size.getY() / 2));
//            System.out.println("Player x = " + ((int) player.getPos().getX() - (int) player.getSize().getX() / 2));
//            System.out.println("Player Y = " + ((int) player.getPos().getY() + (int) player.getSize().getY() / 2));

//        (((playerPosX + playerSizeX / 2) - 15 >= (itemPosX - itemSizeX / 2)) &&   //Right
//                ((playerPosX - playerSizeX / 2) + 15 <= (itemPosX + itemSizeX / 2)) &&  //Left
//                ((playerPosY + playerSizeY / 2) - 15 >= (itemPosY - itemSizeY / 2)) &&  //Down
//                ((playerPosY) <= (itemPosY + itemSizeY / 2))){  //Up
        if (shouldShow) {
            if(((playerPosX) >= (itemPosX - itemSizeX / 2)) &&   //Right
                    ((playerPosX) <= (itemPosX + itemSizeX / 2)) &&  //Left
                    ((playerPosY + playerSizeY / 2) >= (itemPosY - itemSizeY / 2)) &&  //Down
                    ((playerPosY) <= (itemPosY + itemSizeY / 2))){  //Up
                return true;
            }
        }
        return false;
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
            g.drawImage(images[type.getValue()],(int) pos.getX() - (int) size.getX() / 2, (int) pos.getY() - (int) size.getY() / 2, (int) size.getX(), (int) size.getY(), null);
//            System.out.println("item x = " + pos.getX());
//            System.out.println("item y = " + pos.getY());
//            System.out.println("Player x = " + player.getPos().getX());
//            System.out.println("Player Y = " + player.getPos().getY());
        }
    }
}
