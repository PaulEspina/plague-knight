package main.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

public class MouseManager implements MouseListener, MouseMotionListener
{
    private boolean mouse[];
    private int mouseX, mouseY;

    public MouseManager()
    {
        mouse = new boolean[2];
    }

    public void tick()
    {
        Arrays.fill(mouse, false);
    }

    public boolean getMouseButtonState(int mbutton)
    {
        return mouse[mbutton];
    }

    public int getMouseX()
    {
        return mouseX;
    }

    public int getMouseY()
    {
        return mouseY;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            mouse[MouseEvent.BUTTON1] = true;
        }

        if(e.getButton() == MouseEvent.BUTTON2)
        {
            mouse[MouseEvent.BUTTON2] = true;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();

    }

}
