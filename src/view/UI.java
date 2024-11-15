package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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

    public UI(final GamePanel theGamePanel) {
        this.myGamePanel = theGamePanel;

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
            float alpha;
            if (mapOn) {
                alpha = mapHovered ? 0.5f : 1f;
            } else {
                alpha = mapHovered ? 1f : 0.5f;
            }
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
            float alpha;
            if (bagOn) {
                alpha = bagHovered ? 0.5f : 1f;
            } else {
                alpha = bagHovered ? 1f : 0.5f;
            }

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
