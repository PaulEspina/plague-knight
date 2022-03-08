package main.entity;

import main.gfx.ImageLoader;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Crate extends Entity{

    private Point pos;
    private Point size;
    private Point imagePos;
    private BufferedImage picture;
    private BufferedImage image;


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



    public void loadTexture(Point imagePos, Point imageSize, String path)
    {
        this.imagePos = imagePos;
        picture = ImageLoader.loadImage(path);
        image = picture.getSubimage((int)imagePos.getX(), (int)imagePos.getY(), (int)imageSize.getX(), (int)imageSize.getY());
    }

    public Crate(Point pos, Point size){
        this.pos = pos;
        this.size = size;
    };


//    public void randomDrop(){
//        String[] Items = {"Heart", "Apple","Boots", "Attack Boosts","Defense Boost"};
//        Random drop = new Random();
//        int randid = drop.nextInt(Items.length);
//
//        if(randid == 1){
//            //store heart into inventory
//        }
//    }
}
