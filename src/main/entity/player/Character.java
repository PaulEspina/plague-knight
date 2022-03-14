package main.entity.player;

import main.Attackable;
import main.entity.Entity;

public abstract class Character extends Entity implements Attackable
{
//  Movement speed, Hearts
    protected int hearts = 5;
    protected int currentHearts = 5;

    public boolean checkMaxHeart(){
        if(hearts >= 10){
            return false;
        }
        return true;
    }
    public boolean checkCurrentHearts(){
        if(currentHearts < hearts){
            return true;
        }
        return false;
    }
}