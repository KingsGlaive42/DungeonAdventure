package model.PlayerInventory;

import model.DungeonCharacters.Hero;
import model.DungeonManager.Dungeon;
import view.UI;

import java.awt.image.BufferedImage;
import java.io.Serial;
import java.io.Serializable;

/**
 * Abstract class representing an item in the player's inventory.
 * Each item has a name, description, and type, and can be used in the game world.
 * Concrete subclasses must implement the method to define how the item is used and provide an image representation.
 * @author Aileen
 */
public abstract class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String myName;
    private final String myDescription;
    private final ItemType myType;


    /**
     * Constructs an Item with a name, description, and item type.
     *
     * @param theName        The name of the item.
     * @param theDescription A description of the item, explaining its effects or usage.
     * @param theType        The type of the item (e.g., healing potion, pillar, etc.).
     */
    public Item(String theName, String theDescription, ItemType theType) {
        this.myName = theName;
        this.myDescription = theDescription;
        this.myType = theType;
    }

    /**
     * Uses the item in the game world. This method defines how the item interacts with the hero, dungeon, and UI.
     * Concrete subclasses must provide their own implementation.
     *
     * @param theHero    The hero using the item.
     * @param theDungeon The dungeon where the item is used.
     * @param theUI      The UI to display any relevant messages or effects.
     */
    public abstract void use(final Hero theHero, final Dungeon theDungeon, final UI theUI);

    /**
     * Returns the name of the item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return myName;
    }

    /**
     * Returns the description of the item.
     * This description explains the item's functionality or lore.
     *
     * @return The description of the item.
     */
    public String getMyDescription() {
        return myDescription;
    }

    /**
     * Returns the type of the item.
     * The item type defines the category or class of the item (e.g., Healing Potion, Pillar, etc.).
     *
     * @return The item type.
     */
    public ItemType getItemType() {
        return myType;
    }

    /**
     * Returns the image representation of the item.
     * Concrete subclasses must implement this method to provide the specific image.
     *
     * @return A BufferedImage representing the item.
     */
    public abstract BufferedImage getImage();
}
