package model.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class DungeonItems {
    public static List<Item> generateItems() {
        List<Item> dungeonItems = new ArrayList<>();

        // replace with whatever items we decide on

        // consumable potions
        dungeonItems.add(new Consumable("Health Potion", "Restores 50 HP", 50));

        // food
        dungeonItems.add(new Consumable("Bread", "Restores a small amount of HP", 15));

        // weapons
        dungeonItems.add(new Weapon("Weak Sword", "Sword that does minimal damage.", 15));

        return dungeonItems;
    }
}
