package main.display;

import main.Config;

import javax.swing.*;
import java.awt.*;

public class Display
{
    private String title;
    private int width, height;

    private JFrame frame;
    private Canvas canvas;

    public Display()
    {
        this.title = Config.TITLE;
        this.width = Config.SCREEN_WIDTH;
        this.height = Config.SCREEN_HEIGHT;

        createDisplay();
    }


    private void createDisplay()
    {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

}
