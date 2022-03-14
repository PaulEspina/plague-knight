package main.entity;

import main.Drawable;
import main.Vector2f;
import main.gfx.ImageLoader;

import java.awt.image.BufferedImage;

public abstract class Entity implements Drawable
{
    protected Vector2f pos;
    protected Vector2f size;
    protected BufferedImage texture;
    protected int animationSpeed;
    protected float movementSpeed;

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

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        if (movementSpeed < 0) {
            movementSpeed = 0;
        }
        this.movementSpeed = movementSpeed;
    }
}
