package view;

import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import model.Player.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

import static model.GameConfig.theGraphics2D;
import static model.GameConfig.NUM_CHARACTERS;

public class GameCreateScreen {
    private final UI myUI;

    private UIButton[] myCharacterSelectButtons;
    private final UIButton myCreateGameButton;

    private boolean isCharacterSelected;
    private String mySelectedCharacter;

    private boolean isNameInputFocused = false;
    private String myPlayerName = "";
    private final Rectangle myNameInputFieldBounds = new Rectangle(200, 350, 200, 30);

    private int myDifficulty;
    private final UIButton myEasyDifficultyButton;
    private final UIButton myMediumDifficultyButton;
    private final UIButton myHardDifficultyButton;


    public GameCreateScreen(final UI theUI, final AssetManager theAssetManager) {
        myUI = theUI;

        createCharacterButtons(theAssetManager);

        myCreateGameButton = new UIButton(theAssetManager.getAsset("createGameButton"), new Rectangle(300, 300, 30, 30));

        myEasyDifficultyButton = new UIButton(theAssetManager.getAsset("easyButton"), new Rectangle(400, 50, 40, 40));
        myMediumDifficultyButton = new UIButton(theAssetManager.getAsset("mediumButton"), new Rectangle(400, 100, 40, 40));
        myHardDifficultyButton = new UIButton(theAssetManager.getAsset("hardButton"), new Rectangle(400, 150, 40, 40));
    }

    private void createCharacterButtons(final AssetManager theAssetManager) {
        myCharacterSelectButtons = new UIButton[NUM_CHARACTERS];

        for (int i = 0; i < NUM_CHARACTERS; i++) {
            myCharacterSelectButtons[i] = new UIButton(theAssetManager.getAsset(theGraphics2D[i] + "Image"), new Rectangle(60 * i, 30, 50, 50));
        }
    }

    public void draw(final Graphics2D theGraphics2D) {
        if (theGraphics2D == null) {
            throw new IllegalArgumentException("Graphics2D Cannot be null");
        }

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

        myEasyDifficultyButton.draw(theGraphics2D);
        myMediumDifficultyButton.draw(theGraphics2D);
        myHardDifficultyButton.draw(theGraphics2D);
    }

    public void handleHoverUpdate(final Point theMousePoint) {
        for (int i = 0; i < NUM_CHARACTERS; i++) {
            myCharacterSelectButtons[i].setHovered(myCharacterSelectButtons[i].contains(theMousePoint));

            if (mySelectedCharacter != null && mySelectedCharacter.equals(theGraphics2D[i])) {
                myCharacterSelectButtons[i].setHovered(true);
            }
        }

        myEasyDifficultyButton.setHovered(myEasyDifficultyButton.contains(theMousePoint) || myDifficulty == 5);
        myMediumDifficultyButton.setHovered(myMediumDifficultyButton.contains(theMousePoint) || myDifficulty == 8);
        myHardDifficultyButton.setHovered(myHardDifficultyButton.contains(theMousePoint) || myDifficulty == 12);

        myCreateGameButton.setHovered(myCreateGameButton.contains(theMousePoint));
    }

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
    }

    private void handleCharacterSelect(final Point theClickPoint) {
        for (int i = 0; i < NUM_CHARACTERS; i++) {
            if (myCharacterSelectButtons[i].contains(theClickPoint)) {
                mySelectedCharacter = theGraphics2D[i];
                isCharacterSelected = true;
            }
        }
    }

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
