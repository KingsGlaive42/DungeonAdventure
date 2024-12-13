package view;

import controller.CombatController;
import controller.GameController;
import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import model.Player.Player;
import model.SaveGame.SaveFileManager;

import java.awt.*;

/**
 * This is the UI class
 *
 * @author Jayden Fausto
 */
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

    /**
     * UI Constructor
     *
     * @param theGameStateManager GameStateManager.
     * @param theAssetManager AssetManager.
     * @param theSaveFileManager SaveFileManager.
     * @param theGameController GameController.
     * @param theCombatController CombatController.
     */
    public UI(final GameStateManager theGameStateManager, final AssetManager theAssetManager, final SaveFileManager theSaveFileManager, final GameController theGameController, final CombatController theCombatController) {
        this.myGameStateManager = theGameStateManager;

        this.myTitleScreen = new TitleScreen(theAssetManager, theGameStateManager);
        this.myGameScreen = new GameScreen(theAssetManager, theGameController);
        this.myPauseScreen = new PauseScreen(theAssetManager, theGameStateManager);
        this.myLoadGameScreen = new LoadGameScreen(theAssetManager, theGameStateManager, theSaveFileManager, theGameController, theCombatController);
        this.mySaveGameScreen = new SaveGameScreen(theAssetManager, theGameStateManager, theSaveFileManager, theGameController);
        this.myOptionScreen = new OptionScreen(theGameStateManager, theAssetManager);
        this.myGameCreateScreen = new GameCreateScreen(this, theAssetManager, theGameController, theGameStateManager);
        this.myWaitScreen = new LoadingScreen(theGameController, theGameStateManager, theCombatController);
    }

    /**
     * This method draws the title screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void drawTitleScreen(final Graphics2D theGraphics2D) {
        myTitleScreen.draw(theGraphics2D);
    }

    /**
     * This method draws the gameHUD.
     *
     * @param theGraphics2D Graphics.
     */
    public void drawGameHUD(final Graphics2D theGraphics2D) {
        myGameScreen.draw(theGraphics2D);
    }

    /**
     * This method draws the pause screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void drawPauseScreen(final Graphics2D theGraphics2D) {
        myPauseScreen.draw(theGraphics2D);
    }

    /**
     * This method draws the load screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void drawLoadScreen(final Graphics2D theGraphics2D) {
        myLoadGameScreen.draw(theGraphics2D);
    }

    /**
     * This method draws the save screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void drawSaveScreen(final Graphics2D theGraphics2D) {
        mySaveGameScreen.draw(theGraphics2D);
    }

    /**
     * This method draws the option screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void drawOptionScreen(final Graphics2D theGraphics2D) {
        myOptionScreen.draw(theGraphics2D);
    }

    /**
     * This method draws the create screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void drawGameCreateScreen(final Graphics2D theGraphics2D) {
        myGameCreateScreen.draw(theGraphics2D);
    }

    /**
     * This method draws the loading screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void drawLoadingGameScreen(final Graphics2D theGraphics2D) {
        if (myWaitScreen.isLoading()) {
            myWaitScreen.draw(theGraphics2D);
        }
    }

    /**
     * This method updates hover states.
     *
     * @param theMousePoint Hovering point.
     */
    public void updateHoverStates(final Point theMousePoint) {
        switch (myGameStateManager.getCurrentState()) {
            case MENU -> myTitleScreen.handleHoverUpdate(theMousePoint);
            case GAME -> myGameScreen.handleHoverUpdate(theMousePoint);
            case PAUSE -> myPauseScreen.handleHoverUpdate(theMousePoint);
            case LOAD -> myLoadGameScreen.handleHoverUpdate(theMousePoint);
            case SAVE -> mySaveGameScreen.handleHoverUpdate(theMousePoint);
            case GAME_CREATE -> myGameCreateScreen.handleHoverUpdate(theMousePoint);
            case OPTION -> myOptionScreen.handleHoverUpdate(theMousePoint);
        }
    }

    /**
     * This method handles clicking.
     *
     * @param theClickPoint Click point.
     */
    public void handleClicks(final Point theClickPoint) {
        switch (myGameStateManager.getCurrentState()) {
            case MENU -> myTitleScreen.handleClick(theClickPoint);
            case GAME -> myGameScreen.handleClick(theClickPoint);
            case PAUSE -> myPauseScreen.handleClick(theClickPoint);
            case LOAD -> myLoadGameScreen.handleClick(theClickPoint);
            case SAVE -> mySaveGameScreen.handleClick(theClickPoint);
            case GAME_CREATE -> myGameCreateScreen.handleClick(theClickPoint);
            case OPTION -> myOptionScreen.handleClick(theClickPoint);
        }
    }

    /**
     * This method handles key presses.
     *
     * @param theKeyCode Key Code.
     * @param theKeyChar Key Character.
     */
    public void handleKeyPress(final int theKeyCode, final char theKeyChar) {
        if (myGameStateManager.getCurrentState() == GameStateManager.State.GAME_CREATE) {
            myGameCreateScreen.handleKeyPress(theKeyCode, theKeyChar);
        }
    }

    /**
     * This method loads a game.
     *
     * @param thePlayer Player.
     * @param theDungeonWidth Dungeon Width.
     * @param theDungeonHeight Dungeon Height.
     * @param theNumRooms Number of Rooms.
     */
    public void loadGame(final Player thePlayer, final int theDungeonWidth, final int theDungeonHeight, final int theNumRooms) {
        myGameStateManager.setState(GameStateManager.State.LOADING_GAME);

        myWaitScreen.loadGame(thePlayer, theDungeonWidth, theDungeonHeight, theNumRooms);
    }

    /**
     * This method gets Game Screen.
     *
     * @return Game Screen.
     */
    public GameScreen getGameScreen() {
        return myGameScreen;
    }

    /**
     * This method loads save slot info.
     */
    public void loadSlotInfo() {
        myLoadGameScreen.loadSaveSlotDetails();
    }
}
