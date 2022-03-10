package main.entity;

import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected Point pos;
    protected Point size;
    protected BufferedImage texture;
    protected int animationSpeed;
    protected int movementSpeed;

    float time;
    float maxTime = 5;

    public void move(Point vel) {
        pos.translate(vel.x, vel.y);
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
        if (size.x < 0) {
            size.x = 0;
        }
        if (size.y < 0) {
            size.y = 0;
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

}
