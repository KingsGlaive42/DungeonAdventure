package view;

import controller.CombatController;
import controller.GameController;
import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import model.SaveGame.GameLoader;
import model.SaveGame.GameState;
import model.SaveGame.SaveFileManager;
import model.GameConfig;

import java.awt.*;
import java.io.File;

/**
 * This is the LoadGameScreen method
 *
 * @author Jayden Fausto
 */
public class LoadGameScreen implements Screen{
    private final GameStateManager myGameStateManager;
    private final SaveFileManager mySaveFileManager;
    private final GameController myGameController;
    private final CombatController myCombatController;

    private final UIButton myBackButton;
    private final UIButton[] saveSlotButtons;
    private final String[] saveSlotDetails;

    /**
     * LoadGameScreen Constructor
     *
     * @param theAssetManager AssetManager.
     * @param theGameStateManager GameStateManager.
     * @param theSaveFileManager SaveFileManager.
     * @param theGameController GameController.
     * @param theCombatController CombatController.
     */
    public LoadGameScreen(final AssetManager theAssetManager, final GameStateManager theGameStateManager, final SaveFileManager theSaveFileManager, final GameController theGameController, final CombatController theCombatController) {
        this.myGameStateManager = theGameStateManager;
        this.mySaveFileManager = theSaveFileManager;
        this.myGameController = theGameController;
        this.myCombatController = theCombatController;

        saveSlotButtons = new UIButton[3];
        for (int i = 0; i < 3; i++) {
            saveSlotButtons[i] = new UIButton(
                    theAssetManager.getAsset("slotButton"),
                    new Rectangle(100, 50 + i * 100, 350, 75)
            );
        }

        this.myBackButton = new UIButton(theAssetManager.getAsset("backButton"), new Rectangle(10, 10, 50, 50));
        this.saveSlotDetails = new String[3];
        loadSaveSlotDetails();
    }

    /**
     * THis method loads save files in their slots.
     */
    public void loadSaveSlotDetails() {
        for (int i = 0; i < 3; i++) {
            File saveFile = mySaveFileManager.getSaveFile(i + 1);
            if (saveFile.exists()) {
                GameState gameState = GameLoader.loadGame(saveFile.getPath());
                if (gameState != null) {
                    saveSlotDetails[i] = String.format(
                            "Player: %s, Last Save: %s",
                            gameState.getMyPlayer().getName(),
                            getSaveFileDetails(saveFile)
                    );
                } else {
                    saveSlotDetails[i] = "Corrupted Save File";
                }
            } else {
                saveSlotDetails[i] = "Empty Slot";
            }
        }
    }

    /**
     * Helper method that gets save file details
     *
     * @param theSaveFile the Save File
     * @return String representation of save file.
     */
    private String getSaveFileDetails(final File theSaveFile) {
        long lastModified = theSaveFile.lastModified();
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(lastModified));
    }

    /**
     * This method draws the screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.BLACK);
        theGraphics2D.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        int slotY = 50;
        for (int i = 0; i < saveSlotButtons.length; i++) {
            drawSubWindow(slotY, i, theGraphics2D);

            theGraphics2D.setColor(Color.WHITE);
            theGraphics2D.setFont(new Font("Arial", Font.PLAIN, 12));
            theGraphics2D.drawString(saveSlotDetails[i], 110, slotY + 40);

            slotY += 100;
        }

        myBackButton.draw(theGraphics2D);
    }

    /**
     * This method handles mouse hovering.
     *
     * @param theMousePoint Hovering point.
     */
    public void handleHoverUpdate(final Point theMousePoint) {
        for (UIButton slotButton : saveSlotButtons) {
            slotButton.setHovered(slotButton.contains(theMousePoint));
        }
        myBackButton.setHovered(myBackButton.contains(theMousePoint));
    }

    /**
     * This method handles mouse clicks
     *
     * @param theClickPoint Click point.
     */
    public void handleClick(final Point theClickPoint) {
        for (int i = 0; i < saveSlotButtons.length; i++) {
            if (saveSlotButtons[i].contains(theClickPoint)) {
                loadGame(i + 1);
                return;
            }
        }

        if (myBackButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.getPreviousState());
        }
    }

    /**
     * This method loads a game.
     *
     * @param theSlotNumber Slot save data is in.
     */
    private void loadGame(final int theSlotNumber) {
        File saveFile = mySaveFileManager.getSaveFile(theSlotNumber);
        if (saveFile.exists()) {
            GameState loadedState = GameLoader.loadGame(saveFile.getPath());
            if (loadedState != null) {
                myGameController.setMyPlayer(loadedState.getMyPlayer());
                myGameController.setMyDungeon(loadedState.getMyDungeon());
                myGameController.setMyInventory(loadedState.getMyInventory());
                myGameStateManager.setState(GameStateManager.State.GAME);
                myCombatController.setHero((loadedState.getMyPlayer().getHeroClass()));
            } else {
                System.err.println("Failed to load save file: " + saveFile.getName());
            }
        } else {
            System.out.println("Save file does not exist for slot " + theSlotNumber);
        }
    }

    /**
     * This method draws the window.
     *
     * @param theY Y position of window.
     * @param theSlotIndex Slot Index.
     * @param theGraphics2D Graphics.
     */
    private void drawSubWindow(final int theY, final int theSlotIndex, final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(new Color(0, 0, 0, 210));
        theGraphics2D.fillRoundRect(100, theY, 350, 75, 35, 35);
        if (saveSlotButtons[theSlotIndex].isHovered()) {
            theGraphics2D.setColor(Color.MAGENTA);
        } else {
            theGraphics2D.setColor(Color.WHITE);
        }
        theGraphics2D.setStroke(new BasicStroke(5));
        theGraphics2D.drawRoundRect(100 + 5, theY + 5, 350 - 10, 75 - 10, 25, 25);
    }
}
