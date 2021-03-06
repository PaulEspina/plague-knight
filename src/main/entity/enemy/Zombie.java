package main.entity.enemy;

import main.Attackable;
import main.Config;
import main.Vector2f;
import main.entity.player.Player;
import main.gfx.AssetManager;
import main.gfx.Sound;

import javax.sound.sampled.AudioInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class Zombie extends Enemy
{
    private String type;
    private String direction;
    private BufferedImage sprite;
    private final BufferedImage[] images;
    private final BufferedImage[] currentImages;
    private int animationIndex;
    private Sound soundFX;
    private boolean visisble;

    public Zombie()
    {
        type = "normal";
        direction = "south";
        sprite = null;
        images = new BufferedImage[12];
        currentImages = new BufferedImage[3];
        animationIndex = 0;
        animationSpeed = 20;
        movementSpeed = 1;
        soundFX = null;
        visisble = false;
    }

    public Zombie(Vector2f pos, Vector2f size)
    {
        this(pos, size, "normal");
    }

    public Zombie(Vector2f pos, Vector2f size, String type)
    {
        this();
        this.pos = pos;
        this.size = size;
        this.type = type;
        int index = 0;
        if(type.equals("normal"))
        {
            index = 0;
        }
        else if(type.equals("fast"))
        {
            index = 1;
        }
        if(type.equals("slow"))
        {
            index = 2;
        }
        sprite = AssetManager.getInstance().getZombie();
        sprite = sprite.getSubimage(0, Config.ZOMBIE_ASSET_HEIGHT * index, Config.ZOMBIE_ASSET_WIDTH * 12, Config.ZOMBIE_ASSET_HEIGHT);
        for(int i = 0; i < 12; i++)
        {
            images[i] = sprite.getSubimage(Config.ZOMBIE_ASSET_WIDTH * i, 0, Config.ZOMBIE_ASSET_WIDTH, Config.ZOMBIE_ASSET_HEIGHT);
        }
        currentImages[0] = images[0];
        currentImages[1] = images[1];
        currentImages[2] = images[2];

        AudioInputStream zombieFX = null;
        switch(type)
        {
            case "normal":
                zombieFX = AssetManager.getInstance().getZombie1FX();
                healthPoints = 50;
                movementSpeed = 0.8f;
                break;
            case "fast":
                zombieFX = AssetManager.getInstance().getZombie1FX(); // zombie 2 fx is terrible yuck
                healthPoints = 20;
                movementSpeed = 1.2f;
                break;
            case "slow":
                zombieFX = AssetManager.getInstance().getZombie3FX();
                healthPoints = 100;
                movementSpeed = 0.4f;
                break;
        }

        soundFX = new Sound(zombieFX);
        soundFX.setSound(-15);
    }

    @Override
    public void attack(Attackable attackable)
    {
        if(attackable instanceof Player)
        {
            attackable.damage(1); // temporary magic number for testing
        }
    }

    @Override
    public void damage(float damage)
    {
        if(healthPoints > 0)
        {
            healthPoints -= damage;
        }
    }

    @Override
    public void update()
    {
        if(!visisble)
        {
            if(pos.getX() >= 0 && pos.getX() <= Config.SCREEN_WIDTH &&
               pos.getY() >= 0 && pos.getY() <= Config.SCREEN_HEIGHT)
            {
                visisble = true;
                soundFX.play();
            }
        }

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

    public void die()
    {
        soundFX.stop();
    }

    public boolean inRange(Player player){
        int playerPosX = (int) player.getPos().getX();           //30
        int playerPosY = (int) player.getPos().getY();           //30
        int playerSizeX = (int) player.getSize().getX();         //30
        int playerSizeY = (int) player.getSize().getY();         //20
        int zombiePosX = (int) getPos().getX();           //50
        int zombiePosY = (int) getPos().getY();           //50
        int zombieSizeX = (int) getSize().getX();         //30
        int zombieSizeY = (int) getSize().getY();         //20
        if(((zombiePosX) >= (playerPosX - playerSizeX / 2)) &&        //LEFT
          ((zombiePosX) <= (playerPosX + playerSizeX / 2)) &&         //RIGHT
          ((zombiePosY) >= (playerPosY - playerSizeY / 2)) &&         //DOWN
          ((zombiePosY) <= (playerPosY + playerSizeY / 2))) {         //UP
          return true;
        }
        return false;
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

    public void animate()
    {
        animationIndex = (animationIndex + 1) % 3;
    }
}
