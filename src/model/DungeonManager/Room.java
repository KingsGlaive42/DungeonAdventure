package model.DungeonManager;

import model.AnimationSystem.Sprite;
import model.Player.Player;
import model.PlayerInventory.Item;
import model.PlayerInventory.ItemType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Room {
    private final static int ROOM_WIDTH = 17;
    private final static int ROOM_HEIGHT = 13;
    private static final int TILE_SIZE = 32;
    private static final Random myRandom = new Random();

    private final Sprite myFloorSpritesheet = new Sprite();
    private final Sprite myWallSpritesheet = new Sprite();
    private List<Item> myRoomItems = new ArrayList<>();

    private final int myX;
    private final int myY;
    private boolean hasPit;

    private RoomType myRoomType;
    private final BufferedImage[][] myFloorTiles = new BufferedImage[ROOM_WIDTH][ROOM_HEIGHT];

    private final Map<DoorDirection, Room> myConnectedRooms = new HashMap<>();
    private final Map<DoorDirection, Door> myDoors = new HashMap<>();

    Room(final int theX, final int theY, final RoomType theRoomType) {
        myX = theX;
        myY = theY;
        myRoomType = theRoomType;
        loadSpriteSheets();
        initializeAnimations();
    }

    private void loadSpriteSheets() {
        myFloorSpritesheet.loadSprite("src/resources/assets/Terrain/dungeon_floor.png");
        myWallSpritesheet.loadSprite("src/resources/assets/Terrain/dungeon_sprite_sheet.png");
    }

    private void initializeAnimations() {
        initializeMyTiles();

    }

    private void initializeMyTiles() {
        for (int i = 0; i < ROOM_WIDTH; i++) {
            for (int j = 0; j < ROOM_HEIGHT; j++) {
                int rng = myRandom.nextInt(100);

                if (rng < 70) {
                    myFloorTiles[i][j] = myFloorSpritesheet.getSprite(1, 0);
                }
                else if (rng < 77) {
                    myFloorTiles[i][j] = myFloorSpritesheet.getSprite(2, 0);
                }
                else if (rng < 84) {
                    myFloorTiles[i][j] = myFloorSpritesheet.getSprite(3, 0);
                }
                else if (rng < 91) {
                    myFloorTiles[i][j] = myFloorSpritesheet.getSprite(4, 0);
                }
                else if (rng < 94) {
                    myFloorTiles[i][j] = myFloorSpritesheet.getSprite(1, 1);
                }
                else if (rng < 96) {
                    myFloorTiles[i][j] = myFloorSpritesheet.getSprite(4, 1);
                }
                else {
                    myFloorTiles[i][j] = myFloorSpritesheet.getSprite(2, 2);
                }

            }
        }
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

    void addDoor(final DoorDirection theDirection) {
        if (!myDoors.containsKey(theDirection)) {
            myDoors.put(theDirection, new Door(theDirection));
        }
    }

    public void addItem(Item theItem) {
        myRoomItems.add(theItem);
    }

    public List<Item> getRoomItems() {
        return myRoomItems;
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

    void setType(final RoomType theRoomType) {
        myRoomType = theRoomType;
    }

    RoomType getRoomType() {
        return myRoomType;
    }

    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(myRoomType == RoomType.START ? Color.GREEN :
                            myRoomType == RoomType.END ? Color.RED :
                            myRoomType == RoomType.OBJECTIVE ? Color.YELLOW : Color.DARK_GRAY);
        theGraphics2D.fillRect(0, 0, ROOM_WIDTH * TILE_SIZE, ROOM_HEIGHT * TILE_SIZE);

        drawFloor(theGraphics2D);
        drawWalls(theGraphics2D);

        for (Door door : myDoors.values()) {
            door.draw(theGraphics2D);
        }

        for (Item item : myRoomItems) {
            if (item.getItemType() == ItemType.PILLAR) {
                theGraphics2D.setColor(Color.WHITE);
                theGraphics2D.drawString(item.getName().substring(0, 1), 50, 50);
            }
        }
    }

    private void drawFloor(final Graphics2D theGraphics2D) {
        for (int i = 0; i < ROOM_WIDTH; i++) {
            for (int j = 0; j < ROOM_HEIGHT; j++) {
                theGraphics2D.drawImage(myFloorTiles[i][j], i * 32, j * 32, 32, 32, null);
            }
        }
    }

    private void drawWalls(final Graphics2D theGraphics2D) {
        for (int i = 0; i < ROOM_WIDTH; i++) {
            theGraphics2D.drawImage(myWallSpritesheet.getSprite(4, 1), i * 32, 0, 32, 32, null);
            theGraphics2D.drawImage(myWallSpritesheet.getSprite(2, 0 ), i * 32, 384, 32, 32, null);
        }

        for (int j = 0; j < ROOM_HEIGHT; j++) {
            theGraphics2D.drawImage(myWallSpritesheet.getSprite(1, 5), 0, j * 32, 32, 32, null);
            theGraphics2D.drawImage(myWallSpritesheet.getSprite(8, 5), 512, j * 32, 32, 32, null);
        }
    }
}
