package main.entity.player;

import main.Config;
import main.Drawable;
import main.Game;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Heart implements Drawable {
    private Game game;
    private Point pos;
    private Point size;
    private String name;

    private BufferedImage image;
    private BufferedImage[] heartImage;
    private int heartStatus;
    private int heartCount;
    private int currentHeartCount;

    public Heart(Point pos, Point size, String name){
        this.pos = pos;
        this.size = size;
        this.name = name;

        heartImage = new BufferedImage[2];

        image = AssetManager.getInstance().getHUD();
        for(int i = 0; i < 2; i++){
            heartImage[i] = image.getSubimage(i * Config.HEART_WIDTH, 0, Config.HEART_WIDTH, Config.HEART_HEIGHT);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g){
        for(int i = 0; i < heartCount; i++){
            g.drawImage(heartImage[1], (int) getPos().getX() + i * (int) getSize().getX(), (int) getPos().getY(), (int) getSize().getX(), (int) getSize().getY(), null);
        }
        for(int i = 0; i < currentHeartCount; i++){
            g.drawImage(heartImage[0], (int) getPos().getX() + i * (int) getSize().getX(), (int) getPos().getY(), (int) getSize().getX(), (int) getSize().getY(), null);
        }
    }

    public Point getPos() {
        return pos;
    }

    public Point getSize() {
        return size;
    }

    public int getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(int heartCount) {
        this.heartCount = heartCount;
    }

    public int getCurrentHeartCount() {
        return currentHeartCount;
    }

    public void setCurrentHeartCount(int currentHeartCount) {
        this.currentHeartCount = currentHeartCount;
    }
}
