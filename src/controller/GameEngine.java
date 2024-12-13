package controller;

import model.GameConfig;
import view.GamePanel;

import java.awt.*;

/**
 * This is the GameEngine Class
 *
 * @author Jayden Fausto
 */
public class GameEngine implements Runnable {
    private final GamePanel myGamePanel;
    private final GameStateManager myGameStateManager;

    private Thread myGameThread;
    private boolean isRunning;

    /**
     * GameEngine Constructor.
     *
     * @param theGamePanel GamePanel.
     * @param theGameStateManager GameStateManager.
     */
    public GameEngine(final GamePanel theGamePanel, final GameStateManager theGameStateManager) {
        myGamePanel = theGamePanel;
        myGameStateManager = theGameStateManager;
    }

    /**
     * This method starts the game.
     */
    public void startGame() {
        if (myGameThread == null) {
            isRunning = true;
            myGameThread = new Thread(this);
            myGameThread.start();
        }
    }

    public void stopGame() {
        isRunning = false;
        try {
            if (myGameThread != null) {
                myGameThread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method runs the game.
     */
    @Override
    public void run() {
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (isRunning) {
            currentTime = System.nanoTime();
            deltaTime += (currentTime - lastTime) / GameConfig.DRAW_INTERVAL;
            lastTime = currentTime;

            if (deltaTime >= 1) {
                update();
                myGamePanel.repaint();
                deltaTime--;
            }
        }
    }

    /**
     * This method updates game state.
     */
    private void update() {
        myGameStateManager.update();
    }
}
