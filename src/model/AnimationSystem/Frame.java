package model.AnimationSystem;

import java.awt.image.BufferedImage;
import java.io.Serial;
import java.io.Serializable;

public class Frame implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private transient BufferedImage myFrame;
    private int myDuration;

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

    public BufferedImage getFrame() {
        return myFrame;
    }

    public void setFrame(final BufferedImage theFrame) {
        this.myFrame = theFrame;
    }

    public int getMyDuration() {
        return myDuration;
    }

    public void setMyDuration(final int theDuration) {
        if (theDuration <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }
        this.myDuration = theDuration;
    }

}