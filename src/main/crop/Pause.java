package main.crop;

import main.Config;
import main.Game;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pause {
    private Point pos;
    private Point size;
    private String pauseName;
    private BufferedImage icon;
    private BufferedImage currentIcon;
    private BufferedImage[] pauseIcon = new BufferedImage[3];
    public Pause(Point pos, Point size, String pauseName){
        this.pos = pos;
        this.size = size;
        this.pauseName = pauseName;

        icon = AssetManager.getInstance().getMenuButtonImage();
        for(int i = 0; i < 3; i++){
            pauseIcon[i] = icon.getSubimage(i * Config.PAUSE_ASSET_WIDTH, 705, Config.PAUSE_ASSET_WIDTH, Config.PAUSE_ASSET_HEIGHT);
        }
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

    public void pausedImage(){
        currentIcon = pauseIcon[0];
    }

    public void hoverImage(){
        currentIcon = pauseIcon[1];
    }

    public void resumeImage(){
        currentIcon = pauseIcon[2];
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
        g.drawImage(currentIcon, (int) getPos().getX(), (int) getPos().getY(), (int) getSize().getX(), (int) getSize().getY(), null);
    }
}
