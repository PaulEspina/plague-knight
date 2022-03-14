package main.entity.Item;

public abstract class Weapon
{
//    Damage, Range, Knockback

//    Add override functions if needed

    public enum weaponType
    {
        KNIFE(0),
        AXE(1),
        BOW(2),
        SPEAR(3),
        SWORD(4),
        CROSSBOW(5);

        private final int value;

        weaponType(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }

    protected weaponType type;
    protected int damage = 0;
    protected int animationSpeed = 0;
    protected int cooldownSpeed = 0;
    protected int range = 0;
    protected int knockback = 0;


    public weaponType getType()
    {
        return type;
    }
}
