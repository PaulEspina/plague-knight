package main.entity.Item;

import static main.entity.Item.Weapon.weaponType.*;

public class Knife extends Weapon {

//    animation speed/cooldown speed 1 - high, 3 - low??
    public Knife()
    {
        type = KNIFE;
        damage = 10;
        animationSpeed = 1;
        cooldownSpeed = 1;
        range = 2;
        knockback = 5;
    }
}
