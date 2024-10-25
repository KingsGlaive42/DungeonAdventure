package view;

import controller.InputListener;
import model.Player.Player;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final int ORIGINAL_TILE_SIZE = 32; // original art tile size
    private final double PIXEL_SCALAR = 1;

    private final int TILE_SIZE = (int) (ORIGINAL_TILE_SIZE * PIXEL_SCALAR);

    private final int MAX_SCREEN_COLUMNS = 16;
    private final int MAX_SCREEN_ROWS = 12;
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMNS;
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    private final int NANO_IN_SECONDS = 1000000000;
    private final int FPS = 30;
    private final double DRAW_INTERVAL = (double) NANO_IN_SECONDS / FPS;

    private final InputListener myInputListener = new InputListener();
    private Thread myGameThread; // game clock
    private final Player player = new Player(this, myInputListener, "Warrior", "Warrior");

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(myInputListener);
        this.setFocusable(true);
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
        player.update();
    }

    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);

        Graphics2D graphics2D = (Graphics2D) theGraphics;

        //Draw objects here
        player.draw(graphics2D);

        graphics2D.dispose();
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
