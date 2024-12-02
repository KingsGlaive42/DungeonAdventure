package view;

import controller.GameController;
import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import model.SaveGame.GameLoader;
import model.SaveGame.GameState;
import model.SaveGame.SaveFileManager;
import utilities.GameConfig;

import java.awt.*;
import java.io.File;

import static utilities.GameConfig.TILE_SIZE;

public class LoadGameScreen {
    private final GameStateManager myGameStateManager;
    private final SaveFileManager mySaveFileManager;
    private final GameController myGameController;

    private final UIButton myBackButton;
    private final UIButton[] saveSlotButtons;
    private final String[] saveSlotDetails;

    private static final int slotButtonX = 100;


    public LoadGameScreen(final AssetManager theAssetManager, final GameStateManager theGameStateManager, final SaveFileManager theSaveFileManager, final GameController theGameController) {
        this.myGameStateManager = theGameStateManager;
        this.mySaveFileManager = theSaveFileManager;
        this.myGameController = theGameController;

        saveSlotButtons = new UIButton[3];
        for (int i = 0; i < 3; i++) {
            saveSlotButtons[i] = new UIButton(
                    theAssetManager.getAsset("slotButton"),
                    new Rectangle(100, 50 + i * 100, 300, 75)
            );
        }

        this.myBackButton = new UIButton(theAssetManager.getAsset("backButton"), new Rectangle(10, 10, 50, 50));
        this.saveSlotDetails = new String[3];
        loadSaveSlotDetails();

    }

    private void loadSaveSlotDetails() {
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

    private String getSaveFileDetails(final File theSaveFile) {
        long lastModified = theSaveFile.lastModified();
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(lastModified));
    }

    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.BLACK);
        theGraphics2D.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        int slotY = 50;
        for (int i = 0; i < saveSlotButtons.length; i++) {
            drawSubWindow(100, slotY, 300, 75, theGraphics2D);

            theGraphics2D.setColor(Color.WHITE);
            theGraphics2D.setFont(new Font("Arial", Font.PLAIN, 12));
            theGraphics2D.drawString(saveSlotDetails[i], 110, slotY + 40);

            slotY += 100;
        }

        myBackButton.draw(theGraphics2D);
    }

    public void handleHoverUpdate(final Point theMousePoint) {
        for (UIButton slotButton : saveSlotButtons) {
            slotButton.setHovered(slotButton.contains(theMousePoint));
        }
        myBackButton.setHovered(myBackButton.contains(theMousePoint));
    }

    public void handleClick(final Point theClickPoint) {
        for (int i = 0; i < saveSlotButtons.length; i++) {
            if (saveSlotButtons[i].contains(theClickPoint)) {
                loadGame(i + 1);
                return;
            }
        }

        if (myBackButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.MENU);
        }
    }

    private void loadGame(final int theSlotNumber) {
        File saveFile = mySaveFileManager.getSaveFile(theSlotNumber);
        if (saveFile.exists()) {
            GameState loadedState = GameLoader.loadGame(saveFile.getPath());
            if (loadedState != null) {
                myGameController.setMyPlayer(loadedState.getMyPlayer());
                myGameController.setMyDungeon(loadedState.getMyDungeon());
                //myGameController.setMyInventory(loadedState.getMyInventory());
                myGameStateManager.setState(GameStateManager.State.GAME);
            } else {
                System.err.println("Failed to load save file: " + saveFile.getName());
            }
        } else {
            System.out.println("Save file does not exist for slot " + theSlotNumber);
        }
    }

    private void drawSubWindow(final int theX, final int theY, final int theWidth, final int theHeight, final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(new Color(0, 0, 0, 210));
        theGraphics2D.fillRoundRect(theX, theY, theWidth, theHeight, 35, 35);
        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.setStroke(new BasicStroke(5));
        theGraphics2D.drawRoundRect(theX + 5, theY + 5, theWidth - 10, theHeight - 10, 25, 25);
    }
}
