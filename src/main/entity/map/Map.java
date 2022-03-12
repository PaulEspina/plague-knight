package main.entity.map;

import main.Drawable;
import main.Vector2f;
import main.entity.Entity;

import java.awt.*;

public class Map extends Entity implements Drawable {

    private String type;

    public Map(Vector2f pos, Vector2f size)
    {
        this.pos = pos;
        this.size = size;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawRect((int) pos.getX(), (int) pos.getY(), (int) size.getX(), (int) size.getY());
    }
}
