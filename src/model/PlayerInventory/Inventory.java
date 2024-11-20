package model.PlayerInventory;

import model.DungeonCharacters.Hero;
import model.DungeonManager.Dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static model.PlayerInventory.ItemType.*;

public class Inventory {
    private final List<Item> myItems;
    private final Map<ItemType, Integer> myItemCounts;
    private final Dungeon myDungeon;

    public Inventory(Dungeon theDungeon) {
        myItems = new ArrayList<>();
        myItemCounts = new HashMap<>();
        myDungeon = theDungeon;
    }

    public void addItem(Item theItem) {
        myItems.add(theItem);
        myItemCounts.put(theItem.getItemType(), myItemCounts.getOrDefault(theItem.getItemType(), 0) + 1);

        System.out.println(theItem.getName() + " added to inventory.");

        displayInventory();

        // show surrounding rooms when vision potion is added for testing purposes
        if (theItem instanceof VisionPotion visionPotion) {
            visionPotion.use(myDungeon);

            myItemCounts.put(theItem.getItemType(), myItemCounts.get(theItem.getItemType()) - 1);
        }
    }

    public void removeItem(ItemType theType) {
        //myItems.remove(theItem);
        if (myItemCounts.getOrDefault(theType,0) > 0) {
            myItemCounts.put(theType, myItemCounts.get(theType) - 1);
            System.out.println(theType + " use/removed item");
        } else {
            System.out.println("No " + theType + "'s in inventory.");
        }
    }

    public void useItem(ItemType theType, Hero theHero) {
        if (myItemCounts.getOrDefault(theType, 0) <= 0) {
           System.out.println("No " + theType + " in inventory!");
           return;
        }

        Item item = getItem(theType);
        if (item != null) {
            switch (theType) {
                case HEALING_POTION:
                    if (item instanceof HealingPotion healingPotion) {
                        healingPotion.use(theHero);
                    }
                    break;
                case VISION_POTION:
                    if (item instanceof VisionPotion visionPotion) {
                        visionPotion.use(myDungeon);
                    }
                    break;
                case PILLAR:
                    System.out.println("The pillar of " + item.getName() + " cannot be used.");
                    break;
                default:
                    System.out.println("Item type no recognized.");
                    break;
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

    // additional methods for using items/organizing.
}
