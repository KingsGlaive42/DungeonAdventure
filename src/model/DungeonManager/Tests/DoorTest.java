package model.DungeonManager.Tests;

import model.DungeonManager.Door;
import model.DungeonManager.DoorDirection;
import model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static org.junit.jupiter.api.Assertions.*;

public class DoorTest {

    private Door upDoor;
    private Door downDoor;
    private Door leftDoor;
    private Door rightDoor;

    @BeforeEach
    public void setUp() {
        upDoor = new Door(DoorDirection.UP);
        downDoor = new Door(DoorDirection.DOWN);
        leftDoor = new Door(DoorDirection.LEFT);
        rightDoor = new Door(DoorDirection.RIGHT);
    }

    @Test
    public void testDoorInstantiationValid() {
        assertNotNull(upDoor, "The UP door should not be null.");
        assertNotNull(downDoor, "The DOWN door should not be null.");
        assertNotNull(leftDoor, "The LEFT door should not be null.");
        assertNotNull(rightDoor, "The RIGHT door should not be null.");
    }

    @Test
    public void testDoorInstantiationNullDirection() {
        assertThrows(IllegalArgumentException.class, () -> new Door(null),
                "Instantiating a door with null direction should throw IllegalArgumentException.");
    }

    @Test
    public void testDrawWithNullGraphics() {
        assertThrows(IllegalArgumentException.class, () -> upDoor.draw(null),
                "Calling draw with null Graphics2D should throw IllegalArgumentException.");
    }

    @Test
    public void testDrawValidGraphics() {
        JPanel panel = new JPanel();
        Graphics graphics = panel.getGraphics();

        try {
            if (graphics != null) {
                upDoor.draw((Graphics2D) graphics);
                downDoor.draw((Graphics2D) graphics);
                leftDoor.draw((Graphics2D) graphics);
                rightDoor.draw((Graphics2D) graphics);
            }
        } catch (Exception e) {
            fail("Drawing the door should not throw an exception: " + e.getMessage());
        }
    }

    @Test
    public void testIsPlayerColliding() {
        Player player = new Player("warrior", "Player1");

        player.setX(223);
        player.setY(-1.0);

        boolean isColliding = upDoor.isPlayerColliding(player);
        assertTrue(isColliding, "The player should be colliding with the UP door.");

        player.setX(512);
        player.setY(512);

        isColliding = downDoor.isPlayerColliding(player);
        assertFalse(isColliding, "The player should not be colliding with the DOWN door.");
    }

    @Test
    public void testIsPlayerCollidingWithNullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> upDoor.isPlayerColliding(null),
                "Calling isPlayerColliding with null Player should throw IllegalArgumentException.");
    }
}
