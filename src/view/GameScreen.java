package view;

import controller.GameController;
import controller.InputListener;
import model.AnimationSystem.AssetManager;
import model.AnimationSystem.Sprite;
import model.DungeonManager.Dungeon;
import model.DungeonManager.Room;
import model.DungeonManager.RoomType;
import model.PlayerInventory.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.GameConfig.TILE_SIZE;

/**
 * This is the GameScreen Class
 *
 * @author Aileen Rosas
 */
public class GameScreen implements Screen{
    private final GameController myGameController;
    //private final DialogueManager myDialogueManager;

    private final UIButton myMapButton;
    private final UIButton myInventoryButton;

    private final UIButton myFirstPillarSlot;
    //private final UIButton mySecondPillerSlot;
    //private final UIButton myThirdPillarSlot;
    //private final UIButton myFourthPillerSlot;

    private boolean isMapVisible;
    private boolean isInventoryVisible;
    private boolean isDescriptionVisible = true;
    private String myCurrentMessage;
    private boolean isDialogueVisible;

    private int mySlotRow = 0;
    private int mySlotCol = 0;

    private final Map<RoomType, Image> roomTypeImages = new HashMap<>();
    private Image myMapBackground;
    private Image myLocation;
    private final int myInventoryRows = 7;
    private final int myInventoryCols = 3;
    private final int mySlotSize = 39;
    private final int myPadding = 7;

    /**
     * GameScreen Constructor.
     *
     * @param theAssetManager AssetManager.
     * @param theGameController GameController.
     */
    public GameScreen(final AssetManager theAssetManager, final GameController theGameController) {
        this.myGameController = theGameController;
        //this.myDialogueManager = new DialogueManager();

        myMapButton = new UIButton(theAssetManager.getAsset("mapButton"), new Rectangle(412, 344, 60, 60));
        myInventoryButton = new UIButton(theAssetManager.getAsset("inventoryButton"), new Rectangle(478, 350, 48, 48));

        isMapVisible = false;
        isInventoryVisible = false;
        isDialogueVisible = false;
        myCurrentMessage = "";


        myFirstPillarSlot = new UIButton(theAssetManager.getAsset("abstractionImage"), new Rectangle(TILE_SIZE * 9, 215, TILE_SIZE, TILE_SIZE));

        loadRoomImages();
    }

    /**
     * This method draws the screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void draw(final Graphics2D theGraphics2D) {
        myMapButton.draw(theGraphics2D);
        myInventoryButton.draw(theGraphics2D);

        if (isMapVisible) drawMapScreen(theGraphics2D);
        if (isInventoryVisible) {
            drawInventoryScreen(theGraphics2D);
            if (isDescriptionVisible) {
                drawDescriptionScreen(theGraphics2D);
            }
        }
        drawDialogue(theGraphics2D);
    }

    /**
     * This method handles when the player hovers over a button.
     *
     * @param theMousePoint Mouse Pointer.
     */
    public void handleHoverUpdate(final Point theMousePoint) {
        myMapButton.setHovered(myMapButton.contains(theMousePoint));
        myInventoryButton.setHovered(myInventoryButton.contains(theMousePoint));

        if (isInventoryVisible) {
            myFirstPillarSlot.setHovered(myFirstPillarSlot.contains(theMousePoint));

        }
    }

    /**
     * This method handles the player clicking a button.
     *
     * @param theClickPoint Clicked point.
     */
    public void handleClick(final Point theClickPoint) {
        if (myMapButton.contains(theClickPoint)) {
            toggleMapScreen();
        }
        if (myInventoryButton.contains(theClickPoint)) {
            toggleInventoryScreen();
        }
        if (isDialogueVisible()) {
            hideDialogue();
        }
    }

    /**
     * This method draws the map screen.
     *
     * @param theGraphics2D Graphics.
     */
    private void drawMapScreen(final Graphics2D theGraphics2D) {
        if (myMapBackground != null) {
            theGraphics2D.setClip(new RoundRectangle2D.Float(TILE_SIZE+5, TILE_SIZE+5, TILE_SIZE*7-5, TILE_SIZE*7-5, 35, 35));
            theGraphics2D.drawImage(myMapBackground, TILE_SIZE+5, TILE_SIZE+5, TILE_SIZE*7-5, TILE_SIZE*7-5, null);
            theGraphics2D.setClip(null);
        }
        drawSubWindow(TILE_SIZE, TILE_SIZE, TILE_SIZE * 7, TILE_SIZE * 7, theGraphics2D);
        Dungeon dungeon = myGameController.getDungeon();
        Map<Point, Room> rooms = dungeon.getMyRooms();

        Room playerLocation = myGameController.getDungeon().getMyCurrentRoom();

        int dungeonWidth = dungeon.getMyWidth();
        int dungeonHeight = dungeon.getMyHeight();

        int xOffset = TILE_SIZE + ((TILE_SIZE*7) - dungeonWidth * (TILE_SIZE-15)) / 2;
        int yOffset = TILE_SIZE + ((TILE_SIZE*7) - dungeonHeight * (TILE_SIZE-15)) / 2;
        for(Map.Entry<Point, Room> entry : rooms.entrySet()) {
            Point roomLocation = entry.getKey();
            Room room = entry.getValue();

            Image roomImage = getRoomImage(room.getRoomType());

            if (roomImage != null && room.getVisibility() || room.getRoomType() == RoomType.START) {
                int x = xOffset + roomLocation.x * (TILE_SIZE - 15);
                int y = yOffset + roomLocation.y * (TILE_SIZE - 15);

                theGraphics2D.drawImage(roomImage, x, y, TILE_SIZE - 15, TILE_SIZE - 15, null);

                // players current location
                if (room == playerLocation) {
                    int markerSize = TILE_SIZE - 18;
                    int markerX = x + (TILE_SIZE - 15) / 2 - markerSize / 2;
                    int markerY = y + (TILE_SIZE - 20) / 2 - markerSize / 2;

                    if (myLocation != null) {
                        theGraphics2D.drawImage(myLocation, markerX, markerY, markerSize, markerSize, null);
                    }
                }
            }
        }
    }

    /**
     * This method draws the inventory screen.
     *
     * @param theGraphics2D Graphics.
     */
    private void drawInventoryScreen(final Graphics2D theGraphics2D) {
        drawSubWindow(TILE_SIZE * 8, TILE_SIZE,  TILE_SIZE * 5, TILE_SIZE * 11, theGraphics2D);
        List<Item> items = myGameController.getInventory().getItems();

        int xStart = TILE_SIZE * 8 + 15;
        int yStart = TILE_SIZE + 19;

        int rowItems = 3;
        int itemIndex = 0;

        int cursorX = xStart + (mySlotSize + myPadding) * mySlotCol;
        int cursorY = yStart + (mySlotSize + myPadding) * mySlotRow;

        if (!items.isEmpty()) {
            theGraphics2D.setColor(Color.WHITE);
            theGraphics2D.setStroke(new BasicStroke(3));
            theGraphics2D.drawRoundRect(cursorX-1, cursorY-1, mySlotSize + 2, mySlotSize + 2, 10, 10);
        }

        for (int i  = yStart; itemIndex < items.size(); i += mySlotSize + myPadding) {
            for (int j = xStart; j < xStart + rowItems * (mySlotSize + myPadding) && itemIndex < items.size(); j += mySlotSize + myPadding) {
                Item item = items.get(itemIndex++);
                drawItemSlot(j, i, mySlotSize, mySlotSize, item, theGraphics2D);
            }
        }
    }

    /**
     * This method draws the Item slots.
     *
     * @param theX X position for Item Slot.
     * @param theY Y position for Item Slot.
     * @param theWidth Width of Item Slot.
     * @param theHeight Height of Item Slot.
     * @param theItem The Item.
     * @param theGraphics2D Graphics.
     */
    private void drawItemSlot(final int theX, final int theY, final int theWidth, final int theHeight, final Item theItem, final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.DARK_GRAY);
        theGraphics2D.fillRoundRect(theX+1, theY+1, theWidth-1, theHeight-1, 10, 10);

        if (theItem != null && theItem.getImage() != null) {
            theGraphics2D.drawImage(theItem.getImage(), theX+1, theY+1, theWidth-2, theHeight-2, null);
        }
    }

    /**
     * This method handles inventory navigation.
     *
     * @param theInputListener InputListener.
     */
    public void handleInventoryNavigation(InputListener theInputListener) {
        if (theInputListener.isArrowUpPressed())
            moveCursor(-1, 0);
        if (theInputListener.isArrowDownPressed())
            moveCursor(1, 0);
        if (theInputListener.isArrowLeftPressed())
            moveCursor(0, -1);
        if (theInputListener.isArrowRightPressed())
            moveCursor(0, 1);

        if (theInputListener.isUsePressed() && getSlotIndex() < myGameController.getInventory().getItems().size()) {
            useItem();
        }
    }

    /**
     * This method moves the cursor to different row/column.
     *
     * @param myRowChange Numbers of rows to move by.
     * @param myColChange Number of columns to change by.
     */
    public void moveCursor(int myRowChange, int myColChange) {
        int newRow = Math.max(0, Math.min(mySlotRow + myRowChange, myInventoryRows - 1));
        int newCol = Math.max(0, Math.min(mySlotCol + myColChange, myInventoryCols - 1));

        mySlotRow = newRow;
        mySlotCol = newCol;
    }

    /**
     * This method returns the slot index.
     *
     * @return slot index.
     */
    public int getSlotIndex() {
        return mySlotCol + (mySlotRow * 3);
    }

    /**
     * This method uses an item.
     */
    private void useItem() {
        int slotIndex = getSlotIndex();
        Item selectedItem = myGameController.getInventory().getItems().get(slotIndex);

        if (selectedItem != null) {
            myGameController.useItem(selectedItem, myGameController.getPlayer().getHeroClass(), myGameController.getDungeon(), myGameController.getUI());
        }
    }

    /**
     * This method draws the description screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void drawDescriptionScreen(final Graphics2D theGraphics2D) {
        int itemIndex = getSlotIndex();
        List<Item> items = myGameController.getInventory().getItems();
        if (itemIndex < items.size()) {
            drawSubWindow(TILE_SIZE, TILE_SIZE * 8, TILE_SIZE * 7, TILE_SIZE * 3, theGraphics2D);
            theGraphics2D.setColor(Color.YELLOW);
            theGraphics2D.setFont(new Font("Monospaced", Font.PLAIN, 14));
            theGraphics2D.drawString(items.get(itemIndex).getName(), TILE_SIZE + 15, TILE_SIZE * 8 + 25);
            theGraphics2D.setColor(Color.LIGHT_GRAY);
            theGraphics2D.setFont(new Font("Monospaced", Font.PLAIN, 12));
            int y = TILE_SIZE * 8 + 45;
            for (String line : items.get(itemIndex).getMyDescription().split("\n")) {
                theGraphics2D.drawString(line, TILE_SIZE + 15, y);
                y += 17;
            }
        }
    }

    /**
     * This method loads room images.
     */
    private void loadRoomImages() {
        roomTypeImages.put(RoomType.START, loadImage("src/resources/assets/Map/room_start_floor.png"));
        roomTypeImages.put(RoomType.END, loadImage("src/resources/assets/Map/room_exit_floor.png"));
        roomTypeImages.put(RoomType.OBJECTIVE, loadImage("src/resources/assets/Map/room_objective_floor.png"));
        roomTypeImages.put(RoomType.FILLER, loadImage("src/resources/assets/Map/room_floor.png"));
        myLocation = loadImage("src/resources/assets/Map/location_marker.png");
        myMapBackground = loadImage("src/resources/assets/Map/map_background.png");
    }

    /**
     * This method loads an image.
     *
     * @param theFile filePath of image.
     * @return the Image.
     */
    private Image loadImage(final String theFile) {
        try {
            return ImageIO.read(new File(theFile));
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * This method shows dialogue.
     *
     * @param theMessage Message to be shown.
     */
    public void showDialogue(String theMessage) {
        myCurrentMessage = theMessage;
        isDialogueVisible = true;
    }

    /**
     * This message hides dialogue.
     */
    public void hideDialogue() {
        myCurrentMessage = "";
        isDialogueVisible = false;
    }

    /**
     * This message checks if dialogue is visible.
     *
     * @return true if dialogue is visible, false if not.
     */
    public boolean isDialogueVisible() {
        return isDialogueVisible;
    }

    /**
     * This method draws the dialogue.
     *
     * @param theGraphics2D Graphics.
     */
    public void drawDialogue(final Graphics2D theGraphics2D) {
        if (!isDialogueVisible || myCurrentMessage.isEmpty()) {
            return;
        }

        drawSubWindow(TILE_SIZE, TILE_SIZE * 11, TILE_SIZE * 7, TILE_SIZE * 2 - 15, theGraphics2D);

        theGraphics2D.setFont(new Font("Monospaced", Font.PLAIN, 12));
        theGraphics2D.setColor(Color.LIGHT_GRAY);
        int y = TILE_SIZE * 11 + 30;
        theGraphics2D.drawString(myCurrentMessage, TILE_SIZE + 15, y);
    }

    /**
     * This method gets the room image.
     *
     * @param theRoomType RoomType.
     * @return the image.
     */
    private Image getRoomImage(final RoomType theRoomType) {
        return roomTypeImages.getOrDefault(theRoomType, roomTypeImages.get(RoomType.FILLER));
    }

    /**
     * This method checks if inventory is visible.
     *
     * @return true if inventory is invisible, false if not.
     */
    public boolean isMyInventoryVisible() {
        return isInventoryVisible;
    }

    /**
     * This method toggles the map screen.
     */
    private void toggleMapScreen() {
        isMapVisible = !isMapVisible;
    }

    /**
     * This method toggles inventory screen.
     */
    private void toggleInventoryScreen() {
        isInventoryVisible = !isInventoryVisible;
    }

    /**
     * This method draws the sub window.
     *
     * @param theX X position of window.
     * @param theY Y position of window.
     * @param theWidth Width of window.
     * @param theHeight Height of window.
     * @param theGraphics2D Graphics.
     */
    private void drawSubWindow(final int theX, final int theY, final int theWidth, final int theHeight, final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(new Color(0, 0, 0, 210));
        theGraphics2D.fillRoundRect(theX, theY, theWidth, theHeight, 35, 35);
        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.setStroke(new BasicStroke(5));
        theGraphics2D.drawRoundRect(theX + 5, theY + 5, theWidth - 10, theHeight - 10, 25, 25);
    }
}
