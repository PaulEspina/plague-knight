package main.entity.Item;

import static main.entity.Item.Weapon.weaponType.SPEAR;

public class Spear extends Weapon {

//    animation speed/cooldown speed 1 - high, 3 - low??

    public Spear()
    {
        type = SPEAR;
        damage = 20;
        animationSpeed = 2;
        cooldownSpeed = 1;
        range = 3;
        knockback = 2;
    }
}
