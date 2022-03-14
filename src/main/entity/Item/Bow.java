package main.entity.Item;

import static main.entity.Item.Weapon.weaponType.BOW;

public class Bow extends Weapon {

//    animation speed/cooldown speed 1 - high, 3 - low??

    public Bow()
    {
        type = BOW;
        damage = 10;
        animationSpeed = 2;
        cooldownSpeed = 2;
        range = 5;
        knockback = 5;
    }
}
