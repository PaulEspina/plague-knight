package main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KeyManager implements KeyListener
{
    private boolean[] keys;

    public KeyManager()
    {
        keys = new boolean[256];
    }

    public void tick()
    {
        Arrays.fill(keys, false);
    }

    public boolean getKeyState(int key)
    {
        return keys[key];
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = true;
    }
}
