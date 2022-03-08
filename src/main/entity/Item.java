package main.entity;


public class Item extends Entity{

    public int healthBuff;
    public int health;
    public int damage;
    public int defense;
    int counter = 0;

    public void itemDuration(int seconds, String buffname){
        counter = seconds;
        counter++;

        if(counter == 5 && buffname == "bootsBuff" ){
            //character.remove(Boots)
        }
        if(counter == 5 && buffname == "attackBuff"){
            //character.remove(attackBoost)
        }
        if(counter == 5 && buffname == "defenseBuff"){
            //character.remove(defenseBoost)
        }
    }

    public void Heart(){
        //adds permanent hp
        healthBuff = healthBuff + 1;
    }

    public void Apple(){
        health = health + 1;
    }

    public void Boots(){
        String buff = "bootsBuff";
        movementSpeed = movementSpeed + 5;
        itemDuration(5000,buff);
    }

    public void attackBoost(){
        String buff = "attackBuff";
        damage = damage + 5;
        itemDuration(5000,buff);
    }

    public void DefenseBoost(){
        String buff = "defenseBuff";
        defense = defense + 5;
        itemDuration(5000,buff);
    }

    public void Key(){

    }

    public void Sage(){

    }

    public void Hyssop(){

    }

    public void Chamomile(){

    }

    public void Comfrey(){

    }

    public void Rue(){

    }

}
