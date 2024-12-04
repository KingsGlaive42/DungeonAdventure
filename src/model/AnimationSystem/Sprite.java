package model.AnimationSystem;

import model.GameConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a sprite loader and handler for a sprite sheet.
 * Provides functionality to load a sprite sheet and retrieve individual sprites based on grid coordinates.
 */
public class Sprite implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The sprite sheet loaded as a BufferedImage. Marked transient to avoid serialization issues.
     */
    private transient BufferedImage mySpriteSheet;

    /**
     * The size of each sprite tile in pixels.
     */
    public static final int TILE_SIZE = GameConfig.TILE_SIZE;

    /**
     * The default file path to the sprite sheet.
     */
    private static final String DEFAULT_SPRITE_PATH = "src/resources/assets/player/player_idle.png";

    /**
     * Loads the sprite sheet from the specified file path.
     *
     * @param theFile the file path to the sprite sheet, must not be null or empty.
     * @throws IllegalArgumentException if the sprite sheet cannot be loaded.
     */
    public synchronized void loadSprite(final String theFile) {
        if (theFile == null || theFile.isEmpty()) {
            throw new IllegalArgumentException("Sprite file path must not be null or empty");
        }

        mySpriteSheet = null;

        try {
            mySpriteSheet = ImageIO.read(new File(theFile));
        } catch (final IOException theException) {
            throw new IllegalArgumentException("Failed to load sprite from file: " + theFile, theException);

        }
    }

    /**
     * Retrieves a specific sprite from the sprite sheet based on the provided grid coordinates.
     * If the sprite sheet is not already loaded, the default sprite sheet is loaded.
     *
     * @param theXGrid the x-coordinate on the grid, must be non-negative and within bounds.
     * @param theYGrid the y-coordinate on the grid, must be non-negative and within bounds.
     * @return the BufferedImage representing the sprite.
     * @throws IllegalArgumentException if the grid coordinates are invalid.
     */
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

    /**
     * Returns the default sprite sheet file path.
     *
     * @return the default sprite sheet file path.
     */
    public static String getDefaultSpritePath() {
        return DEFAULT_SPRITE_PATH;
    }

    @Override
    public String toString() {
        return "Sprite [TILE_SIZE=" + TILE_SIZE + ", DEFAULT_SPRITE_PATH=" + DEFAULT_SPRITE_PATH + "]";
    }
}
