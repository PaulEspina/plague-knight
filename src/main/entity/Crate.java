package main.entity;

import main.Config;
import main.Vector2f;
import main.gfx.AssetManager;
import main.gfx.ImageLoader;

import java.awt.*;

import java.awt.image.BufferedImage;

public class Crate extends Entity{

    private BufferedImage asset;
    private final BufferedImage[] images;
    private boolean destroyed;

    public Crate()
    {
        asset = null;
        images = new BufferedImage[2];
        destroyed = false;
    }

    public Crate(Vector2f pos, Vector2f size){
        this();
        this.pos = pos;
        this.size = size;
        asset = AssetManager.getInstance().getCrate();
        images[0] = asset.getSubimage(0,
                                       0,
                                       (int) size.getX(),
                                       (int) size.getY());

        images[1] = asset.getSubimage((int) (size.getX() * 1),
                                       0,
                                       (int) size.getX(),
                                       (int) size.getY());
    };

    public boolean isInside(float x, float y) {

        return (x <= this.getSize().getX() + this.getPos().getX() && x >= this.getPos().getX()) &&
               (y <= this.getSize().getY() + this.getPos().getY() && y >= this.getPos().getY());
    }

    public Item destroy()
    {
        destroyed = true;
        int select = (int) (Math.random() * 100 % Config.TOTAL_ITEMS);
        return new Item(Item.Type.values()[select]);
        // TODO animate
    }

    @Override
    public void update()
    {

    }

    @Override
    public void draw(Graphics g)
    {
        g.drawImage(destroyed ? images[1] : images[0], (int) pos.getX(), (int) pos.getY(), (int) size.getX() / 2, (int) size.getY() / 2, null);
    }
}
