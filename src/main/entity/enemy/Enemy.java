package main.entity.enemy;

import main.Attackable;
import main.entity.Entity;

public abstract class Enemy extends Entity implements Attackable
{
    protected int damage = 1;
    protected int healthPoints = 30;

    public int getHealthPoints(){
        return healthPoints;
    }
}
