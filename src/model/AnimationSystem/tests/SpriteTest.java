package model.AnimationSystem.tests;

import model.AnimationSystem.Sprite;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class SpriteTest {

    private static final String VALID_SPRITE_PATH = "src/resources/assets/player/player_idle.png";
    private static final String INVALID_SPRITE_PATH = "src/resources/invalid_path.png";

    @Test
    void testLoadSpriteValidPath() {
        Sprite sprite = new Sprite();
        assertDoesNotThrow(() -> sprite.loadSprite(VALID_SPRITE_PATH));
    }

    @Test
    void testLoadSpriteInvalidPath() {
        Sprite sprite = new Sprite();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprite.loadSprite(INVALID_SPRITE_PATH));
        assertTrue(exception.getMessage().contains("Failed to load sprite"));
    }

    @Test
    void testGetSpriteValidCoordinates() {
        Sprite sprite = new Sprite();
        sprite.loadSprite(VALID_SPRITE_PATH);

        BufferedImage image = sprite.getSprite(0, 0);
        assertNotNull(image);
        assertEquals(Sprite.TILE_SIZE, image.getWidth());
        assertEquals(Sprite.TILE_SIZE, image.getHeight());
    }

    @Test
    void testGetSpriteInvalidCoordinates() {
        Sprite sprite = new Sprite();
        sprite.loadSprite(VALID_SPRITE_PATH);

        assertThrows(IllegalArgumentException.class, () -> sprite.getSprite(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> sprite.getSprite(1000, 1000));
    }

    @Test
    void testToString() {
        Sprite sprite = new Sprite();
        String expected = "Sprite [TILE_SIZE=" + Sprite.TILE_SIZE + ", DEFAULT_SPRITE_PATH=" + Sprite.getDefaultSpritePath() + "]";
        assertEquals(expected, sprite.toString());
    }
}