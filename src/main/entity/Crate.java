package main.entity;

import main.gfx.ImageLoader;

import java.awt.*;

import java.awt.image.BufferedImage;

public class Crate extends Entity{

    private Point pos;
    private Point size;
    private Point imagePos;
    private BufferedImage picture;
    private BufferedImage image;
    private BufferedImage clickedBox;

    String[] items = {"Heart", "Apple", "Boots", "Attack Boost", "Defense Boost"};

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
//    @Override
//    public void update(float delta){
//        time += delta;
//        System.out.println(time);
//        if(time > maxTime){
//            System.out.println("werqfaswe");
//        }
//    }

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

    public void loadTexture(Point imagePos, Point imageSize, String path)
    {
        this.imagePos = imagePos;
        picture = ImageLoader.loadImage(path);
        image = picture.getSubimage((int)imagePos.getX(), (int)imagePos.getY(), (int)imageSize.getX(), (int)imageSize.getY());
        clickedBox = picture.getSubimage((int) imagePos.getX() + (1 * (int) (imageSize.getX())), (int) imagePos.getY(), (int) imageSize.getX(), (int) imageSize.getY());
    }

    public BufferedImage getClickedBox() {
        return clickedBox;
    }


    public Crate(Point pos, Point size){
        this.pos = pos;
        this.size = size;
    };

    public boolean isInside(float x, float y) {

        if ((x <= this.getSize().getX() + this.getPos().getX() && x >= this.getPos().getX()) &&
                (y <= this.getSize().getY() + this.getPos().getY() && y >= this.getPos().getY())) {
            return true;
        }
        return false;
    }

    public Item randomDrop(){

        int select = (int) (Math.random()*100 % items.length);

        return new Item(items[select]);
    }
}
