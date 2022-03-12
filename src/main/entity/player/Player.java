package main.entity.player;

import main.Config;
import main.Drawable;
import main.Vector2f;
import main.gfx.ImageLoader;
import main.input.KeyManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Character implements Drawable{

    private KeyManager keyManager;
    private Vector2f vel;
    private String type;
    private String direction;
    private BufferedImage sprite;
    private BufferedImage[] images;
    private int animationIndex;

    public Player()
    {
        type = "normal";
        direction = "south";
        sprite = null;
        images = new BufferedImage[3];
        animationIndex = 0;
        animationSpeed = 5;
    }

    public Player(Vector2f pos, Vector2f size)
    {
        this(pos, size, "normal", 1);
        images = new BufferedImage[3];
    }

    public Player(Vector2f pos, Vector2f size, String type, float movementSpeed)
    {
        this();
        this.pos = pos;
        this.size = size;
        this.type = type;
        this.movementSpeed = (int)movementSpeed;
        vel = new Vector2f(0 * movementSpeed, 0 * movementSpeed);
        sprite = ImageLoader.loadImage(Config.PLAYER_SPRITE_PATH);
        sprite = sprite.getSubimage(0, (int) size.getY() * 0, (int) size.getX() * 12, (int) size.getY());
        images[0] = sprite.getSubimage((int) (size.getX() * 2),
                0,
                (int) size.getX(),
                (int) size.getY());
    }

    public void accelerate(double speed)
    {
//        velX *= speed;
//        velY *= speed;
    }

    public int clamp(int var, int min, int max)
    {
        if( var >= max)
            return var = max;
        else if( var <= min)
            return var = min;
        else
            return var;
    }

    public void animate()
    {
        animationIndex = (animationIndex + 1) % 3;
    }

    @Override
    public void attack(int damage)
    {

    }

    public void charMovement()
    {
        //default face
        images[0] = sprite.getSubimage(0, 0, (int) size.getX(), (int) size.getY());
        if(north)
        {
            images[0] = sprite.getSubimage((int) size.getX() * 9,
                    0,
                    (int) size.getX(),
                    (int) size.getY());
            images[1] = sprite.getSubimage((int) size.getX() * 10,
                    0,
                    (int) size.getX(),
                    (int) size.getY());
            images[2] = sprite.getSubimage((int) (size.getX() * 11),
                    0,
                    (int) size.getX(),
                    (int) size.getY());
        }
        if(south)
        {
            images[0] = sprite.getSubimage(0, 0, (int) size.getX(), (int) size.getY());
            images[1] = sprite.getSubimage((int) size.getX(),
                    0,
                    (int) size.getX(),
                    (int) size.getY());
            images[2] = sprite.getSubimage((int) (size.getX() * 2),
                    0,
                    (int) size.getX(),
                    (int) size.getY());
        }
        if(east)
        {
            images[0] = sprite.getSubimage((int) size.getX() * 6, 0, (int) size.getX(), (int) size.getY());
            images[1] = sprite.getSubimage((int) size.getX() * 7,
                    0,
                    (int) size.getX(),
                    (int) size.getY());
            images[2] = sprite.getSubimage((int) (size.getX() * 8),
                    0,
                    (int) size.getX(),
                    (int) size.getY());
        }
        if(west)
        {
            images[0] = sprite.getSubimage((int) size.getX() * 3, 0, (int) size.getX(), (int) size.getY());
            images[1] = sprite.getSubimage((int) size.getX() * 4,
                    0,
                    (int) size.getX(),
                    (int) size.getY());
            images[2] = sprite.getSubimage((int) (size.getX() * 5),
                    0,
                    (int) size.getX(),
                    (int) size.getY());
        }
    }

    @Override
    public void update()
    {
        pos.add(vel);
        pos.setX(clamp((int)pos.getX(), 10, Config.SCREEN_WIDTH - 46));
        pos.setY(clamp((int)pos.getY(), 10, Config.SCREEN_HEIGHT - 58));
        charMovement();
    }

    @Override
    public void draw(Graphics g)
    {
        g.drawRect((int) pos.getX(), (int) pos.getY(), (int) size.getX(), (int) size.getY());
        g.drawImage(images[animationIndex], (int) pos.getX(), (int) pos.getY(), null);
    }



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
