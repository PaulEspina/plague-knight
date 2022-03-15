package main.entity.player;

import main.Attackable;
import main.Config;
import main.Drawable;
import main.Vector2f;
import main.entity.Item.Weapon;
import main.entity.enemy.Zombie;
import main.entity.Item.Item.Type;
import main.gfx.AssetManager;
import main.input.KeyManager;
import java.util.Date;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Character implements Drawable{

    private boolean attackAnimate = false;
    private KeyManager keyManager;
    private Vector2f vel;
    private String direction;
    private BufferedImage sprite;
    private final BufferedImage[] images;
    private final BufferedImage[] currentImages;
    private int animationIndex;

    private final BufferedImage[] knifeImages;
    private int attackDirectionIndex;
    public Player()
    {
        sprite = null;
        images = new BufferedImage[12];
        currentImages = new BufferedImage[3];
        knifeImages = new BufferedImage[9];

        movementSpeed = 1;
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
//        No Weapon Player
        for(int i = 0; i < 12; i++)
        {
            images[i] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * i, 0, Config.PLAYER_SPRITE_WIDTH, Config.PLAYER_SPRITE_HEIGHT);
        }
        currentImages[0] = images[0];
        currentImages[1] = images[1];
        currentImages[2] = images[2];

//        Knife Weapon Player
        for(int i = 0; i < 9; i++)
        {
            knifeImages[i] = sprite.getSubimage(Config.PLAYER_SPRITE_WIDTH * i, Config.PLAYER_SPRITE_HEIGHT, Config.PLAYER_SPRITE_WIDTH, Config.PLAYER_SPRITE_HEIGHT);
        }
    }

    @Override
    public void update()
    {
        pos.add(new Vector2f(vel.getX() * movementSpeed, vel.getY() * movementSpeed));
        if(pos.getX() < 0)
        {
            pos.setX(0);
        }
        if(pos.getX() > Config.SCREEN_WIDTH)
        {
            pos.setX(Config.SCREEN_WIDTH);
        }
        if(pos.getY() < 0)
        {
            pos.setY(0);
        }
        if(pos.getY() > Config.SCREEN_HEIGHT)
        {
            pos.setY(Config.SCREEN_HEIGHT);
        }
//        pos.setX(clamp((int)pos.getX(), 10, Config.SCREEN_WIDTH - 46));
//        pos.setY(clamp((int)pos.getY(), 10, Config.SCREEN_HEIGHT - 58));
        checkRotation();
    }

    @Override
    public void draw(Graphics g)
    {
        if(attackAnimate){
            g.drawImage(knifeImages[attackDirectionIndex], (int) pos.getX() - (int) size.getX() / 2,
                    (int) pos.getY() - (int) size.getY() / 2,
                    (int) size.getX(),
                    (int) size.getY(),
                    null);
        }
        else{
            g.drawImage(currentImages[animationIndex], (int) pos.getX() - (int) size.getX() / 2,
                    (int) pos.getY() - (int) size.getY() / 2,
                    (int) size.getX(),
                    (int) size.getY(),
                    null);
        }
    }

    public void animate()
    {
        animationIndex = (animationIndex + 1) % 3;
    }

    @Override
    public void attack(Attackable attackable)
    {
        if(attackable instanceof Zombie)
        {
            attackable.damage(10); // temporary... change when weapon system is online
        }
    }

    @Override
    public void damage(int damage)
    {
        if(currentHearts > 0)
        {
            currentHearts -= damage;
        }
    }


    public boolean inRange(Zombie zombie)
    {
        int playerPosX = (int) getPos().getX();           //30
        int playerPosY = (int) getPos().getY();           //30
        int playerSizeX = (int) getSize().getX();         //30
        int playerSizeY = (int) getSize().getY();         //20
        int zombiePosX = (int) zombie.getPos().getX();    //50
        int zombiePosY = (int) zombie.getPos().getY();    //50
        int zombieSizeX = (int) zombie.getSize().getX();  //30
        int zombieSizeY = (int) zombie.getSize().getY();  //20
        switch(direction)
        {
            case "north":
                if(((playerPosX) >= (zombiePosX - zombieSizeX / 2)) &&        //LEFT
                  ((playerPosX) <= (zombiePosX + zombieSizeX / 2)) &&         //RIGHT
                  ((playerPosY) >= (zombiePosY - zombieSizeY / 2)) &&         //DOWN
                  ((playerPosY) - 30 <= (zombiePosY + zombieSizeY / 2))) {    //UP
                    return true;
                }
                break;
            case "south":
                if(((playerPosX) >= (zombiePosX - zombieSizeX / 2)) &&        //LEFT
                  ((playerPosX) <= (zombiePosX + zombieSizeX / 2)) &&         //RIGHT
                  ((playerPosY) + 30 >= (zombiePosY - zombieSizeY / 2)) &&    //DOWN
                  ((playerPosY) <= (zombiePosY + zombieSizeY / 2))) {         //UP
                    return true;
                }
                break;

            case "east":
                if(((playerPosX) + 30 >= (zombiePosX - zombieSizeX / 2)) &&   //LEFT
                  ((playerPosX) <= (zombiePosX + zombieSizeX / 2)) &&         //RIGHT
                  ((playerPosY) >= (zombiePosY - zombieSizeY / 2)) &&         //DOWN
                  ((playerPosY) <= (zombiePosY + zombieSizeY / 2))) {         //UP
                    return true;
                }
                break;

            case "west":
                if(((playerPosX) >= (zombiePosX - zombieSizeX / 2)) &&        //LEFT
                  ((playerPosX) - 30 <= (zombiePosX + zombieSizeX / 2)) &&    //RIGHT
                  ((playerPosY) >= (zombiePosY - zombieSizeY / 2)) &&         //DOWN
                  ((playerPosY) <= (zombiePosY + zombieSizeY / 2))) {         //UP
                    return true;
                }
                break;
        }
        return false;
    }

    public void pickup(Type type){
        switch (type){
            case HEART:
                if(checkMaxHeart()){
                    hearts += 1;
                    currentHearts += 1;
                }
                System.out.println("HEARTS : " + hearts);
                System.out.println("CURRENT : " + currentHearts);
            break;
            case APPLE:
                if(checkCurrentHearts())
                    currentHearts += 1;
                System.out.println("CURRENT : " + currentHearts);
            break;
            case BOOTS:
//                boolean active = true;
//                long activeTime = new Date().getTime();
//                long endTime = activeTime + 5000;
//
//                update();
//                while(active && activeTime < endTime)
//                {
//
//                }
//                System.out.println("BEFORE : " + movementSpeed);
//                setMovementSpeed(getMovementSpeed() + 100);
//                System.out.println("AFTER : " + movementSpeed);
            break;
            case ATTACK_BOOST:

            break;
            case DEFENSE_BOOST:

            break;
        }
    }

    public void checkRotation()
    {
        switch(direction)
        {
            case "north":
                currentImages[0] = images[9];
                currentImages[1] = images[10];
                currentImages[2] = images[11];
                attackDirectionIndex = 7;
                break;
            case "south":
                currentImages[0] = images[0];
                currentImages[1] = images[1];
                currentImages[2] = images[2];
                attackDirectionIndex = 1;
                break;
            case "west":
                currentImages[0] = images[3];
                currentImages[1] = images[4];
                currentImages[2] = images[5];
                attackDirectionIndex = 3;
                break;
            case "east":
                currentImages[0] = images[6];
                currentImages[1] = images[7];
                currentImages[2] = images[8];
                attackDirectionIndex = 5;
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

    public boolean isAttackAnimate() {
        return attackAnimate;
    }

    public void setAttackAnimate(boolean attackAnimate){
        this.attackAnimate = attackAnimate;
    }
}
