package view;

import controller.InputListener;
import model.DungeonManager.Dungeon;
import model.DungeonManager.Room;
import model.Player.Player;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    private final int ORIGINAL_TILE_SIZE = 32; // original art tile size
    private final double PIXEL_SCALAR = 1;

    private final int TILE_SIZE = (int) (ORIGINAL_TILE_SIZE * PIXEL_SCALAR);
    private final int MAX_SCREEN_COLUMNS = 17;
    private final int MAX_SCREEN_ROWS = 13;
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMNS;
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    private final int NANO_IN_SECONDS = 1000000000;
    private final int FPS = 30;
    private final double DRAW_INTERVAL = (double) NANO_IN_SECONDS / FPS;

    private final int NUMBER_OF_ROOMS = 20;

    private final InputListener myInputListener = new InputListener();
    private Thread myGameThread; // game clock
    private final Player myPlayer;
    private final Dungeon myDungeon;
    private Room myCurrentRoom;

    private BufferedImage bagIcon;
    private Rectangle bagBounds;
    private boolean bagHovered = false;

    private BufferedImage mapIcon;
    private Rectangle mapBounds;
    private boolean mapHovered;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(myInputListener);
        this.setFocusable(true);

        myPlayer = new Player(this, myInputListener, "Warrior", "Warrior");
        myDungeon = new Dungeon(MAX_SCREEN_COLUMNS, MAX_SCREEN_ROWS, NUMBER_OF_ROOMS);

        loadBagIcon();
        loadMapIcon();
        addMouseListener(new BagMouseAdapter());
        addMouseMotionListener(new BagMouseAdapter());
    }

    public void startGameThread() {
        myGameThread = new Thread(this);
        myGameThread.start();
    }

    @Override
    public void run() {
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while (myGameThread != null) {
            currentTime = System.nanoTime();

            deltaTime += (currentTime - lastTime) / DRAW_INTERVAL;
            lastTime = currentTime;

            if (deltaTime >= 1) {
                update();

                repaint();

                deltaTime--;
            }
        }
    }

    public void update() {
        // update game info here
        myPlayer.update();
        myDungeon.checkDoorCollisions(myPlayer);
    }

    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);

        Graphics2D graphics2D = (Graphics2D) theGraphics;

        //Draw objects here
        myDungeon.getMyCurrentRoom().draw(graphics2D);
        myPlayer.draw(graphics2D);
        drawBagIcon(graphics2D);
        drawMapIcon(graphics2D);


        graphics2D.dispose();
    }

    private void loadBagIcon() {
        try {
            bagIcon = ImageIO.read(new File("src/resources/assets/Potato_seeds.png"));
            bagBounds = new Rectangle(SCREEN_WIDTH - 66, SCREEN_HEIGHT - 66, 48, 48); // Position & size
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawBagIcon(final Graphics2D theGraphics2D) {
        if (bagIcon != null) {
            float alpha = bagHovered ? 1f : 0.5f; // Adjust alpha for hover effect
            Composite originalComposite = theGraphics2D.getComposite();
            theGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            theGraphics2D.drawImage(bagIcon, SCREEN_WIDTH - 66, SCREEN_HEIGHT - 66, 48, 48, null);
            theGraphics2D.setComposite(originalComposite);
        }
    }

    private void loadMapIcon() {
        try {
            mapIcon = ImageIO.read(new File("src/resources/assets/map-icon.png"));
            mapBounds = new Rectangle(SCREEN_WIDTH - 132, SCREEN_HEIGHT - 72, 60, 60); // Position & size
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawMapIcon(final Graphics2D theGraphics2D) {
        if (bagIcon != null) {
            float alpha = mapHovered ? 1f : 0.5f; // Adjust alpha for hover effect
            Composite originalComposite = theGraphics2D.getComposite();
            theGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            theGraphics2D.drawImage(mapIcon, SCREEN_WIDTH - 132, SCREEN_HEIGHT - 72, 60, 60, null);
            theGraphics2D.setComposite(originalComposite);
        }
    }

    private class BagMouseAdapter extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            bagHovered = bagBounds.contains(e.getPoint());
            mapHovered = mapBounds.contains(e.getPoint());
            repaint();
        }
    }

    public int getTileSize() { return TILE_SIZE; }

    public int getMaxScreenColumns() {
        return MAX_SCREEN_COLUMNS;
    }

    public int getMaxScreenRows() {
        return MAX_SCREEN_ROWS;
    }

    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }
}
