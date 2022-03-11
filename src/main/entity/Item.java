package main.entity;


import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item extends Entity{


    private Point pos;
    private Point size;
    private Point imagePos;
    private BufferedImage picture;
    private BufferedImage image;
    private String type;

//    String heart;
//    String apple;
//    String boots;
//    String attackBoost;
//    String defenseBoost;
//    String key;
//    String sage;
//    String hyssop;
//    String chamonile;
//    String comfrey;
//    String rue;

    @Override
    public Point getPos() {
        return pos;
    }

    @Override
    public void setPos(Point pos) {
        this.pos = pos;
    }

    @Override
    public Point getSize() {
        return size;
    }

    @Override
    public void setSize(Point size) {
        this.size = size;
    }


    public void setFrame(Point imagePos, Point imageSize) {
        this.imagePos = imagePos;
        image = picture.getSubimage((int)imagePos.getX(), (int)imagePos.getY(), (int)imageSize.getX(), (int)imageSize.getY());
    }

    public Point getImagePos() {
        return imagePos;
    }

    public void setImagePos(Point imagePos) {
        this.imagePos = imagePos;
    }

    public BufferedImage getPicture() {
        return picture;
    }

    public void setPicture(BufferedImage picture) {
        this.picture = picture;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Item(Point pos, Point size){
        this.pos = pos;
        this.size = size;
    }

    public Item(){

    }

    public Item(String type){
        this.type = type;
    }

    public void showItem(){

    }

    public void hideItem(){

    }

    public void loadTexture(Point imagePos, Point imageSize, String path)
    {
        this.imagePos = imagePos;
        picture = ImageLoader.loadImage(path);
        image = picture.getSubimage((int)imagePos.getX(), (int)imagePos.getY(), (int)imageSize.getX(), (int)imageSize.getY());
    }

}
