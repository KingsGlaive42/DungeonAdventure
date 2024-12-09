package model.DungeonManager.Tests;

import model.DungeonManager.*;
import model.PlayerInventory.Item;
import model.PlayerInventory.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DungeonGeneratorTest {

    private DungeonGenerator dungeonGenerator;

    @BeforeEach
    void setUp() {
        dungeonGenerator = new DungeonGenerator(10, 10);
    }

    @Test
    void testGenerateDungeonWithMinimumRooms() {
        dungeonGenerator.generateDungeon(12);
        Map<Point, Room> dungeon = dungeonGenerator.getGeneratedDungeon();

        assertNotNull(dungeon, "Dungeon should not be null after generation.");
        assertFalse(dungeon.isEmpty(), "Dungeon should have rooms after generation.");
        assertTrue(dungeon.size() >= 12, "Dungeon should have at least the specified number of rooms.");
    }

    @Test
    void testAssignSpecialRooms() {
        dungeonGenerator.generateDungeon(15);
        Map<Point, Room> dungeon = dungeonGenerator.getGeneratedDungeon();

        long startRooms = dungeon.values().stream().filter(room -> room.getRoomType() == RoomType.START).count();
        long endRooms = dungeon.values().stream().filter(room -> room.getRoomType() == RoomType.END).count();
        long objectiveRooms = dungeon.values().stream().filter(room -> room.getRoomType() == RoomType.OBJECTIVE).count();

        assertEquals(1, startRooms, "There should be exactly one START room.");
        assertEquals(1, endRooms, "There should be exactly one END room.");
        assertEquals(4, objectiveRooms, "There should be exactly four OBJECTIVE rooms.");
    }

    @Test
    void testDeadEndRooms() {
        dungeonGenerator.generateDungeon(15);
        Map<Point, Room> dungeon = dungeonGenerator.getGeneratedDungeon();

        List<Room> deadEndRooms = dungeon.values().stream()
                .filter(room -> room.getConnectedRooms().size() == 1)
                .toList();

        assertTrue(deadEndRooms.size() >= 6, "Dungeon should have at least 6 dead-end rooms.");
    }

    @Test
    void testRoomItems() {
        dungeonGenerator.generateDungeon(20);
        Map<Point, Room> dungeon = dungeonGenerator.getGeneratedDungeon();

        dungeon.values().stream()
                .filter(room -> room.getRoomType() == RoomType.OBJECTIVE)
                .forEach(room -> {
                    List<Item> items = room.getRoomItems();
                    assertFalse(items.isEmpty(), "OBJECTIVE rooms should have items.");
                    assertEquals(ItemType.PILLAR, items, "OBJECTIVE rooms should contain a pillar item.");
                });

        dungeon.values().stream()
                .filter(room -> room.getRoomType() == RoomType.FILLER)
                .forEach(room -> {
                    boolean containsValidItems = room.getRoomItems().stream()
                            .allMatch(item -> item.getItemType() == ItemType.HEALING_POTION || item.getItemType() == ItemType.VISION_POTION);
                    assertTrue(containsValidItems, "FILLER rooms should only contain healing potions or vision potions.");
                });
    }

    @Test
    void testDungeonStartRoom() {
        dungeonGenerator.generateDungeon(20);
        Map<Point, Room> dungeon = dungeonGenerator.getGeneratedDungeon();

        Room startRoom = dungeon.values().stream()
                .filter(room -> room.getRoomType() == RoomType.START)
                .findFirst()
                .orElse(null);

        assertNotNull(startRoom, "There should be a START room.");
    }

    @Test
    void testGeneratedDungeonIsRectangular() {
        dungeonGenerator = new DungeonGenerator(12, 8);
        dungeonGenerator.generateDungeon(20);
        Map<Point, Room> dungeon = dungeonGenerator.getGeneratedDungeon();

        for (Point point : dungeon.keySet()) {
            assertTrue(point.x >= 0 && point.x < 12, "Room x-coordinate should be within dungeon width.");
            assertTrue(point.y >= 0 && point.y < 8, "Room y-coordinate should be within dungeon height.");
        }
    }

    @Test
    void testInvalidDungeonSizeThrowsException() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> dungeonGenerator.generateDungeon(11),
                "Expected generateDungeon() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Number of rooms must be at least 12."), "Exception message should mention minimum number of rooms.");
    }

    @Test
    void testItemPlacementProbability() {
        dungeonGenerator.generateDungeon(50);
        Map<Point, Room> dungeon = dungeonGenerator.getGeneratedDungeon();

        long pitCount = dungeon.values().stream().filter(Room::getPit).count();
        long healingPotionCount = dungeon.values().stream()
                .flatMap(room -> room.getRoomItems().stream())
                .filter(item -> item.getItemType() == ItemType.HEALING_POTION)
                .count();
        long visionPotionCount = dungeon.values().stream()
                .flatMap(room -> room.getRoomItems().stream())
                .filter(item -> item.getItemType() == ItemType.VISION_POTION)
                .count();

        assertTrue(pitCount > 0, "There should be at least one pit in the dungeon.");
        assertTrue(healingPotionCount > 0, "There should be at least one healing potion in the dungeon.");
        assertTrue(visionPotionCount > 0, "There should be at least one vision potion in the dungeon.");
    }
}
