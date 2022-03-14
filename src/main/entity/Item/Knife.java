package main.entity.Item;

public class Knife extends Weapons{

    public Knife()
    {
        type = Type.KNIFE;
        damage = 2;
    }

    public void sample(){
        System.out.println(damage);
    }

}
