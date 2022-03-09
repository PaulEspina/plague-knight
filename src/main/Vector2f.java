package main;

import java.util.Vector;

public class Vector2f
{
    private float x;
    private float y;

    public Vector2f()
    {
        x = 0;
        y = 0;
    }

    public Vector2f(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f vec)
    {
        x = vec.x;
        y = vec.y;
    }

    public void add(Vector2f vec)
    {
        add(vec.x, vec.y);
    }

    public void add(float x, float y)
    {
        this.x += x;
        this.y += y;
    }

    public void add(float c)
    {
        this.x += c;
        this.y += c;
    }

    public void sub(Vector2f vec)
    {
        sub(vec.x, vec.y);
    }

    public void sub(float x, float y)
    {
        this.x -= x;
        this.y -= y;
    }

    public void sub(float c)
    {
        this.x -= c;
        this.y -= c;
    }

    public void mul(Vector2f vec)
    {
        mul(vec.x, vec.y);
    }

    public void mul(float x, float y)
    {
        this.x *= x;
        this.y *= y;
    }

    public void mul(float c)
    {
        this.x *= c;
        this.y *= c;
    }

    public void div(Vector2f vec)
    {
        div(vec.x, vec.y);
    }

    public void div(float x, float y)
    {
        if(x == 0 || y == 0)
        {
            throw new ArithmeticException("Cannot divide by 0.");
        }

        this.x /= x;
        this.y /= y;
    }

    public void div(float c)
    {
        if(c == 0)
        {
            throw new ArithmeticException("Cannot divide by 0.");
        }

        this.x /= c;
        this.y /= c;
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }
}
