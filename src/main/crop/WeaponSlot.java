package main.crop;

import main.Config;
import main.Game;
import main.entity.Item.Weapon.weaponType;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WeaponSlot {
    private Game game;
    private Point pos;
    private Point size;
    private int weaponAsset;
    private String weaponName;

    private BufferedImage weaponImage;
    private BufferedImage[] weapons;
    private int currentWeapon;

    public WeaponSlot(Game game, Point pos, Point size, String weaponName){
        this.game = game;
        this.pos = pos;
        this.size = size;
        this.weaponName = weaponName;

        weaponImage = AssetManager.getInstance().getItem();
        for(int i = 0; i < 7; i++){
            weapons[i] = weaponImage.getSubimage(i * Config.ITEMS_ASSET_WIDTH, 0, Config.ITEMS_ASSET_WIDTH, Config.ITEMS_ASSET_HEIGHT);
        }
    }

    public void draw(Graphics g){
        g.drawImage(weapons[currentWeapon], (int) getPos().getX(), (int) getPos().getY(), (int) getSize().getX(), (int) getSize().getY(), null);
    }

    public Point getPos() {
        return pos;
    }

    public Point getSize() {
        return size;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeapon(weaponType type)
    {
        switch(type)
        {
            case KNIFE:
                currentWeapon = 0;
            break;
            case AXE:
                currentWeapon = 1;
            break;
            case BOW:
                currentWeapon = 2;
            break;
            case SPEAR:
                currentWeapon = 3;
            break;
            case SWORD:
                currentWeapon = 4;
            break;
            case CROSSBOW:
                currentWeapon = 5;
            break;
        }
    }

    public int getCurrentWeapon()
    {
        return currentWeapon;
    }
}
