package main.entity;

import main.Config;
import main.Vector2f;
import main.entity.player.Player;
import main.gfx.AssetManager;
import main.gfx.ImageLoader;

import java.awt.*;

import java.awt.image.BufferedImage;

public class Crate extends Entity{

    private BufferedImage asset;
    private final BufferedImage[] images;
    private boolean destroyed;

    private Player player;

    public Crate()
    {
        asset = null;
        images = new BufferedImage[2];
        destroyed = false;
    }

    public Crate(Vector2f pos, Vector2f size){
        this();
        this.pos = pos;
        this.size = size;
        asset = AssetManager.getInstance().getCrate();
        images[0] = asset.getSubimage(0, 0, Config.CRATE_ASSET_WIDTH, Config.CRATE_ASSET_HEIGHT);

        images[1] = asset.getSubimage(Config.CRATE_ASSET_WIDTH, 0, Config.CRATE_ASSET_WIDTH, Config.CRATE_ASSET_HEIGHT);
    };

    public Item destroy()
    {
        destroyed = true;
        int select = (int) (Math.random() * 100 % Config.TOTAL_ITEMS);
        return new Item(new Vector2f(pos.getX(), pos.getY()),
                        new Vector2f(Config.ITEMS_ASSET_WIDTH / 2f, Config.ITEMS_ASSET_HEIGHT / 2f),
                        Item.Type.values()[select]);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void draw(Graphics g)
    {
        g.drawImage(destroyed ? images[1] : images[0],
                    (int) pos.getX() - (int) size.getX() /2, (int) pos.getY() - (int) size.getY() / 2,
                    (int) size.getX(), (int) size.getY(),
                    null);
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }
}
