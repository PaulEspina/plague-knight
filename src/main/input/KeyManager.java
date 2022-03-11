package main.input;

import main.states.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KeyManager implements KeyListener
{
    private boolean[] keyDown;
    private boolean[] keyUp;

    public KeyManager()
    {
        keyDown = new boolean[256];
        keyUp = new boolean[256];


    }

    public void tick()
    {
        Arrays.fill(keyDown, false);
        Arrays.fill(keyUp, false);
    }

    public boolean isKeyDown(int key)
    {
        return keyDown[key];
    }

    public boolean isKeyUp(int key)
    {
        return keyUp[key];
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        keyDown[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keyUp[e.getKeyCode()] = true;
    }
}
