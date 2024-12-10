package controller;

import model.GameConfig;
import controller.SoundManager;
import view.GameScreen;
import model.GameConfig;
import view.UI;

import java.awt.*;

public class GameStateManager {
    public enum State {
        MENU,
        GAME,
        PAUSE,
        COMBAT,
        OPTION,
        LOAD,
        SAVE,
        GAME_CREATE,
        LOADING_GAME
    }

    private final GameController myGameController;
    private final transient SoundManager mySoundManager;
    private UI myUI;

    private static State myCurrentState;
    private static State myPreviousState;

    public GameStateManager(final GameController theGameController) {
        myGameController = theGameController;
        mySoundManager = SoundManager.getInstance();
        myCurrentState = State.MENU;
        onEnterState(State.MENU);
        myPreviousState = State.MENU;
    }

    public State getCurrentState() { return myCurrentState; }

    public void setState(final State theNewState) {
        if (theNewState != myCurrentState) {
            onExitState(myCurrentState);
            myPreviousState = myCurrentState;
            myCurrentState = theNewState;
            onEnterState(theNewState);
        }
    }

    private void onEnterState(final State theState) {
        switch (theState) {
            case MENU, OPTION:
                mySoundManager.playBackgroundMusic(GameConfig.MENU_THEME);
                break;
            case GAME:
                mySoundManager.playBackgroundMusic(GameConfig.GAME_THEME);
                break;
            case LOAD:
                myUI.loadSlotInfo();
                break;
            case SAVE, GAME_CREATE, PAUSE:
                break;
            case COMBAT:
                mySoundManager.playBackgroundMusic(GameConfig.COMBAT_THEME);
                break;
        }
    }

    private void onExitState(final State theState) {
        switch (theState) {
            case MENU, COMBAT:
                mySoundManager.stopBackgroundMusic();
                break;
            case GAME, LOAD, SAVE, PAUSE, OPTION, GAME_CREATE:
                break;
        }
    }

    public void update() {
        handleInput();

        switch (myCurrentState) {
            case MENU, PAUSE, OPTION, COMBAT:
                break;
            case GAME:
                myGameController.update();
                break;
        }
    }

    public void paint(final Graphics2D theGraphics2D) {
        if (myUI == null) {
            throw new IllegalStateException("UI not initialized");
        }

        switch (myCurrentState) {
            case MENU:
                myUI.drawTitleScreen(theGraphics2D);
                break;
            case LOAD:
                myUI.drawLoadScreen(theGraphics2D);
                break;
            case SAVE:
                myUI.drawSaveScreen(theGraphics2D);
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
                myUI.drawOptionScreen(theGraphics2D);
                break;
            case GAME_CREATE:
                myUI.drawGameCreateScreen(theGraphics2D);
                break;
            case LOADING_GAME:
                myUI.drawLoadingGameScreen(theGraphics2D);
                break;
        }
    }

    private void handleInput() {
        if (InputListener.getInstance().isPauseJustPressed()) {
            if (myCurrentState == State.PAUSE) {
                setState(State.GAME);
            } else if (myCurrentState == State.GAME) {
                setState(State.PAUSE);
            }
        }
    }

    public void setUI(final UI theUI) {
        myUI = theUI;
    }

    public static State getPreviousState() {
        return myPreviousState;
    }
}
