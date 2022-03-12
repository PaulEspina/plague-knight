package main.button;

import main.Config;
import main.Game;
import main.gfx.AssetManager;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen {
    private Game game;
    private Point pos;
    private Point size;
    private Point imagePos;
    private Point imageSize;
    private String screenName;
    private BufferedImage screen;
    private BufferedImage currentScreen;
    public Screen(Game game, Point pos, Point size, String screenName){
        this.game = game;
        this.pos = pos;
        this.size = size;
        this.screenName = screenName;
    }
    public void loadScreen(Point imagePos, Point imageSize, String path){

//        "/assets/menu/menubuttons/menubuttons.png"

//        Get Sprite sheet
        screen = ImageLoader.loadImage(path);

//        Slice sprite through coordinates
        currentScreen = screen.getSubimage((int) imagePos.getX(), (int) imagePos.getY(), (int) imageSize.getX(), (int) imageSize.getY());
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

    public Point getImagePos() {
        return imagePos;
    }

    public void setImagePos(Point imagePos) {
        this.imagePos = imagePos;
    }

    public Point getImageSize() {
        return imageSize;
    }

    public void setImageSize(Point imageSize) {
        this.imageSize = imageSize;
    }

    public BufferedImage getCurrentScreen(){
        return currentScreen;
    }

    public void draw(Graphics g){
        g.drawImage(currentScreen, (int) getPos().getX(), (int) getPos().getY(), (int) getSize().getX(), (int) getSize().getY(), null);
    }
}