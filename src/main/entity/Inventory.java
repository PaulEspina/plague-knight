package main.entity;

public class Inventory {
    private Item[] items;

    public Inventory(int size){
        items = new Item[size];
    }

    public void add(Item item){

    }

    public Item get(int index){
        return items[index];
    }
}
