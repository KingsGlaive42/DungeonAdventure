package model.DungeonManager;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class DungeonGenerator {
    private static final int MIN_DEAD_ENDS = 6;
    private static final int MIN_DUNGEON_DIMENSION = 8;

    private final int myDungeonWidth;
    private final int myDungeonHeight;
    private Room[][] myDungeonGrid;
    private ArrayList<Point> myAvailableRoomLocations;
    private final Random random;
    private Room myStartRoom;
    private ArrayList<Room> myObjectiveRooms;

    DungeonGenerator(final int theDungeonWidth, final int theDungeonHeight) {
        myDungeonWidth = Math.max(theDungeonWidth, MIN_DUNGEON_DIMENSION);
        myDungeonHeight = Math.max(theDungeonHeight, MIN_DUNGEON_DIMENSION);
        random = new Random();
    }

    void generateDungeon(final int theNumRooms) {
        do {
            initializeDungeon();
            createRooms(theNumRooms);
        } while (!assignSpecialRooms());

        addDoors();
    }

    private void initializeDungeon() {
        myDungeonGrid = new Room[myDungeonWidth][myDungeonHeight];
        myAvailableRoomLocations = new ArrayList<>();
        myObjectiveRooms = new ArrayList<>();
    }

    private void createRooms(final int theNumRooms) {
        int centerX = myDungeonWidth / 2;
        int centerY = myDungeonHeight / 2;
        int startX = centerX + random.nextInt(3) - 1;
        int startY = centerY + random.nextInt(3) - 1;

        myStartRoom = new Room(startX, startY, RoomType.FILLER);
        myDungeonGrid[startX][startY] = myStartRoom;
        addAvailableLocations(startX, startY);

        for (int i = 1; i < theNumRooms; i++) {
            if (myAvailableRoomLocations.isEmpty()) break;

            Point location = myAvailableRoomLocations.remove(random.nextInt(myAvailableRoomLocations.size()));
            int newX = location.x;
            int newY = location.y;
            Room newRoom = new Room(newX, newY, RoomType.FILLER);
            myDungeonGrid[newX][newY] = newRoom;
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
        myStartRoom.setType(RoomType.START);

        Room myEndRoom = findFurthestRoom(myStartRoom, deadEnds);
        if (myEndRoom != null) {
            myEndRoom.setType(RoomType.END);
            deadEnds.remove(myEndRoom);
        }

        for (int i = 0; i < 4; i++) {
            Room objectiveRoom = deadEnds.remove(random.nextInt(deadEnds.size()));
            objectiveRoom.setType(RoomType.OBJECTIVE);
            myObjectiveRooms.add(objectiveRoom);
        }

        return true;
    }

    private void addAvailableLocations(final int theX, final int theY) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Down, Up, Right, Left
        for (int[] dir : directions) {
            int adjX = theX + dir[0];
            int adjY = theY + dir[1];
            if (isValidRoomPlacement(adjX, adjY)) {
                myAvailableRoomLocations.add(new Point(adjX, adjY));
            }
        }
    }

    private void connectAdjacentRooms(final Room theRoom, final int theX, final int theY) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Down, Up, Right, Left
        for (int[] dir : directions) {
            int xOffset = dir[0];
            int yOffset = dir[1];
            int adjX = theX + xOffset;
            int adjY = theY + yOffset;
            if (adjX >= 0 && adjY >= 0 && adjX < myDungeonWidth && adjY < myDungeonHeight && myDungeonGrid[adjX][adjY] != null) {
                Room adjacentRoom = myDungeonGrid[adjX][adjY];
                theRoom.connectRoom(xOffset, yOffset, adjacentRoom);
                adjacentRoom.connectRoom(-xOffset, -yOffset, theRoom);
            }
        }
    }

    private ArrayList<Room> getDeadEndRooms() {
        ArrayList<Room> deadEnds = new ArrayList<>();

        for (int i = 0; i < myDungeonWidth; i++) {
            for (int j = 0; j < myDungeonHeight; j++) {
                if (myDungeonGrid[i][j] == null) continue;

                if (myDungeonGrid[i][j].getConnectedRooms().size() == 1) {
                    deadEnds.add(myDungeonGrid[i][j]);
                }
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
        return  theX >= 0 && theY >= 0 &&
                theX < myDungeonWidth &&
                theY < myDungeonHeight &&
                myDungeonGrid[theX][theY] == null;
    }

    private void addDoors() {
        for (int i = 0; i < myDungeonWidth; i++) {
            for (int j = 0; j < myDungeonHeight; j++) {
                if (myDungeonGrid[i][j] == null) continue;

                for (Map.Entry<DoorDirection, Room> entry: myDungeonGrid[i][j].getConnectedRooms().entrySet()) {
                    DoorDirection direction = entry.getKey();
                    myDungeonGrid[i][j].addDoor(direction);
                }
            }
        }
    }

    Map<Point, Room> getGeneratedDungeon() {
        Map<Point, Room> generatedDungeon = new HashMap<>();

        for (int i = 0; i < myDungeonWidth; i++) {
            for (int j = 0; j < myDungeonHeight; j++) {
                if (myDungeonGrid[i][j] == null) continue;

                generatedDungeon.put(new Point(i, j), myDungeonGrid[i][j]);
            }
        }

        return generatedDungeon;
    }

    void printDungeon() {
        for (int i = 0; i < myDungeonWidth; i++) {
            for (int j = 0; j < myDungeonHeight; j++) {
                if (myDungeonGrid[i][j] != null) {
                    if (myDungeonGrid[i][j].getRoomType() == RoomType.START) {
                        System.out.print("S");
                    } else if (myDungeonGrid[i][j].getRoomType() == RoomType.END) {
                        System.out.print("E");
                    } else if (myDungeonGrid[i][j].getRoomType() == RoomType.OBJECTIVE) {
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

        System.out.println();
    }
}
