package controller;

import utilities.SoundManager;
import view.UI;

import java.awt.*;

public class GameStateManager {
    public enum State {
        MENU,
        GAME,
        PAUSE,
        COMBAT,
        OPTION
    }

    private final GameController myGameController;
    private UI myUI;

    private State myCurrentState;

    public GameStateManager(final GameController theGameController) {
        myGameController = theGameController;
        SoundManager mySoundManager = SoundManager.getInstance();
        myCurrentState = State.MENU;
    }

    public State getCurrentState() { return myCurrentState; }

    public void setState(final State theNewState) {
        if (theNewState != myCurrentState) {
            onExitState(myCurrentState);
            myCurrentState = theNewState;
            onEnterState(theNewState);
        }
    }

    private void onEnterState(final State theState) {

    }

    private void onExitState(final State theState) {

    }

    public void update() {
        switch (myCurrentState) {
            case MENU:
                break;
            case GAME:
                myGameController.update();
                break;
            case PAUSE:
                break;
            case COMBAT:
                break;
            case OPTION:
                break;
        }
    }

    public void paint(final Graphics2D theGraphics2D) {
        switch (myCurrentState) {
            case MENU:
                myUI.drawTitleScreen(theGraphics2D);
                break;
            case GAME:
                myGameController.draw(theGraphics2D);
                break;
            case PAUSE:
                myGameController.draw(theGraphics2D);
                myUI.drawPauseScreen(theGraphics2D);
                break;
            case COMBAT:
                break;
            case OPTION:
                break;
        }
    }

    public void setUI(final UI theUI) {
        myUI = theUI;
    }
}
