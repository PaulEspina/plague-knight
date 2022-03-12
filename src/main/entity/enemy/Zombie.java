package main.entity.enemy;

import main.Config;
import main.Vector2f;
import main.gfx.AssetManager;
import main.gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends Enemy
{
    private String type;
    private String direction;
    private BufferedImage sprite;
    private BufferedImage[] images;
    private int animationIndex;

    public Zombie()
    {
        type = "normal";
        direction = "south";
        sprite = null;
        images = new BufferedImage[3];
        animationIndex = 0;
        animationSpeed = 20;
        movementSpeed = 1;
    }

    public Zombie(Vector2f pos, Vector2f size)
    {
        this(pos, size, "normal");
        images = new BufferedImage[3];
    }

    public Zombie(Vector2f pos, Vector2f size, String type)
    {
        this();
        this.pos = pos;
        this.size = size;
        this.type = type;
        int randNum = (int) (Math.random() * 100) % 3;
        sprite = AssetManager.getInstance().getZombie();
        sprite = sprite.getSubimage(0, Config.ZOMBIE_ASSET_HEIGHT * randNum, Config.ZOMBIE_ASSET_WIDTH * 12, Config.ZOMBIE_ASSET_HEIGHT);
        images[0] = sprite.getSubimage(0, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
        images[1] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
        images[2] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH  * 2, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
    }

    @Override
    public void attack(int damage)
    {

    }

    @Override
    public void update()
    {
        checkRotation();
    }

    public void follow(Vector2f target)
    {
        try
        {
            Vector2f polarCoord = new Vector2f(target);
            polarCoord.sub(new Vector2f(pos.getX(), pos.getY())); // polarize
            polarCoord.div((float) Math.sqrt(Math.pow(polarCoord.getX(), 2) + Math.pow(polarCoord.getY(), 2))); // normalize

            float theta = (float) Math.atan2(polarCoord.getY(), polarCoord.getX()); // get theta
            float x = (float) Math.cos(theta);
            float y = (float) Math.sin(theta);

            float newDistance = (float) Math.sqrt(Math.pow(Math.abs(target.getX() - (pos.getX() + x)), 2) +
                                                  Math.pow(Math.abs(target.getY() - (pos.getY() + y)), 2));
            if(newDistance >= movementSpeed) // clamp
            {
                pos.add(x * (float) movementSpeed, y * (float) movementSpeed);
            }

            float dtheta = (theta * 180f / (float) Math.PI);
            float thetaDistNorth    = Math.abs(Config.DEGREES_NORTH - dtheta);
            float thetaDistSouth    = Math.abs(Config.DEGREES_SOUTH - dtheta);
            float thetaDistEast     = Math.abs(Config.DEGREES_EAST - dtheta);
            float thetaDistWest     = Math.abs(Config.DEGREES_WEST - Math.abs(dtheta));

            float min = Float.MAX_VALUE;
            if(thetaDistNorth < min)
            {
                direction = "north";
                min = thetaDistNorth;
            }
            if(thetaDistSouth < min)
            {
                direction = "south";
                min = thetaDistSouth;
            }
            if(thetaDistEast < min)
            {
                direction = "east";
                min = thetaDistEast;
            }
            if(thetaDistWest < min)
            {
                direction = "west";
            }
        }
        catch(ArithmeticException e)
        {
            //chow
        }
    }

    private void checkRotation()
    {
        switch(direction)
        {
            case "north":
                images[0] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * 9, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                images[1] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * 10, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                images[2] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * 11, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                break;
            case "south":
                images[0] = sprite.getSubimage(0, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                images[1] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                images[2] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * 2, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                break;
            case "west":
                images[0] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * 3, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                images[1] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * 4, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                images[2] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * 5, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                break;
            case "east":
                images[0] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * 6, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                images[1] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * 7, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                images[2] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * 8, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
                break;
        }
    }

    public void animate()
    {
        animationIndex = (animationIndex + 1) % 3;
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
}
