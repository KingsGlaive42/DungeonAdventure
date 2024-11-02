package model.DungeonManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DungeonGenerator {
    private static final int ROOM_WIDTH = 16;
    private static final int ROOM_HEIGHT = 12;
    private static final int MIN_DEAD_ENDS = 6;
    private final int myDungeonWidth;
    private final int myDungeonHeight;
    private Room[][] myDungeonGrid;
    private ArrayList<Room> myRooms;
    private ArrayList<Point> myAvailableRoomLocations;
    private final Random random;
    private Room myStartRoom;
    private Room myEndRoom;
    private ArrayList<Room> myObjectiveRooms;

    public DungeonGenerator(final int theDungeonWidth, final int theDungeonHeight) {
        myDungeonWidth = theDungeonWidth;
        myDungeonHeight = theDungeonHeight;
        random = new Random();
    }

    public void generateDungeon(final int theNumRooms) {
        do {
            initializeDungeon();
            createRooms(theNumRooms);
        } while (!assignSpecialRooms());

        addDoors();
    }

    private void initializeDungeon() {
        myDungeonGrid = new Room[myDungeonWidth][myDungeonHeight];
        myRooms = new ArrayList<>();
        myAvailableRoomLocations = new ArrayList<>();
        myObjectiveRooms = new ArrayList<>();
    }

    private void createRooms(final int theNumRooms) {
        int centerX = myDungeonWidth / 2;
        int centerY = myDungeonHeight / 2;
        int startX = centerX + random.nextInt(3) - 1;
        int startY = centerY + random.nextInt(3) - 1;

        myStartRoom = new Room(startX, startY, Room.RoomType.FILLER);
        myDungeonGrid[startX][startY] = myStartRoom;
        myRooms.add(myStartRoom);
        addAvailableLocations(startX, startY);

        for (int i = 1; i < theNumRooms; i++) {
            if (myAvailableRoomLocations.isEmpty()) break;

            Point location = myAvailableRoomLocations.remove(random.nextInt(myAvailableRoomLocations.size()));
            int newX = location.x;
            int newY = location.y;
            Room newRoom = new Room(newX, newY, Room.RoomType.FILLER);
            myDungeonGrid[newX][newY] = newRoom;
            myRooms.add(newRoom);
            addAvailableLocations(newX, newY);

            connectAdjacentRooms(newRoom, newX, newY);
        }
    }

    private boolean assignSpecialRooms() {
        ArrayList<Room> deadEnds = getDeadEndRooms();
        if (deadEnds.size() < MIN_DEAD_ENDS) {
            return false;
        }

        myStartRoom = deadEnds.remove(random.nextInt(deadEnds.size()));
        myStartRoom.setType(Room.RoomType.START);

        myEndRoom = findFurthestRoom(myStartRoom, deadEnds);
        if (myEndRoom != null) {
            myEndRoom.setType(Room.RoomType.END);
            deadEnds.remove(myEndRoom);
        }

        for (int i = 0; i < 4; i++) {
            Room objectiveRoom = deadEnds.remove(random.nextInt(deadEnds.size()));
            objectiveRoom.setType(Room.RoomType.OBJECTIVE);
            myObjectiveRooms.add(objectiveRoom);
        }

        return true;
    }

    private void addAvailableLocations(final int theX, final int theY) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Up, Down, Right, Left
        for (int[] dir : directions) {
            int adjX = theX + dir[0];
            int adjY = theY + dir[1];
            if (isValidRoomPlacement(adjX, adjY)) {
                myAvailableRoomLocations.add(new Point(adjX, adjY));
            }
        }
    }

    private void connectAdjacentRooms(final Room theRoom, final int theX, final int theY) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Up, Down, Right, Left
        for (int[] dir : directions) {
            int adjX = theX + dir[0];
            int adjY = theY + dir[1];
            if (adjX >= 0 && adjY >= 0 && adjX < myDungeonWidth && adjY < myDungeonHeight && myDungeonGrid[adjX][adjY] != null) {
                Room adjacentRoom = myDungeonGrid[adjX][adjY];
                theRoom.connectRoom(dir[0], dir[1], adjacentRoom);
                adjacentRoom.connectRoom(-dir[0], -dir[1], theRoom);
            }
        }
    }

    private ArrayList<Room> getDeadEndRooms() {
        ArrayList<Room> deadEnds = new ArrayList<>();
        for (Room room : myRooms) {
            if (room.getConnectedRooms().size() == 1) {
                deadEnds.add(room);
            }
        }

        return deadEnds;
    }

    private Room findFurthestRoom(final Room theStartRoom, final ArrayList<Room> theDeadEnds) {
        Room furthestRoom = null;
        int maxDistance = -1;

        for (Room room: theDeadEnds) {
            int distance = Math.abs(room. getX() - theStartRoom.getX()) + Math.abs(room.getY() - theStartRoom.getY());
            if (distance > maxDistance) {
                maxDistance = distance;
                furthestRoom = room;
            }
        }

        return furthestRoom;
    }

    private boolean isValidRoomPlacement(final int theX, final int theY) {
        return theX >= 0 && theY >= 0 && theX < myDungeonWidth && theY < myDungeonHeight && myDungeonGrid[theX][theY] == null;
    }

    private void addDoors() {
        for (Room room : myRooms) {
            for (Map.Entry<String, Room> entry : room.getConnectedRooms().entrySet()) {
                String direction = entry.getKey();
                Room adjacentRoom = entry.getValue();
                room.addDoor(direction, adjacentRoom);
            }
        }
    }

    public Map<Point, Room> getGeneratedDungeon() {
        Map<Point, Room> generatedDungeon = new HashMap<>();
        for (Room room : myRooms) {
            generatedDungeon.put(new Point(room.getX(), room.getY()), room);
        }

        return generatedDungeon;
    }

    public void printDungeon() {
        for (int y = myDungeonHeight - 1; y >= 0; y--) {
            for (int x = 0; x < myDungeonWidth; x++) {
                if (myDungeonGrid[x][y] != null) {
                    if (myDungeonGrid[x][y].getRoomType() == Room.RoomType.START) {
                        System.out.print("S");
                    } else if (myDungeonGrid[x][y].getRoomType() == Room.RoomType.END) {
                        System.out.print("E");
                    } else if (myDungeonGrid[x][y].getRoomType() == Room.RoomType.OBJECTIVE) {
                        System.out.print("O");
                    } else {
                        System.out.print("R");
                    }
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }

    public void printDoors() {
        for (Room room : myRooms) {
            System.out.println("Room at (" + room.getX() + ", " + room.getY() + ") has doors:");

            for (Map.Entry<String, Door> doorEntry : room.getDoors().entrySet()) {
                String direction = doorEntry.getKey();

                Room connectedRoom = room.getConnectedRooms().get(direction);

                if (connectedRoom != null) {
                    System.out.println("  " + direction + " -> Room at ("
                            + connectedRoom.getX() + ", "
                            + connectedRoom.getY() + ")");
                } else {
                    System.out.println("  " + direction + " -> No connected room");
                }
            }
        }
    }

    public static void main(String[] args) {
        DungeonGenerator generator = new DungeonGenerator(9, 8);
        generator.generateDungeon(25);
        generator.printDungeon();
        generator.printDoors();
    }
}
