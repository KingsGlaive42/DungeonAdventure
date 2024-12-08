package model.DungeonManager;

import model.Player.Player;

import java.awt.Point;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Dungeon implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<Point, Room> myRooms;
    private Room myCurrentRoom;
    private final DungeonGenerator generator;

    public Dungeon(final int theWidth, final int theHeight, final int theNumRooms) {
        if (theWidth <= 0 || theHeight <= 0 || theNumRooms <= 0) {
            throw new IllegalArgumentException("Width, height, and number of rooms must be greater than 0.");
        }

        generator = new DungeonGenerator(theWidth, theHeight);
        generator.generateDungeon(theNumRooms);
        myRooms = generator.getGeneratedDungeon();
        myCurrentRoom = findStartRoom();
    }

    private Room findStartRoom() {
        for (Room room : myRooms.values()) {
            if (room.getRoomType() == RoomType.START) {
                return room;
            }
        }
        throw new IllegalStateException("No start room found in dungeon layout. " + generator.getGeneratedDungeon().toString());
    }

    public void checkDoorCollisions(final Player thePlayer) {
        DoorDirection doorDirection = myCurrentRoom.checkPlayerCollisionWithDoor(thePlayer);

        if (doorDirection != null) {
            Room nextRoom = getRoomInDirection(myCurrentRoom, doorDirection);
            if (nextRoom != null) {
                myCurrentRoom = nextRoom; // Transition to the new room
                thePlayer.moveToOppositeDoor(doorDirection);
                myCurrentRoom.playerEnters(thePlayer);
            }
        }
    }

    private Room getRoomInDirection(final Room theRoom, final DoorDirection theDirection) {
        if (theRoom == null || theDirection == null) {
            throw new IllegalArgumentException("Room and direction must not be null.");
        }

        Point pos = new Point(theRoom.getX(), theRoom.getY());
        switch (theDirection) {
            case DoorDirection.UP: pos.translate(0, -1); break;
            case DoorDirection.DOWN: pos.translate(0, 1); break;
            case DoorDirection.LEFT: pos.translate(-1, 0); break;
            case DoorDirection.RIGHT: pos.translate(1, 0); break;
            default: throw new IllegalArgumentException("Invalid DoorDirection: " + theDirection);
        }
        return myRooms.get(pos);
    }

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
                surroundingRooms.put(point, myRooms.get(point));
            }
        }
        return Collections.unmodifiableMap(surroundingRooms);
    }

    public Map<Point, Room> getMyRooms() {
        return myRooms;
    }

    public Room getMyCurrentRoom() {
        return myCurrentRoom;
    }
}
