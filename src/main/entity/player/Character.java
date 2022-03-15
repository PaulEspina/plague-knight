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
        this.currentHearts = currentHearts;
    }
}