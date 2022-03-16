package main.crop;

import main.Config;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RankingHUD {

    private Point pos;
    private Point size;

    private BufferedImage image;
    private BufferedImage screen;

    public RankingHUD(Point pos, Point size, String name){
        this.pos = pos;
        this.size = size;

        image = AssetManager.getInstance().getHUD();

        screen = image.getSubimage(0, 48, Config.RANKING_HUD_WIDTH, Config.RANKING_HUD_HEIGHT);
    }

    public void draw(Graphics g){
        g.drawImage(screen, (int) getPos().getX(), (int) getPos().getY(), (int) getSize().getX(), (int) getSize().getY(), null);
    }

    public Point getPos() {
        return pos;
    }

    public Point getSize() {
        return size;
    }
}

