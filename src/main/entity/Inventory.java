package main.entity;

public class Inventory {
    private Item[] items;
    private int firstFree;

    public Inventory(int size){
        items = new Item[size];
        firstFree = 0;
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

//    For Story but not sure if it will be implemented
    public void remove(int index){
        items[index] = null;
        if(index < firstFree){
            firstFree = index;
        }
    }

    public void remove(Item item){
        for(int i = 0; i < items.length; i++){
            if(items[i] == item){
                items[i] = null;
                if(i < firstFree)
                    firstFree = i;
                return;
            }
        }
    }
}
