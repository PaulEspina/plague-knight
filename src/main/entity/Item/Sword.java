package main.entity.Item;

import static main.entity.Item.Weapon.weaponType.SWORD;

public class Sword extends Weapon {

//    animation speed/cooldown speed 1 - high, 3 - low??

    public Sword()
    {
        type = SWORD;
        damage = 20;
        animationSpeed = 2;
        cooldownSpeed = 2;
        range = 2;
        knockback = 2;
    }
}
