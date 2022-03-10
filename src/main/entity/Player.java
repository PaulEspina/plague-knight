package main.entity;

import main.Game;
import main.gfx.ImageLoader;
import main.states.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player{

    private Game game;
    public static int xAxis = 0, yAxis = 0;
    private int velX, velY, speed;
    private Point pos;
    private Point size;
    private double movement;
    public static int counter = 0;

    //for images
    private BufferedImage character;
    private BufferedImage newCharacter;

    public static final String path = "/main/entity/playermovements.png";
    private Point imagePos;

    public BufferedImage getNewCharacter() { return newCharacter; }
    public Point getImagePos() { return imagePos; }
    public void setImagePos(Point imagePos) { this.imagePos = imagePos; }
    public Point getPos(){ return pos; }
    public void setPos(Point pos) { this.pos = pos; }
    public Point getSize() { return size; }
    public void setSize(Point size) { this.size = size; }

    public void setVelX(int velX)
    {
        this.velX = velX;
    }
    public void setVelY(int velY)
    {
        this.velY = velY;
    }
    public int getVelX()
    {
        return velX;
    }
    public int getVelY()
    {
        return velY;
    }

    public Player(Point pos, Point size, Game game)
    {
        this.game = game;
        this.pos = pos;
        this.size = size;
        velX = 0;
        velY = 0;
    }

    public void multiply(double speed)
    {
        velX *= speed;
        velY *= speed;
    }

    private void move()
    {
        setPos(new Point(getVelX(), getVelY()));

        if(game.getKeyManager().getKeyState(KeyEvent.VK_W))
        {
            counter++;
            System.out.println(counter);
            loadTexture(new Point(56,145), new Point(47, 47), path);
            if(counter % 2 == 0) //left
            {
                loadTexture(new Point(7,145), new Point(47, 47), path);
            }
            else //right
            {
                loadTexture(new Point(97,145), new Point(47, 47), path);
            }
            velY-=5;
        }

        if(game.getKeyManager().getKeyState(KeyEvent.VK_S))
        {
            counter++;
            loadTexture(new Point(56,2), new Point(47, 47), path);

            if(counter % 2 == 0)
            {
                loadTexture(new Point(7,2), new Point(47, 47), path);
            }
            else
            {
                loadTexture(new Point(97,2), new Point(47, 47), path);
            }
            velY+=5;
        }

        if(game.getKeyManager().getKeyState(KeyEvent.VK_D))
        {
            counter++;
            loadTexture(new Point(56,98), new Point(47, 47), path);
            if(counter % 2 == 0)
            {
                loadTexture(new Point(7,99), new Point(47, 47), path);
            }
            else
            {
                loadTexture(new Point(97,99), new Point(47, 47), path);
            }
            velX+=5;
        }

        if(game.getKeyManager().getKeyState(KeyEvent.VK_A))
        {
            counter++;
            loadTexture(new Point(56,50), new Point(47, 47), path);
            if(counter % 2 == 0)
            {
                loadTexture(new Point(7,51), new Point(47, 47), path);
            }
            else
            {
                loadTexture(new Point(97,51), new Point(47, 47), path);
            }
            velX-=5;
        }
    }

    public void tick()
    {
        move();
    }

    public void render(Graphics g)
    {

    }

    public void loadTexture(Point imagePos, Point imageSize, String path)
    {
        this.imagePos = imagePos;
        character = ImageLoader.loadImage(path);
        newCharacter = character.getSubimage((int)imagePos.getX(), (int)imagePos.getY(), (int)imageSize.getX(), (int)imageSize.getY());
    }

    public void setFrame(Point imagePos, Point imageSize)
    {
        this.imagePos = imagePos;
        newCharacter = character.getSubimage((int)imagePos.getX(), (int)imagePos.getY(), (int)imageSize.getX(), (int)imageSize.getY());
    }
}
