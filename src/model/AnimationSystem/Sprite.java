package model.AnimationSystem;

import model.GameConfig;

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
        if (mySpriteSheet == null) {
            loadSprite(DEFAULT_SPRITE_PATH);
        }

        int sheetWidth = mySpriteSheet.getWidth();
        int sheetHeight = mySpriteSheet.getHeight();

        if (theXGrid < 0 || theYGrid < 0 ||
                theXGrid * TILE_SIZE >= sheetWidth ||
                theYGrid * TILE_SIZE >= sheetHeight) {
            throw new IllegalArgumentException("Invalid grid coordinates: (" + theXGrid + ", " + theYGrid + ")");
        }

        return mySpriteSheet.getSubimage(theXGrid * TILE_SIZE, theYGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}
