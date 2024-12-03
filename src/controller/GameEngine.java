package controller;

import utilities.GameConfig;
import view.GamePanel;

public class GameEngine implements Runnable {
    private final GamePanel myGamePanel;
    private final GameStateManager myGameStateManager;

    private Thread myGameThread;
    private boolean isRunning;

    public GameEngine(final GamePanel theGamePanel, final GameStateManager theGameStateManager) {
        myGamePanel = theGamePanel;
        myGameStateManager = theGameStateManager;
    }

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
    
    private void update() {
        myGameStateManager.update();
    }
}
