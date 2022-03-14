package main.entity.Item;

import main.crop.WeaponSlot;

public abstract class Weapons {
//    Damage, Range, Knockback

//    Add override functions if needed

    protected int damage = 1;
    protected int range = 20;
    protected int knockback = 20;
//Buffered image
    private WeaponSlot Knife;
    pri
    public Weapons(){
        Knife = new WeaponSlot()
    }

}
