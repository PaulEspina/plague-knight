package main.button;

import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button{
    private Point pos;
    private Point size;
    private Point imagePos;
    private BufferedImage picture;
    private BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public Point getImagePos(){ return imagePos; }
    public void setImagePos(Point imagePos){ this.imagePos = imagePos; }
    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public Point getSize() {
        return size;
    }

    public void setSize(Point size) {
        this.size = size;
    }

    public Button(Point pos, Point size){
        this.pos = pos;
        this.size = size;
    }

    public void tick(){

    }
    public void draw(Graphics g){

    }

    //    This function is used to load menu buttons and cut the sprite sheet
    public void loadTexture(Point imagePos, Point imageSize, String path){

        this.imagePos = imagePos;
//        "/assets/menu/menubuttons/menubuttons.png"

//        Get Sprite sheet
        picture = ImageLoader.loadImage(path);

//        Slice sprite through coordinates
        image = picture.getSubimage((int) imagePos.getX(), (int) imagePos.getY(), (int) imageSize.getX(), (int) imageSize.getY());
    }
    public void setFrame(Point imagePos, Point imageSize){
        this.imagePos = imagePos;


        image = picture.getSubimage((int) imagePos.getX(), (int) imagePos.getY(), (int) imageSize.getX(), (int) imageSize.getY());
    }
}
