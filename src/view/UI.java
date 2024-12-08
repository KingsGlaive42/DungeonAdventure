package view;

import controller.GameController;
import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import model.PlayerInventory.Inventory;
import model.SaveGame.SaveFileManager;

import java.awt.*;

public class UI {

    private final GameStateManager myGameStateManager;

    private final TitleScreen myTitleScreen;
    private final GameScreen myGameScreen;
    private final PauseScreen myPauseScreen;
    private final LoadGameScreen myLoadGameScreen;
    private final SaveGameScreen mySaveGameScreen;

    public UI(final GameStateManager theGameStateManager, final Inventory theInventory, final AssetManager theAssetManager, final SaveFileManager theSaveFileManager, final GameController theGameController) {
        this.myGameStateManager = theGameStateManager;

        this.myTitleScreen = new TitleScreen(theAssetManager, theGameStateManager);
        this.myGameScreen = new GameScreen(theAssetManager, theGameController);
        this.myPauseScreen = new PauseScreen(theAssetManager, theGameStateManager);
        this.myLoadGameScreen = new LoadGameScreen(theAssetManager, theGameStateManager, theSaveFileManager, theGameController);
        this.mySaveGameScreen = new SaveGameScreen(theAssetManager, theGameStateManager, theSaveFileManager, theGameController);
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

    public void updateHoverStates(final Point theMousePoint) {
        switch (myGameStateManager.getCurrentState()) {
            case GameStateManager.State.MENU -> myTitleScreen.handleHoverUpdate(theMousePoint);
            case GameStateManager.State.GAME -> myGameScreen.handleHoverUpdate(theMousePoint);
            case GameStateManager.State.PAUSE -> myPauseScreen.handleHoverUpdate(theMousePoint);
            case GameStateManager.State.LOAD -> myLoadGameScreen.handleHoverUpdate(theMousePoint);
            case GameStateManager.State.SAVE -> mySaveGameScreen.handleHoverUpdate(theMousePoint);
        }
    }

    public void handleClicks(final Point theClickPoint) {
        switch (myGameStateManager.getCurrentState()) {
            case GameStateManager.State.MENU -> myTitleScreen.handleClick(theClickPoint);
            case GameStateManager.State.GAME -> myGameScreen.handleClick(theClickPoint);
            case GameStateManager.State.PAUSE -> myPauseScreen.handleClick(theClickPoint);
            case GameStateManager.State.LOAD -> myLoadGameScreen.handleClick(theClickPoint);
            case GameStateManager.State.SAVE -> mySaveGameScreen.handleClick(theClickPoint);
        }
    }

    public GameScreen getGameScreen() {
        return myGameScreen;
    }
}
