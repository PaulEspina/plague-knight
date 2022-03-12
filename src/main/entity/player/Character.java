package main.entity.player;

import main.Attackable;
import main.entity.Entity;

public abstract class Character extends Entity implements Attackable
{
    protected int hearts = 5;
}