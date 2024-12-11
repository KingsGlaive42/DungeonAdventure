package model.DungeonManager;

import controller.GameController;
import model.AnimationSystem.Sprite;
import model.DungeonCharacters.Monster;
import model.GameConfig;
import model.Player.Player;
import model.PlayerInventory.Item;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Represents a room in the dungeon.
 * A Room can contain items, connect to other rooms, have doors, and be entered by players.
 */
public class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // File paths for loading assets
    private static final String FLOOR_SS_PATH = "src/resources/assets/Terrain/dungeon_floor.png";
    private static final String WALL_SS_PATH = "src/resources/assets/Terrain/dungeon_sprite_sheet.png";

    // Room dimensions and constants
    private final static int ROOM_WIDTH = 17;
    private final static int ROOM_HEIGHT = 13;
    private static final int TILE_SIZE = 32;
    private static final Random ROOM_SPECIFIC_RANDOM = new Random();
    private final Random myRandom = new Random();

    // Sprites for the floor and wall textures
    private final Sprite myFloorSpritesheet = new Sprite();
    private final Sprite myWallSpritesheet = new Sprite();

    // List of items in the room
    private final List<Item> myRoomItems = new ArrayList<>();
    private final List<Monster> myRoomMonsters = new ArrayList<>();

    // Room coordinates
    private final int myX;
    private final int myY;

    // Room properties
    private boolean hasPit;
    private boolean isVisited;

    private RoomType myRoomType;
    private transient BufferedImage[][] myFloorTiles = new BufferedImage[ROOM_WIDTH][ROOM_HEIGHT];

    // Maps for storing connected room sand doors
    private final Map<DoorDirection, Room> myConnectedRooms = new HashMap<>();
    private final Map<DoorDirection, Door> myDoors = new HashMap<>();

    /**
     * Constructs a Room with specified coordinates and type.
     *
     * @param theX The X coordinate of the room.
     * @param theY The Y coordinate of the room.
     * @param theRoomType The type of the room.
     * @throws IllegalArgumentException if theRoomType is null.
     */
    public Room(final int theX, final int theY, final RoomType theRoomType) {
        if (theRoomType == null) {
            throw new IllegalArgumentException("Room type cannot be null");
        }

        myX = theX;
        myY = theY;
        myRoomType = theRoomType;
        hasPit = false;
        isVisited = false;
        loadSpriteSheets();
        initializeAnimations();
    }

    /**
     * Loads the sprite sheets for the floor and wall textures.
     */
    private void loadSpriteSheets() {
        try {
            myFloorSpritesheet.loadSprite(FLOOR_SS_PATH);
            myWallSpritesheet.loadSprite(WALL_SS_PATH);
        } catch (final Exception theException) {
            throw new RuntimeException("Failed to load sprite sheets", theException);
        }

    }

    /**
     * Initializes the floor tiles with random textures.
     */
    private void initializeAnimations() {
        initializeMyTiles();
    }

    /**
     * Initializes the floor tiles array.
     */
    private void initializeMyTilesArray() {
        myFloorTiles = new BufferedImage[ROOM_WIDTH][ROOM_HEIGHT];
    }

    /**
     * Randomly assigns a floor tile texture to each tile in the room.
     */
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

    /**
     * Connects this room to an adjacent room.
     *
     * @param theAdjX The X coordinate difference to the adjacent room.
     * @param theAdjY The Y coordinate difference to the adjacent room.
     * @param theRoom The adjacent room to connect.
     * @throws IllegalArgumentException if theRoom is null or if the adjacent coordinates are invalid.
     */
    public void connectRoom(final int theAdjX, final int theAdjY, final Room theRoom) {
        if (theRoom == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }

        DoorDirection direction = getDirection(theAdjX, theAdjY);

        if (direction == null) {
            throw new IllegalArgumentException("Invalid adjacent coordinates: " + theAdjX + ", " + theAdjY);
        }

        myConnectedRooms.put(direction, theRoom);
        myDoors.put(direction, new Door(direction));
    }

    /**
     * Determines the direction to the adjacent room based on the coordinate difference.
     *
     * @param dx The X coordinate difference.
     * @param dy The Y coordinate difference.
     * @return The DoorDirection to the adjacent room, or null if invalid.
     */
    private DoorDirection getDirection(final int dx, final int dy) {
        if (dx == 1) return DoorDirection.RIGHT;
        if (dx == -1) return DoorDirection.LEFT;
        if (dy == -1) return DoorDirection.UP;
        if (dy == 1) return DoorDirection.DOWN;
        return null;
    }

    /**
     * Checks if the player collides with any of the doors in this room.
     *
     * @param thePlayer The player to check for collisions.
     * @return The direction of the door the player is colliding with, or null if none.
     * @throws IllegalArgumentException if thePlayer is null.
     */
    public DoorDirection checkPlayerCollisionWithDoor(final Player thePlayer) {
        if (thePlayer == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        for (Map.Entry<DoorDirection, Door> entry : myDoors.entrySet()) {
            if (entry.getValue().isPlayerColliding(thePlayer)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Monster checkPlayerCollisionWithMonsters(Player thePlayer) {
        for (Monster monster : myRoomMonsters) {
            if (isPlayerCollidingWithMonster(thePlayer, monster)) {
                return monster;
            }
        }
        return null;
    }

    private boolean isPlayerCollidingWithMonster(Player thePlayer, Monster theMonster) {
        Rectangle2D playerBounds = new Rectangle2D.Double(
                thePlayer.getX() + thePlayer.getTileSize(),
                thePlayer.getY() + thePlayer.getTileSize(),
                thePlayer.getTileSize(),
                thePlayer.getTileSize() * 2
        );
        Rectangle2D monsterBounds = new Rectangle2D.Double(theMonster.getMonsterX(), theMonster.getMonsterY(), TILE_SIZE*2, TILE_SIZE*2);

        if (theMonster.getName().equalsIgnoreCase("gremlin")) {
            monsterBounds = new Rectangle2D.Double(theMonster.getMonsterX() + TILE_SIZE * 1.5, theMonster.getMonsterY() + TILE_SIZE * 1.5, TILE_SIZE * 2, TILE_SIZE * 2);
        }

        return playerBounds.intersects(monsterBounds);
    }

    //void addDoor(final DoorDirection theDirection) {
    /**
     * Adds a door in the specified direction.
     *
     * @param theDirection The direction in which to add the door.
     * @throws IllegalArgumentException if theDirection is null.
     */
    public void addDoor(final DoorDirection theDirection) {
        if (theDirection == null) {
            throw new IllegalArgumentException("DoorDirection cannot be null");
        }

        if (!myDoors.containsKey(theDirection)) {
            myDoors.put(theDirection, new Door(theDirection));
        }
    }

    /**
     * Handles the player entering the room.
     *
     * @param theGameController The game controller managing the inventory and actions.
     * @throws IllegalArgumentException if theGameController is null.
     */
    public void playerEnters(final GameController theGameController) {
        if (theGameController == null) {
            throw new IllegalArgumentException("The gameController cannot be null.");
        }

        if (!isVisited) {
            if (this.hasPit) {
                System.out.println("Fell in pit");
            } else {
                for (Item item : myRoomItems) {
                    //thePlayer.addToInventory(item);
                    theGameController.getInventory().addItem(item);
                }
                myRoomItems.clear();
                placeMonsters();
            }
            isVisited = true;
        }
    }

    /**
     * Adds an item to the room.
     *
     * @param theItem The item to add.
     * @throws IllegalArgumentException if theItem is null.
     */
    public void addItem(Item theItem) {
        if (theItem == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        myRoomItems.add(theItem);
    }

    public void addMonster(Monster theMonster) {
        myRoomMonsters.add(theMonster);
        //System.out.println("Monster added to room: " + myRoomType);
        //System.out.println("Placing " + myRoomMonsters.size() + " monsters in room (" + myX + ", " + myY + ")");
    }

    public void removeMonster(Monster theMonster) {
        myRoomMonsters.remove(theMonster);
    }

    /**
     * Returns a list of items in the room.
     *
     * @return An unmodifiable list of items in the room.
     */
    public List<Item> getRoomItems() {
        return Collections.unmodifiableList(myRoomItems);
    }

    /**
     * Returns the X coordinate of the room.
     *
     * @return The X coordinate of the room.
     */
    public List<Monster> getMyRoomMonsters() {
        return myRoomMonsters;
    }

    public int getX() {
        return myX;
    }

    /**
     * Returns the Y coordinate of the room.
     *
     * @return The Y coordinate of the room.
     */
    public int getY() {
        return myY;
    }

    /**
     * Returns the map of connected rooms.
     *
     * @return An unmodifiable map of connected rooms.
     */
    public Map<DoorDirection, Room> getConnectedRooms() {
        return Collections.unmodifiableMap(myConnectedRooms);
    }

    /**
     * Sets the type of the room.
     *
     * @param theRoomType The type of the room.
     * @throws IllegalArgumentException if theRoomType is null.
     */
    public void setType(final RoomType theRoomType) {
        if (theRoomType == null) {
            throw new IllegalArgumentException("Room type cannot be null");
        }
        myRoomType = theRoomType;
    }

    /**
     * Returns the type of the room.
     *
     * @return The type of the room.
     */
    public RoomType getRoomType() {
        return myRoomType;
    }

    /**
     * Checks if the room has a pit.
     *
     * @return True if the room has a pit, false otherwise.
     */
    public boolean getPit() {
        return hasPit;
    }

    /**
     * Sets the pit status of the room.
     *
     * @param thePit True to set the room as having a pit, false otherwise.
     */
    public void setPit(boolean thePit) {
        this.hasPit = thePit;
    }

    /**
     * Draws the room.
     *
     * @param theGraphics2D The Graphics2D context for drawing.
     * @throws IllegalArgumentException if theGraphics2D is null.
     */
    public void draw(final Graphics2D theGraphics2D) {
        if (theGraphics2D == null) {
            throw new IllegalArgumentException("The Graphics2D Cannot be null.");
        }
        theGraphics2D.setColor(myRoomType == RoomType.START ? Color.GREEN :
                            myRoomType == RoomType.END ? Color.RED :
                            myRoomType == RoomType.OBJECTIVE ? Color.YELLOW : Color.DARK_GRAY);
        theGraphics2D.fillRect(0, 0, ROOM_WIDTH * TILE_SIZE, ROOM_HEIGHT * TILE_SIZE);

        drawFloor(theGraphics2D);
        drawWalls(theGraphics2D);

        for (Door door : myDoors.values()) {
            door.draw(theGraphics2D);
        }
        drawMonsters(theGraphics2D);

        if (GameConfig.isShowHitboxes()) {
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            theGraphics2D.setComposite(ac);
            theGraphics2D.setColor(Color.CYAN);
            for (Monster monster : myRoomMonsters) {
                if (monster.getName().equalsIgnoreCase("gremlin")) {
                    theGraphics2D.fill(new Rectangle2D.Double(monster.getMonsterX() + TILE_SIZE * 1.5, monster.getMonsterY() + TILE_SIZE * 1.5, TILE_SIZE * 2, TILE_SIZE * 2));
                } else {
                    theGraphics2D.fill(new Rectangle(monster.getMonsterX(), monster.getMonsterY(), TILE_SIZE * 2, TILE_SIZE * 2));
                }
            }
            theGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    /**
     * Draws the floor of the room.
     *
     * @param theGraphics2D The Graphics2D context for drawing.
     */
    private void drawFloor(final Graphics2D theGraphics2D) {
        if (myFloorTiles == null) {
            System.err.println("myFloorTiles in Room is null. Skipping floor rendering.");
            return;
        }

        for (int i = 0; i < ROOM_WIDTH; i++) {
            for (int j = 0; j < ROOM_HEIGHT; j++) {
                theGraphics2D.drawImage(myFloorTiles[i][j], i * 32, j * 32, 32, 32, null);
            }
        }
    }

    /**
     * Draws the walls of the room.
     *
     * @param theGraphics2D The Graphics2D context for drawing.
     */
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

    private void drawMonsters(final Graphics2D theGraphics2D) {
        int scaleWidth;
        int scaleHeight;
        for (Monster monster: myRoomMonsters) {
            if (monster.getName().equals("Gremlin")) {
                scaleWidth = 156;
                scaleHeight = 156;
            } else {
                scaleWidth = 64;
                scaleHeight = 64;
            }
            BufferedImage monsterSprite = monster.getSprite();
            if (monsterSprite != null) {
                int monsterX = monster.getMonsterX();
                int monsterY = monster.getMonsterY();
                theGraphics2D.drawImage(monsterSprite, monsterX, monsterY,
                        scaleWidth, scaleHeight, null);
            }
        }
    }

    private void placeMonsters() {
        if (myRoomMonsters.isEmpty()) {
            return;
        }

        List<Point> availablePositions = new ArrayList<>();
        int startX = 4;
        int startY = 4;
        int endX = ROOM_WIDTH - 4;
        int endY = ROOM_HEIGHT - 4;
        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                availablePositions.add(new Point(i * TILE_SIZE, j *TILE_SIZE));
            }
        }
        // shuffle list randomizing positions
        long seed = (long) myX * 31 + myX;
        ROOM_SPECIFIC_RANDOM.setSeed(seed);
        Collections.shuffle(availablePositions, ROOM_SPECIFIC_RANDOM);

        int monsterCount = Math.min(myRoomMonsters.size(), availablePositions.size());
        for (int i = 0; i < monsterCount; i++) {
            if (i < availablePositions.size()) {
                Point pos = availablePositions.get(i);
                Monster monster = myRoomMonsters.get(i);
                monster.setPosition(pos.x, pos.y);
                //System.out.println("Placing " + myRoomMonsters.size() + " monsters in room (" + myX + ", " + myY + ")");
            }
        }
    }

    /**
     * Checks if the room has a door in the specified direction.
     *
     * @param theDirection The direction to check for a door.
     * @return True if there is a door in the given direction, false otherwise.
     */
    public boolean hasDoor(final DoorDirection theDirection) {
        return myDoors.containsKey(theDirection);
    }

    /**
     * Custom deserialization method for the Room class.
     *
     * @param in The ObjectInputStream to read data from.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if the class for an object being restored cannot be found.
     */
    @Serial
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        //System.out.println("Deserialized Room object.");

        initializeMyTilesArray();
        //System.out.println("Reinitialized myFloorTiles.");

        loadSpriteSheets();
        //System.out.println("Reloaded sprite sheets.");

        initializeAnimations();
        //System.out.println("Initialized animations.");
    }
}
