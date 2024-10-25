package model.AnimationSystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {
    private BufferedImage mySpriteSheet;
    public static final int TILE_SIZE = 32;

    public void loadSprite(final String theFile) {
        mySpriteSheet = null;

        try {
            mySpriteSheet = ImageIO.read(new File(theFile));
        } catch (final IOException theException) {
            throw new RuntimeException(theException);
        }
    }

    public BufferedImage getSprite(final int theXGrid, final int theYGrid) {
        if (mySpriteSheet == null) {
            loadSprite("src/resources/assets/player/player_idle.png");
        }

        return mySpriteSheet.getSubimage(theXGrid * TILE_SIZE, theYGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}
