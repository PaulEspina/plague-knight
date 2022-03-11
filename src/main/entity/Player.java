package main.entity;

import main.Drawable;
import main.Game;
import main.Vector2f;
import main.gfx.ImageLoader;
import main.states.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player extends Entity implements Drawable{

    private Vector2f vel;
    private String direction;
    private BufferedImage sprite;
    private BufferedImage[] images;
    private int animationIndex;

    public Player(Vector2f pos, Vector2f size)
    {
        this.pos = pos;
        this.size = size;
        vel = new Vector2f(0, 0);
    }

    @Override
    public void update()
    {
        pos.add(vel);
    }

    @Override
    public void draw(Graphics g)
    {
        g.drawRect((int) pos.getX(), (int) pos.getY(), (int) size.getX(), (int) size.getY());
//        g.drawImage(player.getNewCharacter(),
//                    (int)player.getPos().getX(),
//                    (int)player.getPos().getY(),
//                    (int)player.getSize().getX(),
//                    (int)player.getSize().getY(),
//                    null);
    }

//    public void multiply(double speed)
//    {
//        velX *= speed;
//        velY *= speed;
//    }


//    public void loadTexture(Point imagePos, Point imageSize, String path)
//    {
//        this.imagePos = imagePos;
//        character = ImageLoader.loadImage(path);
//        newCharacter = character.getSubimage((int)imagePos.getX(), (int)imagePos.getY(), (int)imageSize.getX(), (int)imageSize.getY());
//    }
//
//    public void setFrame(Point imagePos, Point imageSize)
//    {
//        this.imagePos = imagePos;
//        newCharacter = character.getSubimage((int)imagePos.getX(), (int)imagePos.getY(), (int)imageSize.getX(), (int)imageSize.getY());
//    }

    public void setVelX(float velX)
    {
        vel.setX(velX);
    }
    public void setVelY(float velY)
    {
        vel.setY(velY);
    }
    public float getVelX()
    {
        return vel.getX();
    }

    public float getVelY()
    {
        return vel.getY();
    }

    public Vector2f getVel()
    {
        return vel;
    }

    public void setVel(Vector2f vel)
    {
        this.vel = vel;
    }
}
