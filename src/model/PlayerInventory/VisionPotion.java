package model.PlayerInventory;

import model.DungeonCharacters.Hero;
import model.DungeonManager.Dungeon;
import model.DungeonManager.Room;

import java.awt.*;
import java.util.Map;

public class VisionPotion extends Item {
    public VisionPotion() {
        super("Vision Potion", "Reveals nearby rooms.", ItemType.VISION_POTION);
    }

    public void use(final Dungeon theDungeon) {
        Room currRoom = theDungeon.getMyCurrentRoom();
        Map<Point, Room> surroundingRooms = theDungeon.getSurroundingRooms(currRoom);

        System.out.println("Revealing surrounding rooms:");
        for (Map.Entry<Point, Room> entry : surroundingRooms.entrySet()) {
            Room room = entry.getValue();
            System.out.println("Room at " + entry.getKey() + " -> Items: " + room.getRoomItems().size() + " item(s)");
        }
    }
}
