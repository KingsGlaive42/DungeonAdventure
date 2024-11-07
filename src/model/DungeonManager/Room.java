package model.DungeonManager;

import model.Player.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private final int myX;
    private final int myY;
    private RoomType myRoomType;

    private final Map<DoorDirection, Room> myConnectedRooms = new HashMap<>();
    private final Map<DoorDirection, Door> myDoors = new HashMap<>();

    Room(final int theX, final int theY, final RoomType theRoomType) {
        myX = theX;
        myY = theY;
        myRoomType = theRoomType;
    }

    void connectRoom(final int theAdjX, final int theAdjY, final Room theRoom) {
        DoorDirection direction = getDirection(theAdjX, theAdjY);
        if (direction != null) {
            myConnectedRooms.put(direction, theRoom);
            myDoors.put(direction, new Door(direction));
        }
    }

    private DoorDirection getDirection(final int dx, final int dy) {
        if (dx == 1) return DoorDirection.RIGHT;
        if (dx == -1) return DoorDirection.LEFT;
        if (dy == -1) return DoorDirection.UP;
        if (dy == 1) return DoorDirection.DOWN;
        return null;
    }

    DoorDirection checkPlayerCollisionWithDoor(final Player thePlayer) {
        for (Map.Entry<DoorDirection, Door> entry : myDoors.entrySet()) {
            if (entry.getValue().isPlayerColliding(thePlayer)) {
                return entry.getKey(); // Return the direction of the door
            }
        }
        return null; // No collision
    }

    void addDoor(final DoorDirection theDirection, final Room theConnectedRoom) {
        if (!myDoors.containsKey(theDirection)) {
            myDoors.put(theDirection, new Door(theDirection));
        }
    }

    int getX() {
        return myX;
    }

    int getY() {
        return myY;
    }

    Map<DoorDirection, Room> getConnectedRooms() {
        return myConnectedRooms;
    }

    Map<DoorDirection, Door> getDoors() {
        return myDoors;
    }

    void setType(final RoomType theRoomType) {
        myRoomType = theRoomType;
    }

    RoomType getRoomType() {
        return myRoomType;
    }

    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(myRoomType == RoomType.START ? Color.GREEN :
                myRoomType == RoomType.END ? Color.RED :
                        myRoomType == RoomType.OBJECTIVE ? Color.YELLOW :
                                Color.DARK_GRAY);
        theGraphics2D.fillRect(0, 0, 16 * 32, 12 * 32);

        for (Door door : myDoors.values()) {
            door.draw(theGraphics2D);
        }
    }
}
