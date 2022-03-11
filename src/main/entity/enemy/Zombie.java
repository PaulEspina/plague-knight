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
        sprite = sprite.getSubimage(0, (int) size.getY() * randNum, (int) size.getX() * 12, (int) size.getY());
        images[0] = sprite.getSubimage(0,
                                       0,
                                       (int) size.getX(),
                                       (int) size.getY());

        images[1] = sprite.getSubimage((int) (size.getX() * 1),
                                       0,
                                       (int) size.getX(),
                                       (int) size.getY());

        images[2] = sprite.getSubimage((int) (size.getX() * 2),
                                       0,
                                       (int) size.getX(),
                                       (int) size.getY());
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

    public void follow(Vector2f target, double speed)
    {
        try
        {
            Vector2f polarCoord = new Vector2f(target);
            polarCoord.sub(new Vector2f(pos.getX() + size.getX() / 2, pos.getY() + size.getY() / 2)); // polarize
            polarCoord.div((float) Math.sqrt(Math.pow(polarCoord.getX(), 2) + Math.pow(polarCoord.getY(), 2))); // normalize

            float theta = (float) Math.atan2(polarCoord.getY(), polarCoord.getX()); // get theta
            float x = (float) Math.cos(theta);
            float y = (float) Math.sin(theta);
            float newDistance = (float) Math.sqrt(Math.pow(Math.abs(target.getX() - (pos.getX() + size.getX() / 2)), 2) +
                                                  Math.pow(Math.abs(target.getY() - (pos.getY() + size.getY() / 2)), 2));
            if(newDistance >= 1 || newDistance < 0) // clamp
            {
                pos.add(x * (float) speed, y * (float) speed);
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
//            size.getY() * ((int) (Math.random() * 100) % 3)
            case "north":
                images[0] = sprite.getSubimage((int) size.getX() * 9, 0, (int) size.getX(), (int) size.getY());
                images[1] = sprite.getSubimage((int) size.getX() * 10,
                                               0,
                                               (int) size.getX(),
                                               (int) size.getY());
                images[2] = sprite.getSubimage((int) (size.getX() * 11),
                                               0,
                                               (int) size.getX(),
                                               (int) size.getY());
                break;
            case "south":
                images[0] = sprite.getSubimage(0, 0, (int) size.getX(), (int) size.getY());
                images[1] = sprite.getSubimage((int) size.getX(),
                                               0,
                                               (int) size.getX(),
                                               (int) size.getY());
                images[2] = sprite.getSubimage((int) (size.getX() * 2),
                                               0,
                                               (int) size.getX(),
                                               (int) size.getY());
                break;
            case "west":
                images[0] = sprite.getSubimage((int) size.getX() * 3, 0, (int) size.getX(), (int) size.getY());
                images[1] = sprite.getSubimage((int) size.getX() * 4,
                                               0,
                                               (int) size.getX(),
                                               (int) size.getY());
                images[2] = sprite.getSubimage((int) (size.getX() * 5),
                                               0,
                                               (int) size.getX(),
                                               (int) size.getY());
                break;
            case "east":
                images[0] = sprite.getSubimage((int) size.getX() * 6, 0, (int) size.getX(), (int) size.getY());
                images[1] = sprite.getSubimage((int) size.getX() * 7,
                                               0,
                                               (int) size.getX(),
                                               (int) size.getY());
                images[2] = sprite.getSubimage((int) (size.getX() * 8),
                                               0,
                                               (int) size.getX(),
                                               (int) size.getY());
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
        g.drawImage(images[animationIndex], (int) pos.getX(), (int) pos.getY(), null);
    }
}
