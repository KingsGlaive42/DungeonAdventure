package model.DungeonManager;

import model.Player.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Room {
    public enum RoomType { START, END, OBJECTIVE, FILLER }
    private final int myX;
    private final int myY;
    private RoomType myRoomType;

    private final Map<String, Room> myConnectedRooms = new HashMap<>();
    private final Map<String, Door> myDoors = new HashMap<>();

    public Room(final int theX, final int theY, final RoomType theRoomType) {
        myX = theX;
        myY = theY;
        myRoomType = theRoomType;
    }

    public void connectRoom(final int theDX, final int theDY, final Room theRoom) {
        String direction = getDirection(theDX, theDY);
        if (direction != null) {
            myConnectedRooms.put(direction, theRoom);
            myDoors.put(direction, new Door(direction));
        }
    }

    public String checkPlayerCollisionWithDoor(final Player thePlayer) {
        for (Map.Entry<String, Door> entry : myDoors.entrySet()) {
            if (entry.getValue().isPlayerColliding(thePlayer)) {
                return entry.getKey(); // Return the direction of the door
            }
        }
        return null; // No collision
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

    public void addDoor(final String theDirection, final Room theConnectedRoom) {
        if (!myDoors.containsKey(theDirection)) {
            myDoors.put(theDirection, new Door(theDirection));
        }
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    public Map<String, Room> getConnectedRooms() {
        return myConnectedRooms;
    }

    public Map<String, Door> getDoors() {
        return myDoors;
    }

    public void setType(final RoomType theRoomType) {
        myRoomType = theRoomType;
    }

    public RoomType getRoomType() {
        return myRoomType;
    }

    private String getDirection(final int dx, final int dy) {
        if (dx == 1) return "RIGHT";
        if (dx == -1) return "LEFT";
        if (dy == 1) return "UP";
        if (dy == -1) return "DOWN";
        return null;
    }
}
