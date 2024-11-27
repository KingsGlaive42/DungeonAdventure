package model.DungeonManager;

import model.DungeonCharacters.Monster;
import model.MonsterManager.MonsterGeneration;
import model.PlayerInventory.*;

import java.awt.Point;
import java.util.*;

class DungeonGenerator {
    private static final int MIN_DEAD_ENDS = 6;
    private static final int MIN_DUNGEON_DIMENSION = 8;
    private static final int STARTING_ROOM_RADIUS = 3;
    private static final int NUM_OBJECTIVE_ROOMS = 4;
    private static final Random RNG = new Random();
    private final String[] myPillarsNames = {"Abstraction", "Encapsulation", "Inheritance", "Polymorphism"};

    private final int myDungeonWidth;
    private final int myDungeonHeight;
    private Room[][] myDungeonGrid;
    private ArrayList<Point> myAvailableRoomLocations;

    private Room myStartRoom;
    private ArrayList<Room> myObjectiveRooms;

    DungeonGenerator(final int theDungeonWidth, final int theDungeonHeight) {
        myDungeonWidth = Math.max(theDungeonWidth, MIN_DUNGEON_DIMENSION);
        myDungeonHeight = Math.max(theDungeonHeight, MIN_DUNGEON_DIMENSION);
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
        int startX = centerX + RNG.nextInt(STARTING_ROOM_RADIUS) - 1;
        int startY = centerY + RNG.nextInt(STARTING_ROOM_RADIUS) - 1;

        myStartRoom = new Room(startX, startY, RoomType.FILLER);
        myDungeonGrid[startX][startY] = myStartRoom;
        addAvailableLocations(startX, startY);

        for (int i = 1; i < theNumRooms; i++) {
            if (myAvailableRoomLocations.isEmpty()) break;

            Point location = myAvailableRoomLocations.remove(RNG.nextInt(myAvailableRoomLocations.size()));
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

        myStartRoom = deadEnds.remove(RNG.nextInt(deadEnds.size()));
        myStartRoom.setType(RoomType.START);

        Room myEndRoom = findFurthestRoom(myStartRoom, deadEnds);
        if (myEndRoom != null) {
            myEndRoom.setType(RoomType.END);
            deadEnds.remove(myEndRoom);
        }

        for (int i = 0; i < NUM_OBJECTIVE_ROOMS; i++) {
            Room objectiveRoom = deadEnds.remove(RNG.nextInt(deadEnds.size()));
            objectiveRoom.setType(RoomType.OBJECTIVE);
            myObjectiveRooms.add(objectiveRoom);
        }

        assignPillars();
        addDungeonItemsAndMonsters();

        return true;
    }

    private void assignPillars() {
        for (int i = 0; i < NUM_OBJECTIVE_ROOMS; i++) {
            Room objectiveRoom = myObjectiveRooms.get(i);
            String pillar = myPillarsNames[i];
            Pillar pillarItem = new Pillar(pillar);
            objectiveRoom.addItem(pillarItem);
        }
    }

    private void addDungeonItemsAndMonsters() {
        MonsterGeneration monsters = new MonsterGeneration();
        List<Monster> randomMonsters = monsters.generateMonsters(24);
        int monsterIndex =  0;
        for (int i = 0; i < myDungeonWidth; i++) {
            for (int k = 0; k < myDungeonHeight; k++) {
                Room room = myDungeonGrid[i][k];
                if (room == null) {
                    continue;
                }
                if (room.getRoomType() == RoomType.FILLER) {
                    placeRandomItems(room);
                }
                if (room.getRoomType() == RoomType.OBJECTIVE) {
                    for (int j = 0; j < 2; j++) {
                        room.addMonster(randomMonsters.get(monsterIndex));
                        monsterIndex++;
                    }
                } else if (room.getRoomType() == RoomType.END) {
                    for (int j = 0; j < 4; j++) {
                        room.addMonster(randomMonsters.get(monsterIndex));
                        monsterIndex++;
                    }
                } else if (monsterIndex < randomMonsters.size() && Math.random() < 0.2 &&
                        room.getRoomType() == RoomType.FILLER) {
                    room.addMonster(randomMonsters.get(monsterIndex));
                    monsterIndex++;
                }
            }
        }
    }

    // update chances/logic
    private void placeRandomItems(final Room theRoom) {
        if (Math.random() < 0.1) {
            theRoom.setPit(true);
            System.out.println("add a pit");
        } else if (!theRoom.getPit()){
            if (Math.random() < 0.1) {
                System.out.println("add healing potion");
                HealingPotion hPotion = new HealingPotion();
                theRoom.addItem(hPotion);
            }
            if (Math.random() < 0.1) {
                System.out.println("add vision potion");
                VisionPotion vPotion = new VisionPotion();
                theRoom.addItem(vPotion);
            }
        }
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
                    } else if (myDungeonGrid[i][j].getPit()) {
                        System.out.print("X");
                    } else if (myDungeonGrid[i][j].getRoomItems().size() > 1) {
                        System.out.print("M");
                    } else if ((myDungeonGrid[i][j].getRoomType() == RoomType.FILLER) &&
                            (!myDungeonGrid[i][j].getRoomItems().isEmpty())) {
                        for (Item item : myDungeonGrid[i][j].getRoomItems()) {
                            if (item.getItemType() == ItemType.HEALING_POTION) {
                                System.out.print("H");
                            } else if (item.getItemType() == ItemType.VISION_POTION) {
                                System.out.print("V");
                            }
                        }
                    }
                    else {
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
