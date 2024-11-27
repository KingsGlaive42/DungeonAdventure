package view;

import controller.GameStateManager;
import controller.InputListener;
import model.PlayerInventory.Inventory;
import model.PlayerInventory.Item;
import model.PlayerInventory.ItemType;
import utilities.GameConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class UI {
    private static final int BAG_ICON_SIZE = 48;
    private static final int BAG_ICON_OFFSET = 66;
    private static final int MAP_ICON_SIZE = 60;
    private static final int MAP_ICON_OFFSET_X = 132;
    private static final int MAP_ICON_OFFSET_Y = 72;
    private static final float BUTTON_ALPHA_HOVER = 0.5f;
    private static final float BUTTON_ALPHA_DEFAULT = 1f;
    private static final int SCREEN_WIDTH = GameConfig.SCREEN_WIDTH;
    private static final int SCREEN_HEIGHT = GameConfig.SCREEN_HEIGHT;
    private static final int TILE_SIZE = GameConfig.TILE_SIZE;

    private final GameStateManager myGameStateManager;
    private Graphics2D myGraphics2D;

    private BufferedImage bagIcon, mapIcon, playButtonImage;
    private BufferedImage optionsImage, exitImage;
    private Rectangle bagIconBounds, mapIconBounds, playButtonBounds, optionButtonBounds, exitButtonBounds;
    private boolean isBagHovered = false, isMapHovered = false, isPlayHovered = false, isOptionsHovered = false, isExitHovered = false;
    private boolean isBagVisible = false, isMapVisible = false;

    private final Inventory myInventory;

    public UI(final GameStateManager theGameStateManager, final Inventory theInventory) {
        this.myGameStateManager = theGameStateManager;
        this.myInventory = theInventory;

        loadAssets();
    }

    private void loadAssets() {
        try {
            playButtonImage =  ImageIO.read(new File("src/resources/assets/Buttons/Royal Buttons/Gold/royal gold button (not pressed).png"));
            optionsImage = ImageIO.read(new File("src/resources/assets/Buttons/Colored Buttons/light orange/options.png"));
            exitImage = ImageIO.read(new File("src/resources/assets/Buttons/Colored Buttons/light orange/exit.png"));
            bagIcon = ImageIO.read(new File("src/resources/assets/Potato_seeds.png"));
            mapIcon = ImageIO.read(new File("src/resources/assets/map-icon.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        bagIconBounds = new Rectangle(SCREEN_WIDTH - BAG_ICON_OFFSET, SCREEN_HEIGHT - BAG_ICON_OFFSET, BAG_ICON_SIZE, BAG_ICON_SIZE);
        mapIconBounds = new Rectangle(SCREEN_WIDTH - MAP_ICON_OFFSET_X, SCREEN_HEIGHT - MAP_ICON_OFFSET_Y, MAP_ICON_SIZE, MAP_ICON_SIZE);
    }

    public void drawTitleScreen(final Graphics2D theGraphics2D) {
        this.myGraphics2D = theGraphics2D;
        drawBackground(Color.BLACK);
        drawPlayButton();
        drawOptionsButton();
        drawExitButton();
    }

    private void drawBackground(final Color theColor) {
        myGraphics2D.setColor(theColor);
        myGraphics2D.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void drawPlayButton() {
        setAlphaComposite(isPlayHovered ? BUTTON_ALPHA_HOVER : BUTTON_ALPHA_DEFAULT);
        int x = calculatePlayButtonX();
        int y = calculatePlayButtonY();
        playButtonBounds = new Rectangle(x, y, calculatePlayButtonWidth(), calculatePlayButtonHeight());
        myGraphics2D.drawImage(playButtonImage, x, y, calculatePlayButtonWidth(), calculatePlayButtonHeight(), null);
        resetComposite();
    }

    private void drawOptionsButton() {
        setAlphaComposite(isOptionsHovered ? BUTTON_ALPHA_HOVER : BUTTON_ALPHA_DEFAULT);
        int x = calculatePlayButtonX() + 18;
        int y = calculatePlayButtonY() + calculatePlayButtonHeight();
        optionButtonBounds = new Rectangle(x, y, 112, 55);
        myGraphics2D.drawImage(optionsImage, x, y, 112, 55, null);
        resetComposite();
    }

    private void drawExitButton() {
        setAlphaComposite(isExitHovered ? BUTTON_ALPHA_HOVER : BUTTON_ALPHA_DEFAULT);
        int x = calculatePlayButtonX() + 18;
        int y = calculateOptionButtonY() + calculateOptionButtonHeight();
        exitButtonBounds = new Rectangle(x, y, 112, 55);
        myGraphics2D.drawImage(exitImage, x, y, 112, 55, null);
        resetComposite();
    }

    private int calculatePlayButtonX() {
        return (SCREEN_WIDTH - playButtonImage.getWidth()) / 2 - 42;
    }

    private int calculatePlayButtonY() {
        return (SCREEN_HEIGHT - playButtonImage.getHeight()) / 2 - 42;
    }

    private int calculatePlayButtonWidth() {
        return playButtonImage.getWidth() * 2;
    }

    private int calculatePlayButtonHeight() {
        return playButtonImage.getHeight() * 2;
    }

    private int calculateOptionButtonHeight() {
        return (int) optionButtonBounds.getHeight();
    }

    private int calculateOptionButtonY() {
        return (int) optionButtonBounds.getY();
    }

    private void drawBagIcon() {
        if (bagIcon != null) {
            drawIconWithHoverEffect(bagIcon, bagIconBounds, isBagHovered, SCREEN_WIDTH - BAG_ICON_OFFSET, SCREEN_HEIGHT - BAG_ICON_OFFSET);
        }
    }

    private void drawMapIcon() {
        if (mapIcon != null) {
            drawIconWithHoverEffect(mapIcon, mapIconBounds, isMapHovered, SCREEN_WIDTH - MAP_ICON_OFFSET_X, SCREEN_HEIGHT - MAP_ICON_OFFSET_Y);
        }
    }

    private void drawIconWithHoverEffect(final BufferedImage theImage, final Rectangle theBounds, final boolean isHovered, final int theOffsetX, final int theOffsetY) {
        setAlphaComposite(isHovered ? BUTTON_ALPHA_HOVER : BUTTON_ALPHA_DEFAULT);
        myGraphics2D.drawImage(theImage, theOffsetX, theOffsetY, theBounds.width, theBounds.height, null);
        resetComposite();
    }

    public void drawPauseScreen(final Graphics2D theGraphics2D) {
        this.myGraphics2D = theGraphics2D;
        theGraphics2D.setColor(new Color(0, 0, 0, 150));
        theGraphics2D.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.setFont(new Font("Arial", Font.BOLD, 48));
        theGraphics2D.drawString("PAUSED", SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2);

    }

    public void drawHUD(final Graphics2D theGraphics2D) {
        this.myGraphics2D = theGraphics2D;

        drawMapIcon();
        drawBagIcon();

        if (isMapVisible) drawMapScreen();
        if (isBagVisible) drawInventoryScreen();
    }

    private void drawMapScreen() {
        drawSubWindow(TILE_SIZE, TILE_SIZE, TILE_SIZE * 7, TILE_SIZE * 7);
    }

    private void drawInventoryScreen() {
        drawSubWindow(TILE_SIZE * 8, TILE_SIZE, TILE_SIZE * 5, TILE_SIZE * 11);
        List<Item> items = myInventory.getItems();

        int slotSize = 39;
        int padding = 7;
        int xStart = TILE_SIZE * 8 + 15;
        int yStart = TILE_SIZE + 19;

        int rowItems = 3;
        int itemIndex = 0;

        for (int i = yStart; itemIndex < items.size(); i += slotSize + padding) {
            for (int k = xStart; k < xStart + rowItems * (slotSize + padding) && itemIndex < items.size(); k += slotSize + padding) {
                Item item = items.get(itemIndex++);
                drawItemSlot(k, i, slotSize, slotSize, item);
            }
        }
    }

    private void drawItemSlot(int theX, int theY, int theWidth, int theHeight, Item theItem) {
        myGraphics2D.setColor(Color.DARK_GRAY);
        myGraphics2D.fillRect(theX, theY, theWidth, theHeight);

        if (theItem != null && theItem.getImage() != null) {
            myGraphics2D.drawImage(theItem.getImage(), theX, theY, theWidth, theHeight, null);
        }
    }

    public void toggleInventoryScreen() {
        isBagVisible = !isBagVisible;
    }

    public void toggleMapScreen() {
        isMapVisible = !isMapVisible;
    }

    private void drawSubWindow(final int theX, final int theY, final int theWidth, final int theHeight) {
        myGraphics2D.setColor(new Color(0, 0, 0, 210));
        myGraphics2D.fillRoundRect(theX, theY, theWidth, theHeight, 35, 35);
        myGraphics2D.setColor(Color.WHITE);
        myGraphics2D.setStroke(new BasicStroke(5));
        myGraphics2D.drawRoundRect(theX + 5, theY + 5, theWidth - 10, theHeight - 10, 25, 25);
    }

    private void setAlphaComposite(final float theAlpha) {
        myGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, theAlpha));
    }

    private void resetComposite() {
        myGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, BUTTON_ALPHA_DEFAULT));
    }

    public void updateHoverStates(final Point theMousePoint) {
        isBagHovered = bagIconBounds.contains(theMousePoint);
        isMapHovered = mapIconBounds.contains(theMousePoint);
        isPlayHovered = playButtonBounds.contains(theMousePoint);
        isOptionsHovered = optionButtonBounds.contains(theMousePoint);
        isExitHovered = exitButtonBounds.contains(theMousePoint);
    }

    public void handleMenuStateClick(final Point theClickPoint) {
        if (playButtonBounds.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.GAME);
        } else if (optionButtonBounds.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.OPTION);
        } else if (exitButtonBounds.contains(theClickPoint)) {
                System.exit(0);
        }
    }

    public void handleGameStateClick(final Point theClickPoint) {
        if (mapIconBounds.contains(theClickPoint)) {
            toggleMapScreen();
        }
        if (bagIconBounds.contains(theClickPoint)) {
            toggleInventoryScreen();
        }
    }
}
