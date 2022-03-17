package main;

import main.gfx.AssetManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Launcher
{
    public static void main(String[] args)
    {
        System.out.println("Loading assets...");
        try
        {
            AssetManager.getInstance().load();
        }
        catch(IOException | FontFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Failed to load assets.", "Failed to Launch.", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        System.out.println("Assets loaded.");
        Game game = new Game();
        game.start();
    }
}
