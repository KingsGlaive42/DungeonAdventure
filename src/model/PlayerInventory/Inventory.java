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
    private final Dungeon myDungeon;

    private static final int MAX_ITEMS = 21;

    public Inventory(Dungeon theDungeon) {
        myItems = new ArrayList<>();
        myItemCounts = new HashMap<>();
        myDungeon = theDungeon;
    }

    public void addItem(Item theItem) {
        if (myItems.size() < MAX_ITEMS ) {
            myItems.add(theItem);
            myItemCounts.put(theItem.getItemType(), myItemCounts.getOrDefault(theItem.getItemType(), 0) + 1);

            System.out.println(theItem.getName() + " added to inventory.");


            displayInventory();

            /*
            // show surrounding rooms when vision potion is added for testing purposes
            if (theItem instanceof VisionPotion visionPotion) {
                visionPotion.use(myDungeon);

                myItemCounts.put(theItem.getItemType(), myItemCounts.get(theItem.getItemType()) - 1);
            }*/
        }
    }

    public void removeItem(Item theItem) {
        //myItems.remove(theItem);
        if (myItems.contains(theItem)) {
            myItems.remove(theItem);
            myItemCounts.put(theItem.getItemType(), myItemCounts.get(theItem.getItemType()) - 1);

            if (myItemCounts.get(theItem.getItemType()) <= 0) {
                myItemCounts.remove(theItem.getItemType());
            }
        }
    }

    public void useItem(Item theItem, Hero theHero) {
        if (myItemCounts.getOrDefault(theItem.getItemType(), 0) <= 0) {
           System.out.println("No " + theItem.getItemType() + " in inventory!");
           return;
        }


        switch (theItem.getItemType()) {
            case HEALING_POTION:
                if (theItem instanceof HealingPotion healingPotion) {
                    healingPotion.use(theHero);
                    removeItem(theItem);
                }
                break;
            case VISION_POTION:
                if (theItem instanceof VisionPotion visionPotion) {
                    visionPotion.use(myDungeon);
                    removeItem(theItem);
                }
                break;
            case PILLAR:
                System.out.println("The pillar of " + theItem.getName() + " cannot be used.");
                break;
            default:
                System.out.println("Item type no recognized.");
                break;
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
