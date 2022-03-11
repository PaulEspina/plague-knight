package main.button;

import main.Config;
import main.Game;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.System.exit;

public class Button{
    private Game game;
    private Point pos;
    private Point size;
    private Point imagePos;
    private Point imageSize;
    private String buttonName;
    private BufferedImage picture;
    private BufferedImage unhoveredImage;
    private BufferedImage hoveredImage;
    private BufferedImage clickedImage;
    private BufferedImage currentImage;


    public BufferedImage getUnhoveredImage() {
        return unhoveredImage;
    }
    public BufferedImage getHoveredImage() {
        return hoveredImage;
    }
    public BufferedImage getClickedImage() {
        return clickedImage;
    }



    public String getButtonName(){
        return buttonName;
    }

    public Point getImageSize() {
        return imageSize;
    }

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

    public Button(Game game, Point pos, Point size, String buttonName){
        this.game = game;
        this.pos = pos;
        this.size = size;
        this.buttonName = buttonName;
    }

    //    This function is used to load menu buttons and cut the sprite sheet
    public void loadTexture(Point imagePos, Point imageSize, String path){

//        "/assets/menu/menubuttons/menubuttons.png"

//        Get Sprite sheet
        picture = ImageLoader.loadImage(path);

//        Slice sprite through coordinates
        unhoveredImage = picture.getSubimage((int) imagePos.getX(), (int) imagePos.getY(), (int) imageSize.getX(), (int) imageSize.getY());

        hoveredImage = picture.getSubimage((int) imagePos.getX() + (1 * (int) (imageSize.getX())), (int) imagePos.getY(), (int) imageSize.getX(), (int) imageSize.getY());

        clickedImage = picture.getSubimage((int) imagePos.getX() + (2 * (int) (imageSize.getX())), (int) imagePos.getY(), (int) imageSize.getX(), (int) imageSize.getY());
    }

//    Used for the screen in main menu
    public void loadScreen(Point imagePos, Point imageSize, String path){

//        "/assets/menu/menubuttons/menubuttons.png"

//        Get Sprite sheet
        picture = ImageLoader.loadImage(path);

//        Slice sprite through coordinates
        unhoveredImage = picture.getSubimage((int) imagePos.getX(), (int) imagePos.getY(), (int) imageSize.getX(), (int) imageSize.getY());

    }

    public void hoveredImage(){
        currentImage = hoveredImage;
    }
    public void unhoveredImage(){
        currentImage = unhoveredImage;
    }
    public void clickedImage(){
        currentImage = clickedImage;
    }

    public void exitGameNotification(){
        System.out.println("Quitting.....");
        exit(0);
    }

    public boolean isInside(float x, float y) {
//        x <= image.width + image.x && x >= image.x
//        y <= image.width + image.y && y >= image.y
        if((x <= getSize().getX() + getPos().getX() && x >=  getPos().getX()) &&
                (y <= getSize().getY() + getPos().getY() && y >= getPos().getY())){
                return true;
            }
            return false;
        }

    public void draw(Graphics g){
        g.drawImage(currentImage, (int) getPos().getX(), (int) getPos().getY(), (int) getSize().getX(), (int) getSize().getY(), null);
    }

}
