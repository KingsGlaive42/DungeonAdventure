package model.PlayerInventory;

import model.DungeonCharacters.Hero;

public abstract class Item {
    private final String myName;
    private final String myDescription;


    public Item(String theName, String theDescription) {
        this.myName = theName;
        this.myDescription = theDescription;
    }

    public String getName() {
        return myName;
    }

    public String getMyDescription() {
        return myDescription;
    }

    public abstract void use(Hero theHero);
}
