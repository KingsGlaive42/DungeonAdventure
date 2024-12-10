package view;

import controller.GameController;
import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import model.Player.Player;
import model.SaveGame.SaveFileManager;

import java.awt.*;

public class UI {

    private final GameStateManager myGameStateManager;

    private final TitleScreen myTitleScreen;
    private final GameScreen myGameScreen;
    private final PauseScreen myPauseScreen;
    private final LoadGameScreen myLoadGameScreen;
    private final SaveGameScreen mySaveGameScreen;
    private final OptionScreen myOptionScreen;
    private final GameCreateScreen myGameCreateScreen;
    private final LoadingScreen myWaitScreen;

    public UI(final GameStateManager theGameStateManager, final AssetManager theAssetManager, final SaveFileManager theSaveFileManager, final GameController theGameController) {
        this.myGameStateManager = theGameStateManager;

        this.myTitleScreen = new TitleScreen(theAssetManager, theGameStateManager);
        this.myGameScreen = new GameScreen(theAssetManager, theGameController);
        this.myPauseScreen = new PauseScreen(theAssetManager, theGameStateManager);
        this.myLoadGameScreen = new LoadGameScreen(theAssetManager, theGameStateManager, theSaveFileManager, theGameController);
        this.mySaveGameScreen = new SaveGameScreen(theAssetManager, theGameStateManager, theSaveFileManager, theGameController);
        this.myOptionScreen = new OptionScreen();
        this.myGameCreateScreen = new GameCreateScreen(this, theAssetManager, theGameController, theGameStateManager);
        this.myWaitScreen = new LoadingScreen(theGameController, theGameStateManager);
    }

    public void drawTitleScreen(final Graphics2D theGraphics2D) {
        myTitleScreen.draw(theGraphics2D);
    }

    public void drawGameHUD(final Graphics2D theGraphics2D) {
        myGameScreen.draw(theGraphics2D);
    }

    public void drawPauseScreen(final Graphics2D theGraphics2D) {
        myPauseScreen.draw(theGraphics2D);
    }

    public void drawLoadScreen(final Graphics2D theGraphics2D) {
        myLoadGameScreen.draw(theGraphics2D);
    }

    public void drawSaveScreen(final Graphics2D theGraphics2D) {
        mySaveGameScreen.draw(theGraphics2D);
    }

    public void drawOptionScreen(final Graphics2D theGraphics2D) {

    }

    public void drawGameCreateScreen(final Graphics2D theGraphics2D) {
        myGameCreateScreen.draw(theGraphics2D);
    }

    public void drawLoadingGameScreen(final Graphics2D theGraphics2D) {
        if (myWaitScreen.isLoading()) {
            myWaitScreen.draw(theGraphics2D);
        }
    }

    public void updateHoverStates(final Point theMousePoint) {
        switch (myGameStateManager.getCurrentState()) {
            case MENU -> myTitleScreen.handleHoverUpdate(theMousePoint);
            case GAME -> myGameScreen.handleHoverUpdate(theMousePoint);
            case PAUSE -> myPauseScreen.handleHoverUpdate(theMousePoint);
            case LOAD -> myLoadGameScreen.handleHoverUpdate(theMousePoint);
            case SAVE -> mySaveGameScreen.handleHoverUpdate(theMousePoint);
            case GAME_CREATE -> myGameCreateScreen.handleHoverUpdate(theMousePoint);
        }
    }

    public void handleClicks(final Point theClickPoint) {
        switch (myGameStateManager.getCurrentState()) {
            case MENU -> myTitleScreen.handleClick(theClickPoint);
            case GAME -> myGameScreen.handleClick(theClickPoint);
            case PAUSE -> myPauseScreen.handleClick(theClickPoint);
            case LOAD -> myLoadGameScreen.handleClick(theClickPoint);
            case SAVE -> mySaveGameScreen.handleClick(theClickPoint);
            case GAME_CREATE -> myGameCreateScreen.handleClick(theClickPoint);
        }
    }

    public void handleKeyPress(final int theKeyCode, final char theKeyChar) {
        if (myGameStateManager.getCurrentState() == GameStateManager.State.GAME_CREATE) {
            myGameCreateScreen.handleKeyPress(theKeyCode, theKeyChar);
        }
    }

    public void loadGame(final Player thePlayer, final int theDungeonWidth, final int theDungeonHeight, final int theNumRooms) {
        myGameStateManager.setState(GameStateManager.State.LOADING_GAME);

        myWaitScreen.loadGame(thePlayer, theDungeonWidth, theDungeonHeight, theNumRooms);
    }

    public GameScreen getGameScreen() {
        return myGameScreen;
    }

    public void loadSlotInfo() {
        myLoadGameScreen.loadSaveSlotDetails();
    }
}
