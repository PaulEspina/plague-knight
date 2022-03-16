package main.crop;

import main.Config;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Difficulty {
    private Point pos;
    private Point size;
    private String name;

    private BufferedImage image;
    private BufferedImage[] difficultyImage;
    private BufferedImage currentDifficulty;

    public Difficulty(Point pos, Point size, int difficultyButton, String name){
        this.pos = pos;
        this.size = size;
        this.name = name;

        image = AssetManager.getInstance().getDifficultyImage();

        difficultyImage = new BufferedImage[3];
        for(int i = 0; i < 3; i++){
            difficultyImage[i] = image.getSubimage(i * Config.BUTTON_ASSET_WIDTH, difficultyButton, Config.BUTTON_ASSET_WIDTH, Config.BUTTON_ASSET_HEIGHT);
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

    public void unhoveredImage(){
        currentDifficulty =  difficultyImage[2];
    }
    public void hoveredImage(){
        currentDifficulty =  difficultyImage[1];
    }

    public void clickedImage(){
        currentDifficulty =  difficultyImage[0];
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
        g.drawImage(currentDifficulty, (int) getPos().getX(), (int) getPos().getY(), (int) getSize().getX(), (int) getSize().getY(), null);
    }

}
