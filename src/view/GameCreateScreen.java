package view;

import controller.GameController;
import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import model.GameConfig;
import model.Player.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

import static model.GameConfig.CLASS_NAMES;
import static model.GameConfig.NUM_CHARACTERS;

/**
 * This is the GameCreateScreen class
 *
 * @author Jayden Fausto
 */
public class GameCreateScreen implements Screen{
    private final UI myUI;

    private UIButton[] myCharacterSelectButtons;
    private final UIButton myCreateGameButton;
    private final UIButton myBackButton;

    private final GameController myGameController;
    private final GameStateManager myGameStateManager;

    private boolean isCharacterSelected;
    private String mySelectedCharacter;

    private boolean isNameInputFocused = false;
    private String myPlayerName = "";
    private final Rectangle myNameInputFieldBounds = new Rectangle(50, 250, 200, 30);

    private int myDifficulty;
    private final UIButton myEasyDifficultyButton;
    private final UIButton myMediumDifficultyButton;
    private final UIButton myHardDifficultyButton;


    /**
     * GameCreateScreen Constructor
     *
     * @param theUI UI.
     * @param theAssetManager AssetManager.
     * @param theGameController GameController.
     * @param theGameStateManager GameStateManager.
     */
    public GameCreateScreen(final UI theUI, final AssetManager theAssetManager, final GameController theGameController, final GameStateManager theGameStateManager) {
        myUI = theUI;

        this.myGameController = theGameController;
        this.myGameStateManager = theGameStateManager;
        createCharacterButtons(theAssetManager);

        myCreateGameButton = new UIButton(theAssetManager.getAsset("createGameButton"), new Rectangle(450, 325, 70, 70));
        myBackButton = new UIButton(theAssetManager.getAsset("backButton"), new Rectangle(50, 325, 70, 70));

        myEasyDifficultyButton = new UIButton(theAssetManager.getAsset("easyButton"), new Rectangle(50, 170, 70, 70));
        myMediumDifficultyButton = new UIButton(theAssetManager.getAsset("mediumButton"), new Rectangle(130, 170, 70, 70));
        myHardDifficultyButton = new UIButton(theAssetManager.getAsset("hardButton"), new Rectangle(210, 170, 70, 70));
    }

    /**
     * This method creates character buttons.
     *
     * @param theAssetManager AssetManager.
     */
    private void createCharacterButtons(final AssetManager theAssetManager) {
        myCharacterSelectButtons = new UIButton[NUM_CHARACTERS];

        for (int i = 0; i < NUM_CHARACTERS; i++) {
            myCharacterSelectButtons[i] = new UIButton(theAssetManager.getAsset(CLASS_NAMES[i] + "Image"), new Rectangle(80 * i + 50, 60, 70, 70));
        }
    }

    /**
     * This method draws the screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void draw(final Graphics2D theGraphics2D) {
        if (theGraphics2D == null) {
            throw new IllegalArgumentException("Graphics2D Cannot be null");
        }



        theGraphics2D.setColor(Color.BLACK);
        theGraphics2D.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.setFont(new Font("Arial", Font.BOLD, 18));
        theGraphics2D.drawString("Select Character:", 50, 50);
        theGraphics2D.drawString("Select Difficulty:", 50, 165);
        theGraphics2D.drawString("Create New Game", 50, 25);

        for (UIButton button : myCharacterSelectButtons) {
            button.draw(theGraphics2D);
        }

        theGraphics2D.setColor(isNameInputFocused ? Color.WHITE : Color.LIGHT_GRAY);
        theGraphics2D.fill(myNameInputFieldBounds);
        theGraphics2D.setColor(Color.BLACK);
        theGraphics2D.draw(myNameInputFieldBounds);

        theGraphics2D.setFont(new Font("Arial", Font.PLAIN, 14));
        if (myPlayerName.isEmpty() && !isNameInputFocused) {
            theGraphics2D.setColor(Color.GRAY);
            theGraphics2D.drawString("Enter your name...", myNameInputFieldBounds.x + 5, myNameInputFieldBounds.y + 20);
        } else {
            theGraphics2D.setColor(Color.BLACK);
            theGraphics2D.drawString(myPlayerName, myNameInputFieldBounds.x + 5, myNameInputFieldBounds.y + 20);
        }

        if (isCharacterSelected && !myPlayerName.isEmpty() && myDifficulty > 0) {
            myCreateGameButton.draw(theGraphics2D);
        }

        myBackButton.draw(theGraphics2D);
        myEasyDifficultyButton.draw(theGraphics2D);
        myMediumDifficultyButton.draw(theGraphics2D);
        myHardDifficultyButton.draw(theGraphics2D);
    }

    /**
     * This method handles the player hovering over a button.
     *
     * @param theMousePoint MousePointer.
     */
    public void handleHoverUpdate(final Point theMousePoint) {
        for (int i = 0; i < NUM_CHARACTERS; i++) {
            myCharacterSelectButtons[i].setHovered(myCharacterSelectButtons[i].contains(theMousePoint));

            if (mySelectedCharacter != null && mySelectedCharacter.equals(CLASS_NAMES[i])) {
                myCharacterSelectButtons[i].setHovered(true);
            }
        }

        myEasyDifficultyButton.setHovered(myEasyDifficultyButton.contains(theMousePoint) || myDifficulty == 5);
        myMediumDifficultyButton.setHovered(myMediumDifficultyButton.contains(theMousePoint) || myDifficulty == 8);
        myHardDifficultyButton.setHovered(myHardDifficultyButton.contains(theMousePoint) || myDifficulty == 12);
        myBackButton.setHovered(myBackButton.contains(theMousePoint));

        myCreateGameButton.setHovered(myCreateGameButton.contains(theMousePoint));
    }

    /**
     * This method handles mouse click.
     *
     * @param theClickPoint Point where mouse clicked
     */
    public void handleClick(final Point theClickPoint) {
        isNameInputFocused = myNameInputFieldBounds.contains(theClickPoint);
        
        handleCharacterSelect(theClickPoint);

        if (myCreateGameButton.contains(theClickPoint)) {
            if (isCharacterSelected && !myPlayerName.isEmpty()) {
                myUI.loadGame(new Player(mySelectedCharacter, myPlayerName), 3 * myDifficulty, 3 * myDifficulty, 3 * myDifficulty);
            } else {
                System.out.println("Please select a character and enter a name.");
            }
        }

        if (myEasyDifficultyButton.contains(theClickPoint)) {
            myDifficulty = 5;
        }
        if (myMediumDifficultyButton.contains(theClickPoint)) {
            myDifficulty = 8;
        }
        if (myHardDifficultyButton.contains(theClickPoint)) {
            myDifficulty = 12;
        }
        if (myBackButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.MENU);
        }
    }

    /**
     * This method handles the selected character.
     *
     * @param theClickPoint Point where mouse clicked
     */
    private void handleCharacterSelect(final Point theClickPoint) {
        for (int i = 0; i < NUM_CHARACTERS; i++) {
            if (myCharacterSelectButtons[i].contains(theClickPoint)) {
                mySelectedCharacter = CLASS_NAMES[i];
                isCharacterSelected = true;
            }
        }
    }

    /**
     * This method handles key presses.
     *
     * @param keyCode Which Key was Pressed.
     * @param keyChar Key Character.
     */
    public void handleKeyPress(final int keyCode, final char keyChar) {
        if (isNameInputFocused) {
            if (keyCode == KeyEvent.VK_BACK_SPACE && !myPlayerName.isEmpty()) {
                myPlayerName = myPlayerName.substring(0, myPlayerName.length() - 1);
            } else if (keyCode == KeyEvent.VK_ENTER) {
                isNameInputFocused = false;
            } else if (Character.isLetterOrDigit(keyChar) || Character.isSpaceChar(keyChar)) {
                myPlayerName += keyChar;
            }
        }
    }
}
