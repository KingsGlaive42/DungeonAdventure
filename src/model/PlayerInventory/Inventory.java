package model.PlayerInventory;

import model.DungeonCharacters.Hero;
import model.DungeonManager.Dungeon;
import model.Player.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Inventory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Item> myItems;
    private final Map<ItemType, Integer> myItemCounts;

    private static final int MAX_ITEMS = 21;

    public Inventory(Dungeon theDungeon) {
        myItems = new ArrayList<>();
        myItemCounts = new HashMap<>();
    }

    public void addItem(Item theItem) {
        if (myItems.size() < MAX_ITEMS ) {
            myItems.add(theItem);
            myItemCounts.put(theItem.getItemType(), myItemCounts.getOrDefault(theItem.getItemType(), 0) + 1);
        }
    }

    public void removeItem(Item theItem) {
        if (myItems.contains(theItem)) {
            myItems.remove(theItem);
            myItemCounts.put(theItem.getItemType(), myItemCounts.get(theItem.getItemType()) - 1);

            if (myItemCounts.get(theItem.getItemType()) <= 0) {
                myItemCounts.remove(theItem.getItemType());
            }
        }
    }

    public void useItem(Item theItem, Hero theHero, Dungeon theDungeon) {
        if (myItemCounts.getOrDefault(theItem.getItemType(), 0) <= 0) {
           System.out.println("No " + theItem.getItemType() + " in inventory!");
           return;
        }
        if (theItem.getItemType() == ItemType.HEALING_POTION) {
            if (theHero.getHitPoints() < theHero.getMaxHitPoints()) {
                theItem.use(theHero, theDungeon);
                removeItem(theItem);
            }
        } else  {
            theItem.use(theHero, theDungeon);
            if (theItem.getItemType() != ItemType.PILLAR) {
                removeItem(theItem);
            }
        }
    }

    public int getItemCount(ItemType theType) {
        return myItemCounts.getOrDefault(theType, 0);
    }

    public List<Item> getItems() {
        return myItems;
    }

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

    public boolean hasAllPillars() {
        int count = 0;
        for (int i = 0; i < myItems.size(); i++ ) {
            if (myItems.get(i).getItemType() == ItemType.PILLAR) {
                count++;
            }
        }

        return count == 4;
    }

}
