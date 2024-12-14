package view;

import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import model.GameConfig;

import java.awt.*;

/**
 * Represents the "How to Play" screen in the game, which provides instructional screens to the player.
 */
public class HowToPlayScreen implements Screen {
    /**
     * Asset manager for retrieving visual assets for the screen.
     */
    private final AssetManager myAssetManager;

    /**
     * Game state manager for controlling game state transitions.
     */
    private final GameStateManager myGameStateManager;

    /**
     * The current screen index (0-3) being displayed.
     */
    private int myCurrentScreen;

    /**
     * Button for progressing to the next screen or returning to the main menu.
     */
    private final UIButton myNextButton;

    /**
     * Constructs a HowToPlayScreen with the specified asset and game state managers.
     *
     * @param theAssetManager    the AssetManager to retrieve assets.
     * @param theGameStateManager the GameStateManager to handle game state transitions.
     */
    public HowToPlayScreen(final AssetManager theAssetManager, final GameStateManager theGameStateManager) {
        myAssetManager = theAssetManager;
        myGameStateManager = theGameStateManager;

        myCurrentScreen = 0;
        myNextButton = new UIButton(theAssetManager.getAsset("createGameButton"), new Rectangle(450, 350, 60, 60));
    }

    /**
     * Draws the current "How to Play" screen and the next button.
     *
     * @param theGraphics2D the Graphics2D context for rendering.
     */
    @Override
    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.BLACK);
        theGraphics2D.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        switch (myCurrentScreen) {
            case 0 -> drawFirstScreen(theGraphics2D);
            case 1 -> drawSecondScreen(theGraphics2D);
            case 2 -> drawThirdScreen(theGraphics2D);
            case 3 -> drawFourthScreen(theGraphics2D);
        }

        myNextButton.draw(theGraphics2D);
    }

    /**
     * Handles a click event, progressing to the next screen or transitioning back to the main menu.
     *
     * @param theClickPoint the location of the mouse click.
     */
    @Override
    public void handleClick(final Point theClickPoint) {
        if (myNextButton.contains(theClickPoint)) {
            if (myCurrentScreen < 3) {
                myCurrentScreen++;
            } else {
                myCurrentScreen = 0;
                myGameStateManager.setState(GameStateManager.State.MENU);
            }
        }
    }

    /**
     * Updates the hover state of the next button based on the mouse pointer's position.
     *
     * @param theMousePoint the current mouse pointer location.
     */
    @Override
    public void handleHoverUpdate(final Point theMousePoint) {
        myNextButton.setHovered(myNextButton.contains(theMousePoint));
    }

    /**
     * Draws the first instructional screen.
     *
     * @param theGraphics2D the Graphics2D context for rendering.
     */
    private void drawFirstScreen(final Graphics2D theGraphics2D) {
        theGraphics2D.drawImage(myAssetManager.getAsset("htp1"), 0, 0, Color.BLACK, null);
    }

    /**
     * Draws the second instructional screen.
     *
     * @param theGraphics2D the Graphics2D context for rendering.
     */
    private void drawSecondScreen(final Graphics2D theGraphics2D) {
        theGraphics2D.drawImage(myAssetManager.getAsset("htp2"), 0, 0, Color.BLACK, null);
    }

    /**
     * Draws the third instructional screen.
     *
     * @param theGraphics2D the Graphics2D context for rendering.
     */
    private void drawThirdScreen(final Graphics2D theGraphics2D) {
        theGraphics2D.drawImage(myAssetManager.getAsset("htp3"), 0, 0, Color.BLACK, null);
    }

    /**
     * Draws the fourth instructional screen.
     *
     * @param theGraphics2D the Graphics2D context for rendering.
     */
    private void drawFourthScreen(final Graphics2D theGraphics2D) {
        theGraphics2D.drawImage(myAssetManager.getAsset("htp4"), 0, 0, Color.BLACK, null);
    }
}
