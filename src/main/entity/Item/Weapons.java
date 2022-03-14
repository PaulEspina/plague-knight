package main.entity.Item;

import main.crop.WeaponSlot;

import java.awt.image.BufferedImage;

public abstract class Weapons
{
//    Damage, Range, Knockback

//    Add override functions if needed

    public enum Type
    {
        KNIFE(0);

        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }

    protected Type type;
    protected int damage = 1;
    protected int range = 20;
    protected int knockback = 20;

    public Type getType()
    {
        return type;
    }
}
