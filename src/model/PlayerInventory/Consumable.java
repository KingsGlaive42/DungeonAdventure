package model.PlayerInventory;

public class Consumable extends Item {
    private int myEffect;

    public Consumable(String theName, String theDescription, int theEffect) {
        super(theName, theDescription, ItemType.CONSUMABLE);
        this.myEffect = theEffect;
    }

    public int getEffect() {
        return myEffect;
    }

    @Override
    public void use() {
        System.out.println("Using " + getName() + ": Restores " + myEffect);
        // add logic
    }
}
