package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {
    private final int UP = KeyEvent.VK_W;
    private final int DOWN = KeyEvent.VK_S;
    private final int LEFT = KeyEvent.VK_A;
    private final int RIGHT = KeyEvent.VK_D;

    private boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isPausedPressed;
    private boolean wasPausePressed = false;
    @Override
    public void keyTyped(final KeyEvent theKey) {

    }

    @Override
    public void keyPressed(final KeyEvent theKey) {
        int keyCode = theKey.getKeyCode();

        if (keyCode == UP) {
            isUpPressed = true;
        }
        if (keyCode == DOWN) {
            isDownPressed = true;
        }
        if (keyCode == LEFT) {
            isLeftPressed = true;
        }
        if (keyCode == RIGHT) {
            isRightPressed = true;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            isPausedPressed = true;
        }
    }

    @Override
    public void keyReleased(final KeyEvent theKey) {
        int keyCode = theKey.getKeyCode();

        if (keyCode == UP) {
            isUpPressed = false;
        }
        if (keyCode == DOWN) {
            isDownPressed = false;
        }
        if (keyCode == LEFT) {
            isLeftPressed = false;
        }
        if (keyCode == RIGHT) {
            isRightPressed = false;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            isPausedPressed = false;
        }
    }

    public boolean isUpPressed() {
        return isUpPressed;
    }

    public boolean isDownPressed() {
        return isDownPressed;
    }

    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    public boolean isRightPressed() {
        return isRightPressed;
    }

    public boolean isPauseJustPressed() {
        if (isPausedPressed && !wasPausePressed) {
            wasPausePressed = true;
            return true;
        }
        if (!isPausedPressed) {
            wasPausePressed = false;
        }
        return false;
    }
}