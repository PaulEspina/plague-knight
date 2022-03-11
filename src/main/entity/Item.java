package main.entity;


import main.Config;
import main.Vector2f;
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
    private Type type;

    private Boolean shouldShow;

    public Item(){
        pos = new Vector2f(0, 0);
        size = new Vector2f(0, 0);
        shouldShow = true;
    }

    public Item(Type type){
        this();
        this.type = type;

        this.imagePos = imagePos;
        // TODO get images (waiting for jyron)
//        asset = ImageLoader.loadImage(Config.ITEMS_ASSET_PATH);
//        image = asset.getSubimage(0, 0, (int)imageSize.getX(), (int)imageSize.getY());
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

    @Override
    public void update()
    {

    }

    @Override
    public void draw(Graphics g)
    {
        if(shouldShow)
        {
            g.drawRect((int) pos.getX() - (int) size.getX() / 2, (int) pos.getY() - (int) size.getY() / 2, (int) size.getX(), (int) size.getY());
//            g.drawImage(image, (int) pos.getX(), (int) pos.getY(), (int) size.getX(), (int) size.getY(), null);
        }
    }
}
