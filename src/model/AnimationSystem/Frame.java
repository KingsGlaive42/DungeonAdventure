package model.AnimationSystem;

import java.awt.image.BufferedImage;

public class Frame {

    private BufferedImage myFrame;
    private int myDuration;

    public Frame(final BufferedImage theFrame, final int theDuration) {
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
        this.myDuration = theDuration;
    }

}