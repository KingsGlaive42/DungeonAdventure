package model.AnimationSystem.tests;

import model.AnimationSystem.Animation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class AnimationTest {

    private BufferedImage[] testFrames;

    @BeforeEach
    void setUp() {
        testFrames = new BufferedImage[2];
        testFrames[0] = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        testFrames[1] = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    }

    @Test
    void testConstructorValidInput() {
        Animation animation = new Animation(testFrames, 5);
        assertEquals(2, animation.getFrames().size());
    }

    @Test
    void testConstructorInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> new Animation(null, 5));
        assertThrows(IllegalArgumentException.class, () -> new Animation(new BufferedImage[0], 5));
        assertThrows(IllegalArgumentException.class, () -> new Animation(testFrames, 0));
    }

    @Test
    void testStartAndStop() {
        Animation animation = new Animation(testFrames, 5);

        animation.start();
        assertFalse(animation.isStopped());

        animation.stop();
        assertTrue(animation.isStopped());
    }

    @Test
    void testUpdate() {
        Animation animation = new Animation(testFrames, 1);
        animation.start();
        animation.update();
        assertEquals(testFrames[0], animation.getSprite());
    }

    @Test
    void testRestartAndReset() {
        Animation animation = new Animation(testFrames, 5);

        animation.start();
        animation.update();
        animation.update(); // Advance frames
        assertEquals(2, animation.getFrames().size());

        animation.reset();
        assertEquals(0, animation.getFrameCount());
        assertEquals(0, animation.getCurrentFrame());
        assertTrue(animation.isStopped());
    }

    @Test
    void testToString() {
        Animation animation = new Animation(testFrames, 5);
        String expected = "Animation [Total Frames=2, Frame Delay=5ms]";
        assertEquals(expected, animation.toString());
    }
}