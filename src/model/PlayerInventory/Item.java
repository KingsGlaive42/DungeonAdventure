package model.PlayerInventory;

import model.DungeonCharacters.Hero;

import java.awt.image.BufferedImage;

public abstract class Item {
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

    public abstract BufferedImage getImage();
}
