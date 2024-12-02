package model.AnimationSystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AssetManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, BufferedImage> myAssets = new HashMap<>();

    public void loadAsset(final String theKey, final String theFilePath) {
        try {
            myAssets.put(theKey, ImageIO.read(new File(theFilePath)));
        } catch (final IOException e) {
            throw new RuntimeException("An error occurred loading an image from " + theFilePath + ". " + e.getMessage());
        }
    }

    public BufferedImage getAsset(final String theKey) {
        if (!myAssets.containsKey(theKey)) throw new IllegalArgumentException("The key '" + theKey + "' does not exist in Asset Map");

        return myAssets.get(theKey);
    }
}
