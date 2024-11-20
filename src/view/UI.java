package view;

import controller.InputListener;
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

    private BufferedImage bagIcon;
    private Rectangle bagBounds;
    private boolean bagHovered = false;
    private boolean bagOn;

    private BufferedImage mapIcon;
    private Rectangle mapBounds;
    private boolean mapHovered;
    private boolean mapOn;

    private final Inventory myInventory;

    public UI(final GamePanel theGamePanel, Inventory theInventory) {
        this.myGamePanel = theGamePanel;
        this.myInventory = theInventory;

        loadBagIcon();
        bagOn = false;

        loadMapIcon();
        mapOn = false;

        myGamePanel.addMouseListener(new BagMouseAdapter());
        myGamePanel.addMouseMotionListener(new BagMouseAdapter());
    }

    public void draw(Graphics2D theGraphics2D) {
        this.myGraphics2D = theGraphics2D;


        if (myGamePanel.getState() == GamePanel.State.MENU_STATE) {
            drawTitleScreen();
        }
        else if (myGamePanel.getState() == GamePanel.State.GAME_STATE) {
            drawBagIcon();
            drawMapIcon();
            if (mapOn) {
                drawMapScreen();
            }
            if (bagOn) {
                drawInventoryScreen();
            }
        }
        else if (myGamePanel.getState() == GamePanel.State.PAUSE_STATE) {
            drawPauseScreen();
        }
    }

    public void drawTitleScreen() {

    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = myGamePanel.getTileSize() * 8;
        int y = myGamePanel.getScreenHeight() / 2;

        myGraphics2D.drawString(text, x, y);
    }

    private void loadMapIcon() {
        try {
            mapIcon = ImageIO.read(new File("src/resources/assets/map-icon.png"));
            mapBounds = new Rectangle(myGamePanel.getScreenWidth() - 132, myGamePanel.getScreenHeight() - 72, 60, 60); // Position & size
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawMapIcon() {
        if (mapIcon != null) {
            float alpha = mapHovered ? 1f : 0.5f;

            Composite originalComposite = myGraphics2D.getComposite();
            myGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            myGraphics2D.drawImage(mapIcon, myGamePanel.getScreenWidth() - 132, myGamePanel.getScreenHeight() - 72, 60, 60, null);
            myGraphics2D.setComposite(originalComposite);
        }
    }

    public void drawMapScreen() {
        //Create a frame
        final int frameX = myGamePanel.getTileSize();
        final int frameY = myGamePanel.getTileSize();
        final int frameWidth = myGamePanel.getTileSize() * 7;
        final int frameHeight = myGamePanel.getTileSize() * 7;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
    }

    public void toggleMapScreen() {
        mapOn = !mapOn;
    }

    private void loadBagIcon() {
        try {
            bagIcon = ImageIO.read(new File("src/resources/assets/Potato_seeds.png"));
            bagBounds = new Rectangle(myGamePanel.getScreenWidth() - 66, myGamePanel.getScreenHeight() - 66, 48, 48); // Position & size
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawBagIcon() {
        if (bagIcon != null) {
            float alpha = bagHovered ? 1f : 0.5f;

            Composite originalComposite = myGraphics2D.getComposite();
            myGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            myGraphics2D.drawImage(bagIcon, myGamePanel.getScreenWidth() - 66, myGamePanel.getScreenHeight() - 66, 48, 48, null);
            myGraphics2D.setComposite(originalComposite);
        }
    }
    public void drawInventoryScreen() {
        //Create a frame
        final int frameX = myGamePanel.getTileSize() * 8;
        final int frameY = myGamePanel.getTileSize();
        final int frameWidth = myGamePanel.getTileSize() * 5;
        final int frameHeight = myGamePanel.getTileSize() * 11;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        List<Item> items = myInventory.getItems();
        //System.out.println("Items in Inventory " + items.size());
        int itemY = frameY + 20;
        for (Item item : items) {
            myGraphics2D.drawString(item.getName(), frameX + 10, itemY);
            myGraphics2D.drawString(item.getMyDescription(), frameX + 10, itemY + 15);
            itemY += 40;
        }
    }

    public void toggleInventoryScreen() {
        bagOn = !bagOn;
    }

    private class BagMouseAdapter extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            bagHovered = bagBounds.contains(e.getPoint());
            mapHovered = mapBounds.contains(e.getPoint());
            myGamePanel.repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (mapHovered) {
                toggleMapScreen();
            }
            if (bagHovered) {
                toggleInventoryScreen();
            }
        }
    }

    public void drawSubWindow(final int theX, final int theY, final int theWidth, final int theHeight) {
        Color color = new Color(0, 0, 0, 210);
        myGraphics2D.setColor(color);
        myGraphics2D.fillRoundRect(theX, theY, theWidth, theHeight, 35, 35);

        color = new Color(255, 255, 255);
        myGraphics2D.setColor(color);
        myGraphics2D.setStroke(new BasicStroke(5));
        myGraphics2D.drawRoundRect(theX + 5, theY + 5, theWidth - 10, theHeight - 10, 25, 25);

    }
}
