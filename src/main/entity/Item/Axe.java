package main.entity.Item;

import static main.entity.Item.Weapon.weaponType.*;

public class Axe extends Weapon {

//    animation speed/cooldown speed 1 - high, 3 - low??

    public Axe()
    {
        type = AXE;
        damage = 30;
        animationSpeed = 3;
        cooldownSpeed = 2;
        range = 2;
        knockback = 1;
    }
}

