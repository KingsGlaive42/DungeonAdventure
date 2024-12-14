package model.DungeonManager;

import controller.GameController;
import model.Player.Player;
import controller.SoundManager;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Dungeon consisting of rooms, with functionality for transitioning between rooms and handling door collisions.
 * The dungeon also manages the current room the player is in and contains methods to find neighboring rooms.
 * The dungeon is generated with a specific width, height, and number of rooms.
 */
public class Dungeon implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //map that stores all rooms in the dungeon are
    private final Map<Point, Room> myRooms;

    // The current room the player is in
    private Room myCurrentRoom;

    // The dungeon generator used for creating rooms in the dungeon
    private final DungeonGenerator generator;

    // Sound manager for sound effects, restored during deserialization
    private transient SoundManager mySoundManager;

    private final int myWidth;

    private final int myHeight;

    /**
     * Constructs a new Dungeon with specified width, height, and number of rooms.
     *
     * @param theWidth The width of the dungeon.
     * @param theHeight The height of the dungeon.
     * @param theNumRooms The number of rooms to be generated in the dungeon.
     * @throws IllegalArgumentException if width, height, or number of rooms are less than or equal to zero.
     */
    public Dungeon(final int theWidth, final int theHeight, final int theNumRooms) {
        if (theWidth <= 0 || theHeight <= 0 || theNumRooms <= 0) {
            throw new IllegalArgumentException("Width, height, and number of rooms must be greater than 0.");
        }
        this.myHeight = theHeight;
        this.myWidth = theWidth;

        generator = new DungeonGenerator(theWidth, theHeight);
        generator.generateDungeon(theNumRooms);
        myRooms = generator.getGeneratedDungeon();
        myCurrentRoom = findStartRoom();
        mySoundManager = SoundManager.getInstance();
        mySoundManager.loadSoundEffect("doorOpening", "src/resources/sounds/doorOpening.wav");
    }

    /**
     * Finds and returns the start room in the generated dungeon.
     *
     * @return The start room.
     * @throws IllegalStateException if no start room is found in the dungeon layout.
     */
    private Room findStartRoom() {
        for (Room room : myRooms.values()) {
            if (room.getRoomType() == RoomType.START) {
                return room;
            }
        }
        throw new IllegalStateException("No start room found in dungeon layout. " + generator.getGeneratedDungeon().toString());
    }

    /**
     * Checks if the player collides with a door in the current room, and if so, transitions the player to the next room.
     * Plays door-opening sound effect when transitioning between rooms.
     *
     * @param thePlayer The player currently in the dungeon.
     * @param theGameController The game controller to manage player-related actions during room transition.
     */
    public void checkDoorCollisions(final Player thePlayer, final GameController theGameController) {
        DoorDirection doorDirection = myCurrentRoom.checkPlayerCollisionWithDoor(thePlayer);

        if (doorDirection != null) {
            mySoundManager.playSoundEffect("doorOpening");
            Room nextRoom = getRoomInDirection(myCurrentRoom, doorDirection);
            if (nextRoom != null) {
                myCurrentRoom = nextRoom; // Transition to the new room
                thePlayer.moveToOppositeDoor(doorDirection);
                myCurrentRoom.playerEnters(theGameController);
            }
        }
    }

    /**
     * Gets the room in the specified direction from the given room.
     *
     * @param theRoom The room to get the adjacent room from.
     * @param theDirection The direction in which to get the next room.
     * @return The room in the specified direction, or null if there is no room.
     * @throws IllegalArgumentException if the room or direction is null.
     */
    private Room getRoomInDirection(final Room theRoom, final DoorDirection theDirection) {
        if (theRoom == null || theDirection == null) {
            throw new IllegalArgumentException("Room and direction must not be null.");
        }

        Point pos = new Point(theRoom.getX(), theRoom.getY());
        switch (theDirection) {
            case UP: pos.translate(0, -1); break;
            case DOWN: pos.translate(0, 1); break;
            case LEFT: pos.translate(-1, 0); break;
            case RIGHT: pos.translate(1, 0); break;
            default: throw new IllegalArgumentException("Invalid DoorDirection: " + theDirection);
        }
        return myRooms.get(pos);
    }

    /**
     * Gets a map of the rooms surrounding the current room.
     *
     * @param theCurrentRoom The current room.
     * @return An unmodifiable map of surrounding rooms.
     */
    public Map<Point, Room> getSurroundingRooms(final Room theCurrentRoom) {
        Map<Point, Room> surroundingRooms = new HashMap<>();
        int x = theCurrentRoom.getX();
        int y = theCurrentRoom.getY();

        // 8 neighboring rooms
        int[][] directions = {
                {x-1, y-1}, {x-1, y}, {x-1, y+1},
                {x, y-1},             {x, y+1},
                {x+1, y-1}, {x+1, y}, {x+1, y+1}
        };

        for (int[] pos : directions) {
            Point point = new Point(pos[0], pos[1]);
            if (myRooms.containsKey(point)) {
                myRooms.get(point).setVisibility(true);
                surroundingRooms.put(point, myRooms.get(point));

            }
        }
        return Collections.unmodifiableMap(surroundingRooms);
    }

    /** @return A map of all rooms in the dungeon. */
    public Map<Point, Room> getMyRooms() {
        return myRooms;
    }

    /**
     * Gets the current room the player is in.
     *
     * @return The current room.
     */
    public Room getMyCurrentRoom() {
        return myCurrentRoom;
    }

    /** @return The height of the dungeon. */
    public int getMyHeight() {
        return myHeight;
    }

    /** @return The width of the dungeon. */
    public int getMyWidth() {
        return myWidth;
    }

    /**
     * Custom deserialization method to restore transient fields.
     *
     * @param in The ObjectInputStream used to read the object.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class cannot be found.
     */
    @Serial
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        //System.out.println("Deserialized Room object.");

        this.mySoundManager = SoundManager.getInstance();
        this.mySoundManager.loadSoundEffect("doorOpening", "src/resources/sounds/doorOpening.wav");
    }
}
