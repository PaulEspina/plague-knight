package main.entity.player;

import main.Config;
import main.Drawable;
import main.Vector2f;
import main.gfx.AssetManager;
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
        sprite = null;
        images = new BufferedImage[3];
        animationIndex = 0;
        animationSpeed = 20;
    }

    public Player(Vector2f pos, Vector2f size)
    {
        this(pos, size, "normal");
        images = new BufferedImage[3];
    }

    public Player(Vector2f pos, Vector2f size, String type)
    {
        this();
        this.pos = pos;
        this.size = size;
        this.type = type;;
        vel = new Vector2f(0, 0);
        sprite = AssetManager.getInstance().getPlayer();
        sprite = sprite.getSubimage(0, Config.PLAYER_SPRITE_HEIGHT * 0, Config.PLAYER_SPRITE_WIDTH * 12, (int) size.getY());
        images[0] = sprite.getSubimage(0,
                0,
                Config.PLAYER_SPRITE_WIDTH,
                Config.PLAYER_SPRITE_HEIGHT);
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
        if(north)
        {
            images[0] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * 9,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
            images[1] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * 10,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
            images[2] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * 11,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
        }
        if(south)
        {
            images[0] = sprite.getSubimage(0,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
            images[1] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
            images[2] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * 2,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
        }
        if(east)
        {
            images[0] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * 6,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
            images[1] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * 7,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
            images[2] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * 8,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
        }
        if(west)
        {
            images[0] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * 3,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
            images[1] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * 4,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
            images[2] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * 5,
                    0,
                    Config.PLAYER_SPRITE_WIDTH,
                    Config.PLAYER_SPRITE_HEIGHT);
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
        g.drawImage(images[animationIndex], (int) pos.getX() - (int) size.getX() / 2,
                                            (int) pos.getY() - (int) size.getY() / 2,
                                            (int) size.getX(),
                                            (int) size.getY(),
                                            null);
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
