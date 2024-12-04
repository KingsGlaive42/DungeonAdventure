package view;

import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import controller.SoundManager;

import java.awt.*;

import static model.GameConfig.SCREEN_HEIGHT;
import static model.GameConfig.SCREEN_WIDTH;

public class PauseScreen {
    private static final int SLIDER_WIDTH = 200;
    private static final int SLIDER_HEIGHT = 10;
    private int myBackgroundMusicVolume = 50;
    private int mySFXVolume = 50;
    private Rectangle myBgmSliderBounds, mySfxSliderBounds;
    private final UIButton mySaveButton;
    private final GameStateManager myGameStateManager;

    public PauseScreen(final AssetManager theAssetManager, final GameStateManager theGameStateManager) {
        this.myGameStateManager = theGameStateManager;
        mySaveButton = new UIButton(theAssetManager.getAsset("playButton"), new Rectangle(10, 10, 50, 50));
    }

    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(new Color(0, 0, 0, 150));
        theGraphics2D.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.setFont(new Font("Arial", Font.BOLD, 48));
        theGraphics2D.drawString("PAUSED", SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2 - 100);

        drawVolumeOptions(theGraphics2D);

        mySaveButton.draw(theGraphics2D);
    }

    public void handleClick(final Point theClickPoint) {
        if (myBgmSliderBounds.contains(theClickPoint)) {
            int relativeX = theClickPoint.x - myBgmSliderBounds.x;
            myBackgroundMusicVolume = Math.min(100, Math.max(0, (relativeX * 100) / myBgmSliderBounds.width));
            SoundManager.getInstance().setBackgroundVolume(myBackgroundMusicVolume);
        }

        if (mySfxSliderBounds.contains(theClickPoint)) {
            int relativeX = theClickPoint.x - mySfxSliderBounds.x;
            mySFXVolume = Math.min(100, Math.max(0, (relativeX * 100) / mySfxSliderBounds.width));
            SoundManager.getInstance().setEffectsVolume(mySFXVolume);
        }

        if (mySaveButton.contains(theClickPoint)) {
            myGameStateManager.setState(GameStateManager.State.SAVE);
        }
    }

    public void handleHoverUpdate(final Point theMousePoint) {
        mySaveButton.setHovered(mySaveButton.contains(theMousePoint));
    }

    private void drawVolumeOptions(final Graphics2D theGraphics2D) {
        int x = SCREEN_WIDTH / 2 - SLIDER_WIDTH / 2;
        int y = SCREEN_HEIGHT / 2;

        drawSlider(x, y, myBackgroundMusicVolume, "BGM Volume", theGraphics2D);
        myBgmSliderBounds = new Rectangle(x, y, SLIDER_WIDTH, SLIDER_HEIGHT);

        int sfxY = y + 60;
        drawSlider(x, sfxY, mySFXVolume, "SFX Volume", theGraphics2D);
        mySfxSliderBounds = new Rectangle(x, sfxY, SLIDER_WIDTH, SLIDER_HEIGHT);
    }

    private void drawSlider(final int theX, final int theY, final int theVolume, final String theLabel, final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.DARK_GRAY);
        theGraphics2D.fillRect(theX, theY, SLIDER_WIDTH, SLIDER_HEIGHT);

        int knobX = theX + (SLIDER_WIDTH * theVolume / 100) - 5;
        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.fillOval(knobX, theY - 5, 10, SLIDER_HEIGHT + 10);

        theGraphics2D.setFont(new Font("Arial", Font.PLAIN, 18));
        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.drawString(theLabel + ": " + theVolume + "%", theX, theY - 20);
    }
}
