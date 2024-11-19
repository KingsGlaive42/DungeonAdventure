package view;

import controller.InputListener;
import model.DungeonManager.Dungeon;
import model.DungeonManager.Room;
import model.Player.Player;
import model.PlayerInventory.Inventory;

import javax.swing.JPanel;
import java.awt.*;

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
    private final UI myUI;

    enum State {
        GAME_STATE,
        PAUSE_STATE,
        MENU_STATE,
        COMBAT_STATE
    }

    private State myState;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(myInputListener);
        this.setFocusable(true);

        myDungeon = new Dungeon(MAX_SCREEN_COLUMNS, MAX_SCREEN_ROWS, NUMBER_OF_ROOMS);
        Inventory myInventory = new Inventory(myDungeon);
        myPlayer = new Player(this, myInputListener, "Warrior", "Warrior", myInventory);
        myUI = new UI(this, myInventory);
        myState = State.GAME_STATE;
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
        switch (myState) {
            case State.MENU_STATE:
                break;
            case State.GAME_STATE:
                updateGameStateInfo();
                break;
            case State.COMBAT_STATE:
                break;
            case State.PAUSE_STATE:
                updatePauseStateInfo();
                break;

        }
    }

    private void updateGameStateInfo() {
        myPlayer.update();
        myDungeon.checkDoorCollisions(myPlayer);
        if (myInputListener.isPauseJustPressed()) {
            setState(State.PAUSE_STATE);
        }

        if (myInputListener.isInventoryJustPressed()) {
            myUI.toggleInventoryScreen();
        }

        if (myInputListener.isMapJustPressed()) {
            myUI.toggleMapScreen();
        }
    }

    private void updatePauseStateInfo() {
        if (myInputListener.isPauseJustPressed()) {
            setState(State.GAME_STATE);
        }
    }

    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);

        Graphics2D graphics2D = (Graphics2D) theGraphics;


        switch (myState) {
            case MENU_STATE:
                break;
            case GAME_STATE:
                paintGameState(graphics2D);
                break;
            case COMBAT_STATE:
                break;
            case PAUSE_STATE:
                paintPauseState(graphics2D);
                break;

        }

        graphics2D.dispose();
    }

    private void paintGameState(final Graphics2D graphics2D) {
        myDungeon.getMyCurrentRoom().draw(graphics2D);
        myPlayer.draw(graphics2D);
        myUI.draw(graphics2D);
    }

    private void paintPauseState(final Graphics2D graphics2D) {
        paintGameState(graphics2D);
        myUI.draw(graphics2D);
    }

    public void setState(final State theState) { myState = theState; }
    public State getState() { return myState; }

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

    public InputListener getInputListener() {
        return myInputListener;
    }
}
