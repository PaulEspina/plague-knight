package main.entity;

import main.Config;
import main.Vector2f;
import main.gfx.ImageLoader;

import java.awt.*;

import java.awt.image.BufferedImage;

public class Crate extends Entity{

    private BufferedImage asset;
    private BufferedImage image;

    public Crate(Vector2f pos, Vector2f size){
        this.pos = pos;
        this.size = size;
    };

    public boolean isInside(float x, float y) {

        return (x <= this.getSize().getX() + this.getPos().getX() && x >= this.getPos().getX()) &&
               (y <= this.getSize().getY() + this.getPos().getY() && y >= this.getPos().getY());
    }

    public Item destroy(){

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

    }
}
