package view;

import model.PlayerInventory.Inventory;
import model.PlayerInventory.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class UI {
    private final GamePanel myGamePanel;
    private Graphics2D myGraphics2D;

    private static final int BAG_ICON_SIZE = 48;
    private static final int BAG_ICON_OFFSET = 66;
    private static final int MAP_ICON_SIZE = 60;
    private static final int MAP_ICON_OFFSET_X = 132;
    private static final int MAP_ICON_OFFSET_Y = 72;
    private static final float BUTTON_ALPHA_HOVER = 0.5f;
    private static final float BUTTON_ALPHA_DEFAULT = 1f;

    private BufferedImage bagIcon, mapIcon, unpressedPlayButtonImage, pressedPlayButtonImage, playButtonImage;
    private BufferedImage optionsImage, exitImage;
    private Rectangle bagIconBounds, mapIconBounds, playButtonBounds, optionButtonBounds, exitButtonBounds;
    private boolean isBagHovered = false, isMapHovered = false, isPlayHovered = false, isOptionsHovered = false, isExitHovered = false;
    private boolean isBagVisible = false, isMapVisible = false;

    private final Inventory myInventory;

    public UI(final GamePanel theGamePanel, final Inventory theInventory) {
        this.myGamePanel = theGamePanel;
        this.myInventory = theInventory;

        loadIcons();

        myGamePanel.addMouseListener(new BagMouseAdapter());
        myGamePanel.addMouseMotionListener(new BagMouseAdapter());
    }

    private void loadIcons() {
        try {
            unpressedPlayButtonImage = ImageIO.read(new File("src/resources/assets/Buttons/Royal Buttons/Gold/royal gold button (not pressed).png"));
            pressedPlayButtonImage = ImageIO.read(new File("src/resources/assets/Buttons/Royal Buttons/Gold/royal gold button (pressed).png"));
            playButtonImage = unpressedPlayButtonImage;

            optionsImage = ImageIO.read(new File("src/resources/assets/Buttons/Colored Buttons/light orange/options.png"));
            exitImage = ImageIO.read(new File("src/resources/assets/Buttons/Colored Buttons/light orange/exit.png"));

            bagIcon = ImageIO.read(new File("src/resources/assets/Potato_seeds.png"));
            bagIconBounds = new Rectangle(myGamePanel.getScreenWidth() - BAG_ICON_OFFSET, myGamePanel.getScreenHeight() - BAG_ICON_OFFSET, BAG_ICON_SIZE, BAG_ICON_SIZE);

            mapIcon = ImageIO.read(new File("src/resources/assets/map-icon.png"));
            mapIconBounds = new Rectangle(myGamePanel.getScreenWidth() - MAP_ICON_OFFSET_X, myGamePanel.getScreenHeight() - MAP_ICON_OFFSET_Y, MAP_ICON_SIZE, MAP_ICON_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(final Graphics2D theGraphics2D) {
        this.myGraphics2D = theGraphics2D;

        if (myGamePanel.getState() == GamePanel.State.MENU_STATE) {
            drawTitleScreen();
        } else if (myGamePanel.getState() == GamePanel.State.GAME_STATE) {
            drawBagIcon();
            drawMapIcon();
            if (isMapVisible) drawMapScreen();
            if (isBagVisible) drawInventoryScreen();
        } else if (myGamePanel.getState() == GamePanel.State.PAUSE_STATE) {
            drawPauseScreen();
        }
    }

    private void drawTitleScreen() {
        drawBackground(Color.BLACK);
        drawPlayButton();
        drawOptionsButton();
        drawExitButton();
    }

    private void drawBackground(final Color theColor) {
        myGraphics2D.setColor(theColor);
        myGraphics2D.fillRect(0, 0, myGamePanel.getScreenWidth(), myGamePanel.getScreenHeight());
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
        return (myGamePanel.getScreenWidth() - unpressedPlayButtonImage.getWidth()) / 2 - 42;
    }

    private int calculatePlayButtonY() {
        return (myGamePanel.getScreenHeight() - unpressedPlayButtonImage.getHeight()) / 2 - 42;
    }

    private int calculatePlayButtonWidth() {
        return unpressedPlayButtonImage.getWidth() * 2;
    }

    private int calculatePlayButtonHeight() {
        return unpressedPlayButtonImage.getHeight() * 2;
    }

    private int calculateOptionButtonHeight() {
        return (int) optionButtonBounds.getHeight();
    }

    private int calculateOptionButtonY() {
        return (int) optionButtonBounds.getY();
    }

    private void drawBagIcon() {
        if (bagIcon != null) {
            drawIconWithHoverEffect(bagIcon, bagIconBounds, isBagHovered, myGamePanel.getScreenWidth() - BAG_ICON_OFFSET, myGamePanel.getScreenHeight() - BAG_ICON_OFFSET);
        }
    }

    private void drawMapIcon() {
        if (mapIcon != null) {
            drawIconWithHoverEffect(mapIcon, mapIconBounds, isMapHovered, myGamePanel.getScreenWidth() - MAP_ICON_OFFSET_X, myGamePanel.getScreenHeight() - MAP_ICON_OFFSET_Y);
        }
    }

    private void drawIconWithHoverEffect(final BufferedImage theImage, final Rectangle theBounds, final boolean isHovered, final int theOffsetX, final int theOffsetY) {
        setAlphaComposite(isHovered ? BUTTON_ALPHA_HOVER : BUTTON_ALPHA_DEFAULT);
        myGraphics2D.drawImage(theImage, theOffsetX, theOffsetY, theBounds.width, theBounds.height, null);
        resetComposite();
    }

    private void drawPauseScreen() {
        myGraphics2D.drawString("PAUSED", myGamePanel.getTileSize() * 8, myGamePanel.getScreenHeight() / 2);
    }

    private void drawMapScreen() {
        drawSubWindow(myGamePanel.getTileSize(), myGamePanel.getTileSize(), myGamePanel.getTileSize() * 7, myGamePanel.getTileSize() * 7);
    }

    private void drawInventoryScreen() {
        drawSubWindow(myGamePanel.getTileSize() * 8, myGamePanel.getTileSize(), myGamePanel.getTileSize() * 5, myGamePanel.getTileSize() * 11);
        List<Item> items = myInventory.getItems();
        int itemY = myGamePanel.getTileSize() + 20;
        for (Item item : items) {
            myGraphics2D.drawString(item.getName(), myGamePanel.getTileSize() * 8 + 10, itemY);
            myGraphics2D.drawString(item.getMyDescription(), myGamePanel.getTileSize() * 8 + 10, itemY + 15);
            itemY += 40;
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

    private class BagMouseAdapter extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            updateHoverStates(e.getPoint());
            myGamePanel.repaint();
        }

        @Override
        public void mouseClicked(final MouseEvent theEvent) {
            if (myGamePanel.getState() == GamePanel.State.MENU_STATE) {
                handleMenuStateClick(theEvent.getPoint());
            }
            if (isMapHovered) toggleMapScreen();
            if (isBagHovered) toggleInventoryScreen();
        }

        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            if (myGamePanel.getState() == GamePanel.State.MENU_STATE) {
                if (playButtonBounds.contains(theEvent.getPoint())) {
                    playButtonImage = unpressedPlayButtonImage;
                    myGamePanel.setState(GamePanel.State.GAME_STATE);
                } else {
                    playButtonImage = unpressedPlayButtonImage;
                }
                myGamePanel.repaint();
            }
        }

        private void updateHoverStates(final Point theMousePoint) {
            isBagHovered = bagIconBounds.contains(theMousePoint);
            isMapHovered = mapIconBounds.contains(theMousePoint);
            isPlayHovered = playButtonBounds.contains(theMousePoint);
            isOptionsHovered = optionButtonBounds.contains(theMousePoint);
            isExitHovered = exitButtonBounds.contains(theMousePoint);
        }

        private void handleMenuStateClick(final Point theClickPoint) {
            if (playButtonBounds.contains(theClickPoint)) {
                playButtonImage = pressedPlayButtonImage;
                myGamePanel.repaint();
            } else if (optionButtonBounds.contains(theClickPoint)) {
                myGamePanel.setState(GamePanel.State.OPTION_STATE);
            } else if (exitButtonBounds.contains(theClickPoint)) {
                System.exit(0);
            }
        }
    }
}
