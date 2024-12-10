package model.DungeonManager.Tests;

import controller.GameController;
import model.DungeonManager.DoorDirection;
import model.DungeonManager.Dungeon;
import model.DungeonManager.Room;
import model.DungeonManager.RoomType;
import model.Player.Player;
import model.PlayerInventory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static model.GameConfig.TILE_SIZE;

class RoomTest {
  private Room room;
  private Room connectedRoom;

  private Player player;

  @BeforeEach
  public void setup() {
      room = new Room(0, 0, RoomType.FILLER);
      connectedRoom = new Room(1, 0, RoomType.FILLER);
      player = new Player("warrior", "TestPlayer", new Inventory(new Dungeon(0, 0, 0)));
  }

    @Test
    public void testConstructorValidInput() {
        assertNotNull(room);
        assertEquals(0, room.getX());
        assertEquals(0, room.getY());
        assertEquals(RoomType.FILLER, room.getRoomType());
    }

    @Test
    public void testConnectRoomValidConnection() {
        room.connectRoom(1, 0, connectedRoom);
        Map<DoorDirection, Room> connectedRooms = room.getConnectedRooms();

        assertEquals(1, connectedRooms.size());
        assertTrue(connectedRooms.containsKey(DoorDirection.RIGHT));
        assertEquals(connectedRoom, connectedRooms.get(DoorDirection.RIGHT));
    }

    @Test
    public void testConnectRoom_InvalidCoordinates() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                room.connectRoom(2, 2, connectedRoom));
        assertEquals("Invalid adjacent coordinates: 2, 2", exception.getMessage());
    }

    @Test
    public void testAddDoorValidDirection() {
        room.addDoor(DoorDirection.UP);
        assertTrue(room.hasDoor(DoorDirection.UP));
    }
    @Test
    public void testAddDoor_NullDirection() {
        // Test adding a null door direction
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                room.addDoor(null));
        assertEquals("DoorDirection cannot be null", exception.getMessage());
    }


    @Test
    public void testPlayerEntersRoomNotVisited() {
        // Add items to the room and simulate player entering
        Item item1 = new Pillar("Polymorphism");
        Item item2 = new HealingPotion();
        room.addItem(item1);
        room.addItem(item2);

        Inventory inventory = new Inventory(null);

        GameController gameController = new GameController(player, null, inventory);
        room.playerEnters(gameController);

        List<Item> playerItems = gameController.getInventory().getItems();
        assertTrue(playerItems.contains(item1));
        assertTrue(playerItems.contains(item2));
        assertTrue(room.getRoomItems().isEmpty(), "Room should have no items after player enters.");
    }

    @Test
    public void testPlayerEntersWithPit() {
        GameController gameController = new GameController(player, null, null);
        room.setPit(true);
        room.playerEnters(gameController);

        assertTrue(room.getPit(), "Player should have encountered a pit in the room.");
    }

    @Test
    public void testAddItemValidItem() {
        Pillar item = new Pillar("Encapsulation");
        room.addItem(item);

        List<Item> roomItems = room.getRoomItems();
        assertEquals(1, roomItems.size());
        assertEquals(item, roomItems);
    }

    @Test
    public void testGetConnectedRooms_NoConnections() {
        Map<DoorDirection, Room> connectedRooms = room.getConnectedRooms();
        assertTrue(connectedRooms.isEmpty(), "New room should have no connected rooms.");
    }

    @Test
    public void testSetAndGetRoomType() {
        room.setType(RoomType.OBJECTIVE);
        assertEquals(RoomType.OBJECTIVE, room.getRoomType());
    }

    @Test
    public void testSetAndGetPit() {
        room.setPit(true);
        assertTrue(room.getPit());

        room.setPit(false);
        assertFalse(room.getPit());
    }

    @Test
    public void testPlayerCollisionWithDoorNoCollision() {
        DoorDirection collisionDirection = room.checkPlayerCollisionWithDoor(player);
        assertNull(collisionDirection, "There should be no door collision in an empty room.");
    }

    @Test
    public void testDrawNoExceptions() {
        BufferedImage image = new BufferedImage(17 * TILE_SIZE, 13 * TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();

        assertDoesNotThrow(() -> room.draw(graphics2D));
    }

    @Test
    public void testAddAndRetrieveItems() {
        // Add multiple items and verify they can be retrieved correctly
        Pillar item1 = new Pillar("Abstraction");
        HealingPotion item2 = new HealingPotion();
        room.addItem(item1);
        room.addItem(item2);

        List<Item> items = room.getRoomItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }
}