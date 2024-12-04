package model.AnimationSystem.tests;

import model.AnimationSystem.Frame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.*;


import static org.junit.jupiter.api.Assertions.*;

class FrameTest {
    private BufferedImage testImage;

    @BeforeEach
    void setUp() {
        testImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                testImage.setRGB(x, y, 0xFFFFFF);
            }
        }
    }

    @Test
    void testConstructorValidInputs() {
        Frame frame = new Frame(testImage, 500);
        assertEquals(testImage, frame.getFrame());
        assertEquals(500, frame.getDuration());
    }

    @Test
    void testConstructorInvalidImage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Frame(null, 500));
        assertEquals("Frame Image must not be null.", exception.getMessage());
    }

    @Test
    void testConstructorInvalidDuration() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Frame(testImage, 0));
        assertEquals("Duration must be greater than zero.", exception.getMessage());
    }

    @Test
    void testSerializationAndDeserialization() throws IOException, ClassNotFoundException {
        Frame originalFrame = new Frame(testImage, 500);

        // Serialize the frame
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(originalFrame);
        objectOutputStream.close();

        // Deserialize the frame
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Frame deserializedFrame = (Frame) objectInputStream.readObject();

        // Check deserialized data
        assertNotNull(deserializedFrame);
        assertEquals(500, deserializedFrame.getDuration());
        assertNotNull(deserializedFrame.getFrame());

        // Verify the image's content
        BufferedImage deserializedImage = deserializedFrame.getFrame();
        assertEquals(testImage.getWidth(), deserializedImage.getWidth());
        assertEquals(testImage.getHeight(), deserializedImage.getHeight());
        for (int x = 0; x < testImage.getWidth(); x++) {
            for (int y = 0; y < testImage.getHeight(); y++) {
                assertEquals(testImage.getRGB(x, y), deserializedImage.getRGB(x, y));
            }
        }
    }

    @Test
    void testToString() {
        Frame frame = new Frame(testImage, 500);
        assertEquals("Frame [Duration=500ms]", frame.toString());
    }
}