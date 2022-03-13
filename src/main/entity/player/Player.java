package main.entity.player;

import main.Config;
import main.Drawable;
import main.Vector2f;
import main.entity.Item;
import main.gfx.AssetManager;
import main.input.KeyManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Character implements Drawable{

    private KeyManager keyManager;
    private Vector2f vel;
    private String direction;
    private BufferedImage sprite;
    private BufferedImage[] images;
    private BufferedImage[] currentImages;
    private int animationIndex;

    public Player()
    {
        sprite = null;
        images = new BufferedImage[12];
        currentImages = new BufferedImage[3];
        animationIndex = 0;
        animationSpeed = 20;
        direction = "north";
    }

    public Player(Vector2f pos, Vector2f size)
    {
        this();
        this.pos = pos;
        this.size = size;
        vel = new Vector2f(0, 0);
        sprite = AssetManager.getInstance().getPlayer();
        for(int i = 0; i < 12; i++)
        {
            images[i] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * i, 0, Config.PLAYER_SPRITE_WIDTH, Config.PLAYER_SPRITE_HEIGHT);
        }
        currentImages[0] = images[0];
        currentImages[1] = images[1];
        currentImages[2] = images[2];
    }

    @Override
    public void update()
    {
        pos.add(vel);
//        pos.setX(clamp((int)pos.getX(), 10, Config.SCREEN_WIDTH - 46));
//        pos.setY(clamp((int)pos.getY(), 10, Config.SCREEN_HEIGHT - 58));
        checkRotation();
    }

    @Override
    public void draw(Graphics g)
    {
        g.drawImage(currentImages[animationIndex], (int) pos.getX() - (int) size.getX() / 2,
                    (int) pos.getY() - (int) size.getY() / 2,
                    (int) size.getX(),
                    (int) size.getY(),
                    null);
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

    public void addItem(Item item){
        System.out.println("Picked up!!");
    }

    public void checkRotation()
    {
        switch(direction)
        {
            case "north":
                currentImages[0] = images[9];
                currentImages[1] = images[10];
                currentImages[2] = images[11];
                break;
            case "south":
                currentImages[0] = images[0];
                currentImages[1] = images[1];
                currentImages[2] = images[2];
                break;
            case "west":
                currentImages[0] = images[3];
                currentImages[1] = images[4];
                currentImages[2] = images[5];
                break;
            case "east":
                currentImages[0] = images[6];
                currentImages[1] = images[7];
                currentImages[2] = images[8];
                break;
        }
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

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }
}
