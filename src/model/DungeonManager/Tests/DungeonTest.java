package model.DungeonManager.Tests;

import model.DungeonManager.*;
import model.Player.Player;
import controller.GameController;
import model.PlayerInventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonTest {

    private Dungeon dungeon;
    private Player player;
    private Inventory inventory;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        dungeon = new Dungeon(10, 10, 20);
        inventory = new Inventory(dungeon);
        player = new Player("warrior", "PlayerName", inventory);
        gameController = new GameController(player, dungeon, inventory);
    }

    @Test
    void testDungeonConstructorValidInput() {
        assertNotNull(dungeon, "Dungeon should not be null after instantiation.");
        assertNotNull(dungeon.getMyCurrentRoom(), "Dungeon should have a current room assigned.");
    }

    @Test
    void testDungeonConstructorInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> new Dungeon(-5, 10, 15), "Width must be greater than 0.");
        assertThrows(IllegalArgumentException.class, () -> new Dungeon(10, -10, 15), "Height must be greater than 0.");
        assertThrows(IllegalArgumentException.class, () -> new Dungeon(10, 10, -5), "Number of rooms must be greater than 0.");
    }

    @Test
    void testCurrentRoomInitialization() {
        Room currentRoom = dungeon.getMyCurrentRoom();
        assertNotNull(currentRoom, "Current room should be initialized.");
        assertEquals(RoomType.START, currentRoom.getRoomType(), "Current room should be the START room.");
    }

    @Test
    void testRoomTransitionThroughDoors() {
        Room initialRoom = dungeon.getMyCurrentRoom();
        assertNotNull(initialRoom, "Initial room should not be null.");

        player.setPosition(150, 150);

        DoorDirection direction = initialRoom.getConnectedRooms().keySet().iterator().next();
        if (direction != null) {
            simulatePlayerCollisionWithDoor(player, direction, initialRoom);
            dungeon.checkDoorCollisions(player, gameController);

            Room newRoom = dungeon.getMyCurrentRoom();
            assertNotEquals(initialRoom, newRoom, "Player should have moved to a new room.");
        }
    }

    private void simulatePlayerCollisionWithDoor(Player player, DoorDirection direction, Room room) {
        switch (direction) {
            case UP -> player.setPosition(221, -6);
            case DOWN -> player.setPosition(221, 305);
            case LEFT -> player.setPosition(-27, 135);
            case RIGHT -> player.setPosition(477, 140);
            default -> throw new IllegalArgumentException("Invalid DoorDirection: " + direction);
        }
    }

    @Test
    void testGetSurroundingRooms() {
        Room currentRoom = dungeon.getMyCurrentRoom();
        Map<Point, Room> surroundingRooms = dungeon.getSurroundingRooms(currentRoom);

        assertNotNull(surroundingRooms, "Surrounding rooms map should not be null.");
        assertTrue(surroundingRooms.size() <= 8, "There should be at most 8 surrounding rooms.");
    }
}
