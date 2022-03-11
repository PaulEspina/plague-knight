package main;

import main.gfx.AssetManager;

public class Launcher
{
    public static void main(String[] args)
    {
        System.out.println("Loading assets...");
        if(AssetManager.getInstance().load())
        {
            System.out.println("Assets loaded.");
            Game game = new Game();
            game.start();
        }
    }
}
