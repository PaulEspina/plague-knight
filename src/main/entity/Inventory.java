package main.entity;

import main.crop.Weapon;

public class Inventory {

    private Weapon[] weapons;
    private Item[] items;
    private int firstFree;

    public Inventory(){
        weapons = new Weapon[6];


    }

    public void add(Item item){
//        if(firstFree == items.length)
//            return false;
//        items[firstFree] = item;
//
//        for(int i = firstFree + 1; i < items.length; i++){
//            if(items[i] == null){
//                firstFree = i;
//                return true;
//            }
//        }
//
//        firstFree = items.length;
//
//        return true;
        for(int i = 0; i < items.length; i++){
            if(items[i] == null){
                items[i] = item;
                break;
            }
        }
    }

    public Item get(int index){
        return items[index];
    }

}
