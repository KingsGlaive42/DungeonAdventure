package view;

import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import model.GameConfig;

import java.awt.*;

public class OptionScreen implements Screen {
    private final GameStateManager myGameStateManager;

    private final UIButton[] myUIButtons;
    private final UIButton myBackButton;

    public OptionScreen(final GameStateManager theGameStateManager, final AssetManager theAssetManager) {
        this.myGameStateManager = theGameStateManager;

        myUIButtons = new UIButton[GameConfig.CHEAT_NAMES.length];
        for (int i = 0; i < myUIButtons.length; i++) {
            myUIButtons[i] = new UIButton(theAssetManager.getAsset(GameConfig.CHEAT_NAMES[i] + "Image"), new Rectangle(130 * i + 25, 150, 100, 100));
        }

        myBackButton = new UIButton(theAssetManager.getAsset("backButton"), new Rectangle(25, 325, 70, 70));
    }

    public void draw(final Graphics2D theGraphics2D) {
        if (theGraphics2D == null) {
            throw new IllegalArgumentException("Graphics2D Cannot be null");
        }

        theGraphics2D.setColor(Color.BLACK);
        theGraphics2D.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.setFont(new Font("Arial", Font.BOLD, 48));
        theGraphics2D.drawString("CHEATS", 160, 70);

        for (UIButton button : myUIButtons) {
            button.draw(theGraphics2D);
        }

        myBackButton.draw(theGraphics2D);
    }

    public void handleClick(final Point theClickPoint) {
        for (int i = 0; i < myUIButtons.length; i++) {
            if (myUIButtons[i].contains(theClickPoint)) {
                switch (i) {
                    case 0:
                        GameConfig.setInfiniteHealth(!GameConfig.isInfiniteHealth());
                        break;
                    case 1:
                        GameConfig.setInfiniteDamage(!GameConfig.isInfiniteDamage());
                        break;
                    case 2:
                        GameConfig.setHighSpeed(!GameConfig.isHighSpeed());
                        break;
                    case 3:
                        GameConfig.setShowHitboxes(!GameConfig.isShowHitboxes());
                        break;
                    default:
                        throw new IllegalStateException("Not enough UI buttons to handle");
                }
            }
        }

        if (myBackButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.MENU);
        }
    }

    public void handleHoverUpdate(final Point theMousePoint) {
        for (UIButton myUIButton : myUIButtons) {
            myUIButton.setHovered(myUIButton.contains(theMousePoint));
        }

        if (GameConfig.isInfiniteHealth()) {
            myUIButtons[0].setHovered(true);
        }
        if (GameConfig.isInfiniteDamage()) {
            myUIButtons[1].setHovered(true);
        }
        if (GameConfig.isHighSpeed()) {
            myUIButtons[2].setHovered(true);
        }
        if (GameConfig.isShowHitboxes()) {
            myUIButtons[3].setHovered(true);
        }

        myBackButton.setHovered(myBackButton.contains(theMousePoint));
    }

}
