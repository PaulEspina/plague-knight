package main.crop;

import main.Config;
import main.Game;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class YouDied {
    private Point pos;
    private Point size;
    private String name;

    private BufferedImage youDiedImage;
    private BufferedImage currentImage;

    public YouDied(Point pos, Point size, String name){
        this.pos = pos;
        this.size = size;
        this.name = name;

        youDiedImage = AssetManager.getInstance().getYouDiedImage();

        currentImage = youDiedImage.getSubimage(0, 0, Config.YOU_DIED_WIDTH, Config.YOU_DIED_HEIGHT);
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
