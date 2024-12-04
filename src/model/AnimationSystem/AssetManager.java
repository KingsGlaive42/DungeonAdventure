package model.AnimationSystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the loading and retrieval of assets such as images for use in the application.
 * Assets are stored in a map with unique string keys for quick access.
 */
public class AssetManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * A map of asset keys to their corresponding BufferedImage.
     */
    private final Map<String, BufferedImage> myAssets = new HashMap<>();

    /**
     * A map of asset keys to their file paths, used for reloading assets upon deserialization.
     */
    private final transient Map<String, String> myAssetPaths = new HashMap<>();

    /**
     * Loads an asset from the specified file path and associates it with a unique key.
     *
     * @param theKey the unique key for the asset, must not be null or empty.
     * @param theFilePath the file path to the asset, must not be {@code null} or empty.
     * @throws RuntimeException if the file cannot be loaded.
     */
    public synchronized void loadAsset(final String theKey, final String theFilePath) {
        if (theKey == null || theKey.isEmpty()) {
            throw new IllegalArgumentException("The asset key must not be null or empty.");
        }
        if (theFilePath == null || theFilePath.isEmpty()) {
            throw new IllegalArgumentException("The file path must not be null or empty.");
        }

        try {
            BufferedImage image = ImageIO.read(new File(theFilePath));
            myAssets.put(theKey, image);
            myAssetPaths.put(theKey, theFilePath);
        } catch (final IOException e) {
            throw new RuntimeException("An error occurred loading an image from " + theFilePath + ". " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves an asset by its key.
     *
     * @param theKey the key of the asset to retrieve, must not be null.
     * @return the BufferedImage associated with the given key.
     * @throws IllegalArgumentException if the key does not exist.
     */
    public synchronized BufferedImage getAsset(final String theKey) {
        if (theKey == null) {
            throw new IllegalArgumentException("The asset key must not be null.");
        }
        if (!myAssets.containsKey(theKey)) {
            throw new IllegalArgumentException("The key '" + theKey + "' does not exist in the asset map.");
        }
        return myAssets.get(theKey);
    }

    /**
     * Returns an unmodifiable view of the loaded assets.
     *
     * @return an unmodifiable map of asset keys to their BufferedImage.
     */
    public synchronized Map<String, BufferedImage> getAssets() {
        return Collections.unmodifiableMap(myAssets);
    }

    @Override
    public synchronized String toString() {
        return "AssetManager [Assets Loaded=" + myAssets.keySet() + "]";
    }
}
