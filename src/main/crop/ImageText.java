package main.crop;

import main.Config;
import main.Game;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageText {
    private Game game;
    private Point pos;
    private Point size;
    private String name;

    private BufferedImage pauseImage;
    private BufferedImage currentImage;
    private BufferedImage pausedImage;
    private BufferedImage noImage;

    public ImageText(Game game, Point pos, Point size, String name){
        this.game = game;
        this.pos = pos;
        this.size = size;
        this.name = name;

        pauseImage = AssetManager.getInstance().getPausedImage();

        currentImage = pauseImage.getSubimage(0, 0, Config.PAUSED_TEXT_WIDTH, Config.PAUSED_TEXT_HEIGHT);
    }


    public void showPausedImage(){
        pausedImage = currentImage;
    }


    public void draw(Graphics g){
        g.drawImage(currentImage, (int) getPos().getX(), (int) getPos().getY(), (int) getSize().getX(), (int) getSize().getY(), null);

    }

    public Point getPos() {
        return pos;
    }

    public Point getSize() {
        return size;
    }
}
