package controller;

import view.GameScreen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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


    public static InputListener getInstance() {
        if (myInstance == null) {
            myInstance = new InputListener();
        }

        return myInstance;
    }


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

    public boolean isArrowUpPressed() {
        return isArrowUpPressed;
    }

    public boolean isArrowDownPressed() {
        return isArrowDownPressed;
    }

    public boolean isArrowLeftPressed() {
        return isArrowLeftPressed;
    }

    public boolean isArrowRightPressed() {
        return isArrowRightPressed;
    }

    public boolean isUsePressed() {
        return isUsePressed;
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