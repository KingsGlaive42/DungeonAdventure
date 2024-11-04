package model.PlayerInventory;

public class Weapon extends Item {
    private final int myDamage;

    public Weapon(String theName, String theDescription, int theDamage) {
        super(theName, theDescription, ItemType.WEAPON);
        this.myDamage = theDamage;
    }

    public int getDamage() {
        return myDamage;
    }

    @Override public void use() {
        System.out.println("Equipping " + getName() + ": Deals " + myDamage + " damage.");
        // add behavior
    }
}
