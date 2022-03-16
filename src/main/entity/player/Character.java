package main.entity.player;

import main.Attackable;
import main.entity.Entity;

public abstract class Character extends Entity implements Attackable
{
//  Movement speed, Hearts
    protected int hearts = 5;
    protected int currentHearts = 5;
    protected int damage = 1;

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

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public int getCurrentHearts() {
        return currentHearts;
    }

    public void setCurrentHearts(int currentHearts) {
        if(currentHearts > hearts)
        {
            this.currentHearts = hearts;
        }
    }

    public void increaseHearts(int value)
    {
        hearts += value;
    }

    public void increaseCurrentHearts(int value)
    {
        if(currentHearts + value <= hearts)
        {
            currentHearts += value;
        }
    }

    public void reduceHearts(int value)
    {
        if(hearts - value > 0)
        {
            hearts -= value;
            if(currentHearts > hearts)
            {
                currentHearts = hearts;
            }
        }
    }
}
