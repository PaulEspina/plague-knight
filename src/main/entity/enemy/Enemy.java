package main.entity.enemy;

import main.Attackable;
import main.entity.Entity;

public abstract class Enemy extends Entity implements Attackable
{
    protected int zombieHealth = 30;
    protected int fastZombieHealth = 20;
    protected int slowzombieHealth = 40;
    protected int damage = 1;
}
