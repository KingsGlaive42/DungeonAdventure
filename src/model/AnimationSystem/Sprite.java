package model.AnimationSystem;

import utilities.GameConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class Sprite implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private transient BufferedImage mySpriteSheet;
    public static final int TILE_SIZE = GameConfig.TILE_SIZE;
    private static final String DEFAULT_SPRITE_PATH = "src/resources/assets/player/player_idle.png";

    public synchronized void loadSprite(final String theFile) {
        mySpriteSheet = null;

        try {
            mySpriteSheet = ImageIO.read(new File(theFile));
        } catch (final IOException theException) {
            throw new IllegalArgumentException("Failed to load sprite from file: " + theFile, theException);

        }
    }

    public synchronized BufferedImage getSprite(final int theXGrid, final int theYGrid) {
        return getSprite(theXGrid, theYGrid, TILE_SIZE, TILE_SIZE);
    }

    public synchronized BufferedImage getSprite(final int theXGrid, final int theYGrid, final int theWidth, final int theHeight) {
        if (mySpriteSheet == null) {
            loadSprite(DEFAULT_SPRITE_PATH);
        }

        int sheetWidth = mySpriteSheet.getWidth();
        int sheetHeight = mySpriteSheet.getHeight();

        if (theXGrid < 0 || theYGrid < 0 ||
                theXGrid * theWidth >= sheetWidth ||
                theYGrid * theHeight >= sheetHeight) {
            throw new IllegalArgumentException("Invalid grid coordinates or sprite size: (" + theXGrid + ", " + theYGrid + ")");
        }

        return mySpriteSheet.getSubimage(theXGrid * theWidth, theYGrid * theHeight, theWidth, theHeight);
    }
}