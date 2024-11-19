package model.PlayerInventory;

import model.DungeonCharacters.Hero;

public class Item {
    private final String myName;
    private final String myDescription;
    private final ItemType myType;


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

    public ItemType getItemType() {
        return myType;
    }
}
