package model.AnimationSystem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Frame implements Serializable {
    /**
     * Serialization identifier for this class.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The image associated with this frame. This field is transient and will be serialized manually.
     */
    private transient BufferedImage myFrame;

    /**
     * The duration of the frame in milliseconds.
     */
    private final int myDuration;

    /**
     * Constructs a Frame object with the specified image and duration.
     *
     * @param theFrame the BufferedImage representing the frame image, must not be {@code null}.
     * @param theDuration the duration of the frame in milliseconds, must be greater than zero.
     * @throws IllegalArgumentException if theFrame is null or theDuration is not positive.
     */
    public Frame(final BufferedImage theFrame, final int theDuration) {
        if (theFrame == null) {
            throw new IllegalArgumentException("Frame Image must not be null.");
        }
        if (theDuration <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }

        this.myFrame = theFrame;
        this.myDuration = theDuration;
    }

    /**
     * Returns the image associated with this frame.
     *
     * @return the BufferedImage of this frame.
     */
    public BufferedImage getFrame() {
        return myFrame;
    }

    /**
     * Returns the duration of this frame in milliseconds.
     *
     * @return the duration of this frame in milliseconds.
     */
    public int getDuration() {
        return myDuration;
    }

    /**
     * Custom serialization logic for the BufferedImage field.
     * The image is written in PNG format to the output stream.
     *
     * @param theOutputStream the ObjectOutputStream to which the object is written.
     * @throws IOException if an I/O error occurs during writing.
     */
    @Serial
    private void writeObject(final ObjectOutputStream theOutputStream) throws IOException {
        theOutputStream.defaultWriteObject();
        if (myFrame != null) {
            // Write the image type to ensure correct deserialization
            theOutputStream.writeInt(myFrame.getType());
            theOutputStream.writeInt(myFrame.getWidth());
            theOutputStream.writeInt(myFrame.getHeight());
            for (int x = 0; x < myFrame.getWidth(); x++) {
                for (int y = 0; y < myFrame.getHeight(); y++) {
                    theOutputStream.writeInt(myFrame.getRGB(x, y));
                }
            }
        }
    }

    /**
     * Custom deserialization logic for the BufferedImage field.
     * The image is restored from the input stream.
     *
     * @param theInputStream the ObjectInputStream from which the object is read.
     * @throws IOException if an I/O error occurs during reading.
     * @throws ClassNotFoundException if a class of the serialized object cannot be found.
     */
    @Serial
    private void readObject(final ObjectInputStream theInputStream) throws IOException, ClassNotFoundException {
        theInputStream.defaultReadObject();
        int imageType = theInputStream.readInt();
        int width = theInputStream.readInt();
        int height = theInputStream.readInt();
        myFrame = new BufferedImage(width, height, imageType);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                myFrame.setRGB(x, y, theInputStream.readInt());
            }
        }
    }

    /**
     * Returns a string representation of this Frame object.
     * The string includes the duration of the frame in milliseconds.
     *
     * @return a string representation of this frame.
     */
    @Override public String toString() {
        return "Frame [Duration=" + myDuration + "ms]";
    }
}