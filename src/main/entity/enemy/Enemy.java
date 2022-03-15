package main.entity.enemy;

import main.Attackable;
import main.entity.Entity;

public abstract class Enemy extends Entity implements Attackable
{
    protected int healthPoints = 30;
    protected int damage = 1;

    public int getHealthPoints(){
        return healthPoints;
    }
}
