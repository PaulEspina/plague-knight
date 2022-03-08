package main.entity;

import java.awt.*;

public abstract class Entity
{
    private Point pos;
    private Point size;
    String texture;
    int animationSpeed;
    int movementSpeed;

    public void move(Point vel)
    {
        pos.translate(vel.x, vel.y);
    }
}
