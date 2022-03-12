package main.entity;

import main.Drawable;
import main.Vector2f;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity implements Drawable
{
    protected Vector2f pos;
    protected Vector2f size;
    protected BufferedImage texture;
    protected int animationSpeed;
    protected int movementSpeed;
    protected boolean north, south, east, west;

    float time;
    float maxTime = 5;

    public void move(Vector2f vel)
    {
        pos.add(vel.getX(), vel.getY());
    }

    public Vector2f getPos()
    {
        return pos;
    }

    public void setPos(Vector2f pos)
    {
        this.pos = pos;
    }

    public Vector2f getSize()
    {
        return size;
    }

    public void setSize(Vector2f size)
    {
        if(size.getX() < 0)
        {
            size.setX(0);
        }
        if(size.getY() < 0)
        {
            size.setY(0);
        }
        this.size = size;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(String path) {
        texture = ImageLoader.loadImage(path);
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(int animationSpeed) {
        if (animationSpeed <= 0) {
            animationSpeed = 1;
        }
        this.animationSpeed = animationSpeed;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        if (movementSpeed < 0) {
            movementSpeed = 0;
        }
        this.movementSpeed = movementSpeed;
    }

//    public void update(float delta){
//        this.update(delta);
//    }

    public void north(boolean north)
    {
        this.north = north;
    }

    public boolean north()
    {
        return north;
    }

    public void south(boolean south)
    {
        this.south = south;
    }

    public boolean south()
    {
        return south;
    }

    public void east(boolean east)
    {
        this.east = east;
    }

    public boolean east()
    {
        return east;
    }

    public void west(boolean west)
    {
        this.west = west;
    }

    public boolean west()
    {
        return west;
    }
}
