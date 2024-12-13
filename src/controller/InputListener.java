package controller;

import view.GameScreen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This is the Input Listener class that listens to what keys the user presses.
 *
 * @author Jayden Fausto
 */
public class InputListener implements KeyListener{
    private final int UP = KeyEvent.VK_W;
    private final int DOWN = KeyEvent.VK_S;
    private final int LEFT = KeyEvent.VK_A;
    private final int RIGHT = KeyEvent.VK_D;
    private final int ARROW_UP = KeyEvent.VK_UP;
    private final int ARROW_DOWN = KeyEvent.VK_DOWN;
    private final int ARROW_LEFT = KeyEvent.VK_LEFT;
    private final int ARROW_RIGHT = KeyEvent.VK_RIGHT;
    private final int USE = KeyEvent.VK_E;
    private final int PAUSE = KeyEvent.VK_ESCAPE;

    private boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isPausedPressed;
    private boolean isArrowUpPressed, isArrowDownPressed, isArrowLeftPressed, isArrowRightPressed;
    private boolean isUsePressed;
    private boolean wasPausePressed = false;

    private boolean isTypingName = false;
    private final StringBuilder typedName = new StringBuilder();

    private static InputListener myInstance;

    /**
     * This method returns the instance of InputListener.
     *
     * @return Instance of Input Listener.
     */
    public static InputListener getInstance() {
        if (myInstance == null) {
            myInstance = new InputListener();
        }

        return myInstance;
    }


    /**
     * This method adds name player types to game.
     *
     * @param theKey the event to be processed
     */
    @Override
    public void keyTyped(final KeyEvent theKey) {
        if (isTypingName) {
            char keyChar = theKey.getKeyChar();

            if (Character.isLetterOrDigit(keyChar) || keyChar == ' ') {
                    typedName.append(keyChar);
            } else if (keyChar == '\b' && !typedName.isEmpty()) {
                    typedName.deleteCharAt(typedName.length() - 1);
            }
        }
    }

    /**
     * This method checks which of the movement/selection
     * keys were pressed.
     *
     * @param theKey the event to be processed
     */
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
        if (keyCode == ARROW_UP) {
            isArrowUpPressed = true;
        }
        if (keyCode == ARROW_DOWN) {
            isArrowDownPressed = true;
        }
        if (keyCode == ARROW_LEFT) {
            isArrowLeftPressed = true;
        }
        if (keyCode == ARROW_RIGHT) {
            isArrowRightPressed = true;
        }
        if (keyCode == USE) {
            isUsePressed = true;
        }
        if (keyCode == PAUSE) {
            isPausedPressed = true;
        }
    }

    /**
     * This method checks which of the movement/selection
     * keys were released.
     *
     * @param theKey the event to be processed
     */
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
        if (keyCode == ARROW_UP) {
            isArrowUpPressed = false;
        }
        if (keyCode == ARROW_DOWN) {
            isArrowDownPressed = false;
        }
        if (keyCode == ARROW_LEFT) {
            isArrowLeftPressed = false;
        }
        if (keyCode == ARROW_RIGHT) {
            isArrowRightPressed = false;
        }
        if (keyCode == USE) {
            isUsePressed = false;
        }
        if (keyCode == PAUSE) {
            isPausedPressed = false;
        }
    }

    /**
     * This method checks if up was pressed.
     *
     * @return True if pressed, false if not.
     */
    public boolean isUpPressed() {
        return isUpPressed;
    }

    /**
     * This method checks if down was pressed.
     *
     * @return True if pressed, false if not.
     */
    public boolean isDownPressed() {
        return isDownPressed;
    }

    /**
     * This method checks if left was pressed.
     *
     * @return True if pressed, false if not.
     */
    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    /**
     * This method checks if right was pressed.
     *
     * @return True if pressed, false if not.
     */
    public boolean isRightPressed() {
        return isRightPressed;
    }

    /**
     * This method checks if arrowUp was pressed.
     *
     * @return True if pressed, false if not.
     */
    public boolean isArrowUpPressed() {
        return isArrowUpPressed;
    }

    /**
     * This method checks if arrowDown was pressed.
     *
     * @return True if pressed, false if not.
     */
    public boolean isArrowDownPressed() {
        return isArrowDownPressed;
    }

    /**
     * This method checks if arrowLeft was pressed.
     *
     * @return True if pressed, false if not.
     */
    public boolean isArrowLeftPressed() {
        return isArrowLeftPressed;
    }

    /**
     * This method checks if arrowRight was pressed.
     *
     * @return True if pressed, false if not.
     */
    public boolean isArrowRightPressed() {
        return isArrowRightPressed;
    }

    /**
     * This method checks if use was pressed.
     *
     * @return True if pressed, false if not.
     */
    public boolean isUsePressed() {
        return isUsePressed;
    }

    /**
     * This method checks if pause was pressed.
     *
     * @return True if pressed, false if not.
     */
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

    /**
     * This method resets all the button pressed booleans.
     */
    public void reset() {
        isUpPressed = false;
        isDownPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
        isArrowUpPressed = false;
        isArrowDownPressed = false;
        isArrowLeftPressed = false;
        isArrowRightPressed = false;
        isUsePressed = false;
        isPausedPressed = false;
        wasPausePressed = false;
    }

    /**
     * This method sets if left was pressed.
     *
     * @param isPressed true if pressed, false if not.
     */
    public void setLeftPressed(final boolean isPressed) {
        this.isLeftPressed = isPressed;
    }

    public void setTypingName(final boolean isTyping) {
        isTypingName = isTyping;
    }

    public String getTypedName() {
        return typedName.toString();
    }

    public void clearTypedName() {
        typedName.setLength(0);
    }
}