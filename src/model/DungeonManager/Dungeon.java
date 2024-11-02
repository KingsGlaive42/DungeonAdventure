package model.DungeonManager;

import model.Player.Player;

import java.awt.Point;
import java.util.Map;

public class Dungeon {
    private final Map<Point, Room> myRooms;
    private Room myCurrentRoom;
    private final DungeonGenerator generator;

    public Dungeon(final int theWidth, final int theHeight, final int theNumRooms) {
        generator = new DungeonGenerator(theWidth, theHeight);
        generator.generateDungeon(theNumRooms);
        myRooms = generator.getGeneratedDungeon();
        myCurrentRoom = findStartRoom();
    }

    private Room findStartRoom() {
        for (Room room : myRooms.values()) {
            if (room.getRoomType() == Room.RoomType.START) {
                return room;
            }
        }
        throw new IllegalStateException("No start room found in dungeon layout. " + generator.getGeneratedDungeon().toString());
    }

    public Room getMyCurrentRoom() {
        return myCurrentRoom;
    }

    public void checkDoorCollisions(final Player thePlayer) {
        String doorDirection = myCurrentRoom.checkPlayerCollisionWithDoor(thePlayer);

        if (doorDirection != null) {
            Room nextRoom = getRoomInDirection(myCurrentRoom, doorDirection);
            if (nextRoom != null) {
                myCurrentRoom = nextRoom; // Transition to the new room
                thePlayer.moveToOppositeDoor(doorDirection);
            }
        }
    }

    public Room getRoomInDirection(final Room theRoom, final String theDirection) {
        Point pos = new Point(theRoom.getX(), theRoom.getY());
        switch (theDirection) {
            case "UP": pos.translate(0, -1); break;
            case "DOWN": pos.translate(0, 1); break;
            case "LEFT": pos.translate(-1, 0); break;
            case "RIGHT": pos.translate(1, 0); break;
        }
        return myRooms.get(pos);
    }
}
