package model.PlayerInventory;

public class Item {
    private final String myName;
    private final String myDescription;
    private final ItemType myType;

    // will add/replace with whatever else we decide
    public enum ItemType {
        CONSUMABLE,
        WEAPON,
        KEY_ITEM,
        ARMOR
    }

    public Item(String theName, String theDescription, ItemType theType) {
        this.myName = theName;
        this.myDescription = theDescription;
        this.myType = theType;
    }

    public String getName() {
        return myName;
    }

    public String getMyDescription() {
        return myDescription;
    }

    public ItemType getType() {
        return myType;
    }

    // additional methods for using item/equipping.
}
