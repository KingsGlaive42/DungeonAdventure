package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {
    private final int UP = KeyEvent.VK_W;
    private final int DOWN = KeyEvent.VK_S;
    private final int LEFT = KeyEvent.VK_A;
    private final int RIGHT = KeyEvent.VK_D;
    private final int PAUSE = KeyEvent.VK_ESCAPE;
    private final int INVENTORY = KeyEvent.VK_B;
    private final int MAP = KeyEvent.VK_M;

    private boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isPausedPressed, isInventoryPressed, isMapPressed;
    private boolean wasPausePressed = false;
    private boolean wasInventoryPressed = false;
    private boolean wasMapPressed = false;
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
        if (keyCode == PAUSE) {
            isPausedPressed = true;
        }
        if (keyCode == INVENTORY) {
            isInventoryPressed = true;
        }
        if (keyCode == MAP) {
            isMapPressed = true;
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
        if (keyCode == PAUSE) {
            isPausedPressed = false;
        }
        if (keyCode == INVENTORY) {
            isInventoryPressed = false;
        }
        if (keyCode == MAP) {
            isMapPressed = false;
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

    public boolean isInventoryJustPressed() {
        if (isInventoryPressed && !wasInventoryPressed) {
            wasInventoryPressed = true;
            return true;
        }
        if (!isInventoryPressed) {
            wasInventoryPressed = false;
        }
        return false;
    }

    public boolean isMapJustPressed() {
        if (isMapPressed && !wasMapPressed) {
            wasMapPressed = true;
            return true;
        }
        if (!isMapPressed) {
            wasMapPressed = false;
        }
        return false;
    }
}