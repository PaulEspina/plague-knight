package main.button;

import main.Config;
import main.Game;
import main.gfx.AssetManager;
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

    private BufferedImage screen;
    private BufferedImage currentScreen;

    private BufferedImage menuButtonImage;
    private BufferedImage[] buttons = new BufferedImage[4];
    private BufferedImage currentImage;

    public Button(Game game, Point pos, Point size, int buttonAsset, String buttonName){
        this.game = game;
        this.pos = pos;
        this.size = size;
        this.buttonName = buttonName;

        menuButtonImage = AssetManager.getInstance().getMenuButtonImage();
        for(int i = 0; i < 4; i++){
            buttons[i] = menuButtonImage.getSubimage(i * Config.BUTTON_ASSET_WIDTH, buttonAsset, Config.BUTTON_ASSET_WIDTH, Config.BUTTON_ASSET_HEIGHT);
        }
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

    public void unhoveredImage(){
    currentImage =  buttons[0];
}
    public void hoveredImage(){
        currentImage =  buttons[1];
    }

    public void clickedImage(){
        currentImage =  buttons[2];
    }
    public void disabledImage() {
        currentImage = buttons[3];
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
