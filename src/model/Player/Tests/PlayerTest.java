package model.Player.Tests;

import controller.InputListener;
import model.DungeonManager.DoorDirection;
import model.DungeonManager.Dungeon;
import model.Player.Player;
import model.PlayerInventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private InputListener myInputListener;
    private Player myPlayer;

    @BeforeEach
    public void setup() {
        myInputListener = InputListener.getInstance();
        Dungeon dungeon = new Dungeon(10, 10, 20);
        Inventory inventory = new Inventory(dungeon);
        myPlayer = new Player("Warrior", "TestPlayer");
    }

    @Test
    public void testConstructorValidInput() {
        assertNotNull(myPlayer);
        assertEquals("TestPlayer", myPlayer.getName());
    }

    @Test
    public void testConstructorInvalidCharacterClass() {
        Dungeon dungeon = new Dungeon(10, 10, 20);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Player("invalidClass", "TestPlayer"));
        assertEquals("Invalid character class: invalidClass", exception.getMessage());
    }

    @Test
    public void testConstructorNullName() {
        Dungeon dungeon = new Dungeon(10, 10, 20);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Player("warrior", null));
        assertEquals("Player name cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testHeroClassWarrior() {
        assertTrue(myPlayer.getName().contains("TestPlayer"));
    }

    @Test
    public void testMovementUpdatePosition() {
        myInputListener.setLeftPressed(true);

        myPlayer.update();

        assertTrue(myPlayer.getX() < 6.5 * myPlayer.getTileSize(), "Player should have moved left.");

        myInputListener.setLeftPressed(false);
    }

    @Test
    public void testBoundsCheck() {
        for (int i = 0; i < 100; i++) {
            myInputListener.setLeftPressed(true);
            myPlayer.update();
        }

        assertEquals(-32, myPlayer.getX(), "Player should be at the left boundary.");

        myInputListener.setLeftPressed(false);
    }

    @Test
    public void testMoveToOppositeDoor_Down() {
        myPlayer.moveToOppositeDoor(DoorDirection.DOWN);
        assertEquals(0, myPlayer.getY(), "Player should have moved to the opposite door at Y = 0.");
    }

    @Test
    public void testSetDefaultValues() {
        assertEquals(6.5 * myPlayer.getTileSize(), myPlayer.getX(), "Default X position is incorrect.");
        assertEquals(4 * myPlayer.getTileSize(), myPlayer.getY(), "Default Y position is incorrect.");
        assertEquals(6, myPlayer.getSpeed(), "Default speed is incorrect.");
    }
}