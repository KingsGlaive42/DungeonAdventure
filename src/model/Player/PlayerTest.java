package model.Player;

import controller.InputListener;
import model.DungeonManager.DoorDirection;
import model.DungeonManager.Dungeon;
import model.PlayerInventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.SoundManager;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private InputListener myInputListener;
    private SoundManager mySoundManager;
    private Dungeon myDungeon;
    private Inventory myInventory;
    private Player myPlayer;

    @BeforeEach
    public void setup() {
        myInputListener = InputListener.getInstance();
        mySoundManager = SoundManager.getInstance();
        myDungeon = new Dungeon(20, 20, 20);
        myInventory = new Inventory(myDungeon);

        myPlayer = new Player("Warrior", "TestPlayer", myInventory);
    }

    @Test
    public void testConstructorValidInput() {
        assertNotNull(myPlayer);
        assertEquals("TestPlayer", myPlayer.getName());
    }

    @Test
    public void testConstructorInvalidCharacterClass() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Player("invalidClass", "TestPlayer", myInventory));
        assertEquals("Invalid character class: invalidClass", exception.getMessage());
    }

    @Test
    public void testConstructorNullName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Player("warrior", null, myInventory));
        assertEquals("Player name cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testHeroClassWarrior() {
        assertTrue(myPlayer.getName().contains("TestPlayer"));
        assertSame(myPlayer.getMyInventory(), myInventory);
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
    public void testInventoryManipulation() {
        assertEquals(myInventory, myPlayer.getMyInventory(), "Player inventory should match the given inventory.");

        Inventory newInventory = new Inventory(myDungeon);
        myPlayer.setInventory(newInventory);

        assertEquals(newInventory, myPlayer.getMyInventory(), "Player inventory should be updated to the new inventory.");
    }

    @Test
    public void testSetDefaultValues() {
        assertEquals(6.5 * myPlayer.getTileSize(), myPlayer.getX(), "Default X position is incorrect.");
        assertEquals(4 * myPlayer.getTileSize(), myPlayer.getY(), "Default Y position is incorrect.");
        assertEquals(6, myPlayer.getSpeed(), "Default speed is incorrect.");
    }
}