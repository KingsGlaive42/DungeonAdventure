package model.PlayerInventory;

import model.DungeonCharacters.Hero;
import model.DungeonManager.Dungeon;
import model.Player.Player;
import view.UI;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the inventory class that manages the player inventory.
 *
 * @author Aileen Rosas
 */
public class Inventory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Item> myItems;
    private final Map<ItemType, Integer> myItemCounts;

    private static final int MAX_ITEMS = 21;

    /**
     * Inventory Constructor.
     *
     * @param theDungeon the Dungeon.
     */
    public Inventory(Dungeon theDungeon) {
    public Inventory(final Dungeon theDungeon) {
        myItems = new ArrayList<>();
        myItemCounts = new HashMap<>();
    }

    public void addItem(final Item theItem) {
    /**
     * This method adds items to the player's inventory.
     *
     * @param theItem Item to be added.
     */
    public void addItem(Item theItem) {
        if (myItems.size() < MAX_ITEMS ) {
            myItems.add(theItem);
            myItemCounts.put(theItem.getItemType(), myItemCounts.getOrDefault(theItem.getItemType(), 0) + 1);
        }
    }

    /**
     * This method removes items from the player inventory.
     *
     * @param theItem Item to be removed.
     */
    public void removeItem(Item theItem) {
    public void removeItem(final Item theItem) {
        if (myItems.contains(theItem)) {
            myItems.remove(theItem);
            myItemCounts.put(theItem.getItemType(), myItemCounts.get(theItem.getItemType()) - 1);

            if (myItemCounts.get(theItem.getItemType()) <= 0) {
                myItemCounts.remove(theItem.getItemType());
            }
        }
    }

    public void useItem(final Item theItem, final Hero theHero, final Dungeon theDungeon, final UI theUI) {
    /**
     * This method uses an item in the player inventory.
     *
     * @param theItem Item to be used.
     * @param theHero Hero that is using item.
     * @param theDungeon Dungeon.
     */
    public void useItem(Item theItem, Hero theHero, Dungeon theDungeon) {
        if (myItemCounts.getOrDefault(theItem.getItemType(), 0) <= 0) {
           //System.out.println("No " + theItem.getItemType() + " in inventory!");
            //theUI.getGameScreen().showDialogue("No " + theItem.getItemType() + " in inventory!");
           return;
        }
        if (theItem.getItemType() == ItemType.HEALING_POTION) {
            if (theHero.getHitPoints() < theHero.getMaxHitPoints()) {
                theItem.use(theHero, theDungeon, theUI);
                removeItem(theItem);
            } else {
                theItem.use(theHero, theDungeon, theUI);
            }
        } else  {
            theItem.use(theHero, theDungeon, theUI);
            if (theItem.getItemType() != ItemType.PILLAR) {
                removeItem(theItem);
            }
        }
    }

    public int getItemCount(final ItemType theType) {
    /**
     * This method returns the Item Count of an item type.
     *
     * @param theType ItemType.
     * @return number of that ItemType.
     */
    public int getItemCount(ItemType theType) {
        return myItemCounts.getOrDefault(theType, 0);
    }

    /**
     * This method gets all items.
     *
     * @return list of all items.
     */
    public List<Item> getItems() {
        return myItems;
    }

    public Item getItem(final ItemType theType) {

    public Item getItem(ItemType theType) {
        for (Item item : myItems) {
            if (item.getItemType() == theType) {
                return item;
            }
        }
        return null;
    }

    public void displayInventory() {
        System.out.println("Items in Inventory: " + myItems.size());
        System.out.println("Inventory: ");
        for (Item item : myItems) {
            System.out.println("- " + item.getName());
        }
    }

    public boolean hasHealingPotions() {
        return getItemCount(ItemType.HEALING_POTION) >  0;
    }

    /**
     * This method checks if user has all pillars.
     *
     * @return true if has all, false if not.
     */
    public boolean hasAllPillars() {
        int count = 0;
        for (int i = 0; i < myItems.size(); i++) {
            if (myItems.get(i).getItemType() == ItemType.PILLAR) {
                count++;
            }
        }
        return count == 4;
    }

}
