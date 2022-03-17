package main.entity.enemy;

import main.Attackable;
import main.Config;
import main.Vector2f;
import main.entity.player.Player;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Boss extends Enemy{
    private boolean attackAnimate = false;
    private boolean deadAnimate = false;
    private String direction;
    private BufferedImage sprite;
    private final BufferedImage[] images;
    private final BufferedImage[] currentImages;
    private int animationIndex;

    private final BufferedImage[] attackImages;
    private int attackDirectionIndex;

    private final BufferedImage[] deadImages;
    private int deadDirectionIndex;

    public Boss() {
        direction = "south";
        sprite = null;
        images = new BufferedImage[20];
        currentImages = new BufferedImage[4];

        attackImages = new BufferedImage[5];
        deadImages = new BufferedImage[5];
        animationIndex = 0;
        attackDirectionIndex = 0;
        deadDirectionIndex = 0;
        animationSpeed = 20;
        movementSpeed = 1;
    }

    public Boss(Vector2f pos, Vector2f size)
    {
        this();
        this.pos = pos;
        this.size = size;

        sprite = AssetManager.getInstance().getBoss1();
//        sprite = sprite.getSubimage(0, 0, Config.BOSS_1_ASSET_WIDTH * 12, Config.BOSS_1_ASSET_HEIGHT);

        for(int i = 0; i < 20; i++){
            images[i] = sprite.getSubimage(Config.BOSS_1_ASSET_WIDTH * i, 0, Config.BOSS_1_ASSET_WIDTH, Config.BOSS_1_ASSET_HEIGHT);
        }

        currentImages[0] = images[0];
        currentImages[1] = images[1];
        currentImages[2] = images[2];

        attackImages[0] = images[12];
        attackImages[1] = images[13];
        attackImages[2] = images[14];
        attackImages[3] = images[15];

        deadImages[0] = images[16];
        deadImages[1] = images[17];
        deadImages[2] = images[18];
        deadImages[3] = images[19];

        healthPoints = 10;
        movementSpeed = 0.8f;

    }


    @Override
    public void attack(Attackable attackable) {
        if(attackable instanceof Player)
        {
            attackable.damage(2); // temporary magic number for testing
        }
    }

    @Override
    public void damage(float damage) {
        if(healthPoints > 0)
        {
            healthPoints -= damage;
        }
    }

    @Override
    public void update() {
        checkRotation();
    }

    @Override
    public void draw(Graphics g) {
        if(deadAnimate){
            g.drawImage(deadImages[deadDirectionIndex], (int) pos.getX() - (int) size.getX() / 2,
                    (int) pos.getY() - (int) size.getY() / 2,
                    (int) size.getX(),
                    (int) size.getY(),
                    null);
        }
        else{
            if(attackAnimate){
                g.drawImage(attackImages[attackDirectionIndex], (int) pos.getX() - (int) size.getX() / 2,
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
    }

    public boolean inRange(Player player){
        int playerPosX = (int) player.getPos().getX();           //30
        int playerPosY = (int) player.getPos().getY();           //30
        int playerSizeX = (int) player.getSize().getX();         //30
        int playerSizeY = (int) player.getSize().getY();         //20
        int bossPosX = (int) getPos().getX();           //50
        int bossPosY = (int) getPos().getY();           //50
        int bossSizeX = (int) getSize().getX();         //30
        int bosssSizeY = (int) getSize().getY();         //20
        if(((bossPosX) >= (playerPosX - playerSizeX / 2) + 10) &&        //LEFT
                ((bossPosX) <= (playerPosX + playerSizeX / 2) + 10) &&         //RIGHT
                ((bossPosY) >= (playerPosY - playerSizeY / 2) + 10) &&         //DOWN
                ((bossPosY) <= (playerPosY + playerSizeY / 2) + 10)) {         //UP
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
                attackDirectionIndex = 3;
                deadDirectionIndex = 3;
                break;
            case "south":
                currentImages[0] = images[0];
                currentImages[1] = images[1];
                currentImages[2] = images[2];
                attackDirectionIndex = 0;
                deadDirectionIndex = 0;
                break;
            case "west":
                currentImages[0] = images[3];
                currentImages[1] = images[4];
                currentImages[2] = images[5];
                attackDirectionIndex = 1;
                deadDirectionIndex = 1;
                break;
            case "east":
                currentImages[0] = images[6];
                currentImages[1] = images[7];
                currentImages[2] = images[8];
                attackDirectionIndex = 2;
                deadDirectionIndex = 2;
                break;
        }
    }

    public boolean isAttackAnimate() {
        return attackAnimate;
    }

    public void setAttackAnimate(boolean attackAnimate){
        this.attackAnimate = attackAnimate;
    }

    public boolean isDeadAnimate() {
        return deadAnimate;
    }

    public void setDeadAnimate(boolean deadAnimate) {
        this.deadAnimate = deadAnimate;
    }

    public void animate()
    {
        animationIndex = (animationIndex + 1) % 3;
    }


}
