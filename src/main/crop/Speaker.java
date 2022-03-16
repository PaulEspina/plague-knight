package main.crop;

import main.Config;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Speaker {
    private Point pos;
    private Point size;
    private String name;

    private BufferedImage image;
    private BufferedImage[] speakerImage;
    private BufferedImage currentSpeakerImage;


    public Speaker(Point pos, Point size, int xPos, int yPos, String name){
        this.pos = pos;
        this.size = size;
        this.name = name;

        speakerImage = new BufferedImage[3];

        image = AssetManager.getInstance().getHUD();
        for(int i = 0; i < 3; i++){
            speakerImage[i] = image.getSubimage(xPos + (i * Config.SPEAKER_WIDTH), yPos, Config.SPEAKER_WIDTH, Config.SPEAKER_HEIGHT);
        }
    }

    public void draw(Graphics g){
        g.drawImage(currentSpeakerImage, (int) getPos().getX(), (int) getPos().getY(), (int) getSize().getX(), (int) getSize().getY(), null);
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

    public void unhoveredImage(){
        currentSpeakerImage = speakerImage[0];
    }

    public void clickedImage(){
        currentSpeakerImage = speakerImage[1];
    }

    public void hoveredImage(){
        currentSpeakerImage = speakerImage[2];
    }

    public Point getPos() {
        return pos;
    }

    public Point getSize() {
        return size;
    }
}
