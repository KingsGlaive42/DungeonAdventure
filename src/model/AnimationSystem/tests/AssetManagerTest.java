package model.AnimationSystem.tests;

import model.AnimationSystem.AssetManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AssetManagerTest {

    private AssetManager assetManager;

    @BeforeEach
    void setUp() {
        assetManager = new AssetManager();
    }

    @Test
    void testLoadAssetValidInput() {
        String key = "testAsset";
        String filePath = "src/resources/test_image.png";

        assetManager.loadAsset(key, filePath);
        assertNotNull(assetManager.getAsset(key));
    }

    @Test
    void testLoadAssetInvalidFilePath() {
        String key = "invalidAsset";
        String filePath = "src/resources/non_existent.png";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> assetManager.loadAsset(key, filePath));
        assertTrue(exception.getMessage().contains("An error occurred loading an image"));
    }

    @Test
    void testGetAssetValidKey() {
        String key = "validAsset";
        String filePath = "src/resources/test_image.png";

        assetManager.loadAsset(key, filePath);
        BufferedImage image = assetManager.getAsset(key);
        assertNotNull(image);
    }

    @Test
    void testGetAssetInvalidKey() {
        String key = "missingKey";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> assetManager.getAsset(key));
        assertEquals("The key '" + key + "' does not exist in the asset map.", exception.getMessage());
    }

    @Test
    void testGetAssets() {
        String key1 = "asset1";
        String key2 = "asset2";
        String filePath = "src/resources/test_image.png";

        assetManager.loadAsset(key1, filePath);
        assetManager.loadAsset(key2, filePath);

        assertEquals(2, assetManager.getAssets().size());
    }
}