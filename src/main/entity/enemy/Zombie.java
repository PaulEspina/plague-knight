package main.entity.enemy;

import main.Config;
import main.Vector2f;

import java.awt.*;

public class Zombie extends Enemy
{
    public Zombie()
    {
    }

    public Zombie(Vector2f pos, Vector2f size)
    {
        this.pos = pos;
        this.size = size;
    }

    @Override
    public void attack(int damage)
    {

    }

    @Override
    public void update()
    {

    }

    public void follow(Vector2f target, int speed)
    {
        try
        {
            Vector2f polarCoord = new Vector2f(target);
            polarCoord.sub(new Vector2f(pos.getX() + size.getX() / 2, pos.getY() + size.getY() / 2)); // polarize
            polarCoord.div((float) Math.sqrt(Math.pow(polarCoord.getX(), 2) + Math.pow(polarCoord.getY(), 2))); // normalize

            float direction = (float) Math.atan2(polarCoord.getY(), polarCoord.getX()); // get theta
            float x = (float) Math.cos(direction);
            float y = (float) Math.sin(direction);
            float newDistance = (float) Math.sqrt(Math.pow(Math.abs(target.getX() - (pos.getX() + size.getX() / 2)), 2) + Math.pow(Math.abs(target.getY() - (pos.getY() + size.getY() / 2)), 2));
            if(newDistance >= 1 || newDistance < 0) // clamp
            {
                pos.add(x * speed, y * speed);
            }
        }
        catch(ArithmeticException e)
        {
            //chow
        }
    }

    @Override
    public void draw(Graphics g)
    {
        g.drawRect((int) pos.getX(), (int) pos.getY(), (int) size.getX(), (int) size.getY());
    }
}
