package main.entity.Item;

import static main.entity.Item.Weapon.weaponType.CROSSBOW;
import static main.entity.Item.Weapon.weaponType.SWORD;

public class Crossbow extends Weapon {

//    animation speed/cooldown speed 1 - high, 3 - low??

    public Crossbow()
    {
        type = CROSSBOW;
        damage = 30;
        animationSpeed = 1;
        cooldownSpeed = 3;
        range = 5;
        knockback = 3;
    }
}
