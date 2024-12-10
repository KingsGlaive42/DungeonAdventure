package view;

import controller.GameStateManager;
import model.AnimationSystem.AssetManager;

import java.awt.*;

import static model.GameConfig.SCREEN_HEIGHT;
import static model.GameConfig.SCREEN_WIDTH;

public class TitleScreen {
    private final GameStateManager myGameStateManager;
    
    private final UIButton myPlayButton;
    private final UIButton myLoadButton;
    private final UIButton myOptionsButton;
    private final UIButton myExitButton;

    public TitleScreen(final AssetManager theAssetManager, final GameStateManager theGameStateManager) {
        this.myGameStateManager = theGameStateManager;
        
        myPlayButton = new UIButton(theAssetManager.getAsset("playButton"), new Rectangle(192, 150, 150, 64));
        myLoadButton = new UIButton(theAssetManager.getAsset("loadButton"), new Rectangle(10, 350, 55, 55));
        myOptionsButton = new UIButton(theAssetManager.getAsset("optionButton"), new Rectangle(210, 214, 112, 55));
        myExitButton = new UIButton(theAssetManager.getAsset("exitButton"), new Rectangle(210, 269, 112, 55));
    }

    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.BLACK);
        theGraphics2D.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        myPlayButton.draw(theGraphics2D);
        myLoadButton.draw(theGraphics2D);
        myOptionsButton.draw(theGraphics2D);
        myExitButton.draw(theGraphics2D);
    }

    public void handleHoverUpdate(final Point theMousePoint) {
        myPlayButton.setHovered(myPlayButton.contains(theMousePoint));
        myLoadButton.setHovered(myLoadButton.contains(theMousePoint));
        myOptionsButton.setHovered(myOptionsButton.contains(theMousePoint));
        myExitButton.setHovered(myExitButton.contains(theMousePoint));
    }

    public void handleClick(final Point theClickPoint) {
        if (myPlayButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.GAME_CREATE);
        } else if (myLoadButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.LOAD);
        } else if (myOptionsButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.OPTION);
        } else if (myExitButton.contains(theClickPoint)) {
            System.exit(0);
        }
    }
}
