package model.AnimationSystem;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Animation implements Serializable {

    private int myFrameCount;
    private final int myFrameDelay;
    private int myCurrentFrame;
    private final int myAnimationDirection;
    private final int myTotalFrames;

    private boolean isStopped;

    private final List<Frame> myFrames = new ArrayList<>();

    public Animation(final BufferedImage[] theFrames, final int theFrameDelay) {
        if (theFrames == null || theFrames.length == 0) {
            throw new IllegalArgumentException("Frame Array must not be null or empty.");
        }
        if (theFrameDelay <= 0) {
            throw new IllegalArgumentException("Frame delay must be greater than 0.");
        }

        this.myFrameDelay = theFrameDelay;
        this.isStopped = true;

        for (BufferedImage frame : theFrames) {
            addFrame(frame, theFrameDelay);
        }

        this.myFrameCount = 0;
        this.myCurrentFrame = 0;
        this.myAnimationDirection = 1;
        this.myTotalFrames = this.myFrames.size();

    }

    public synchronized void start() {
        if (!isStopped) {
            return;
        }

        if (myFrames.isEmpty()) {
            return;
        }

        isStopped = false;
    }

    public synchronized void stop() {
        if (myFrames.isEmpty()) {
            return;
        }

        isStopped = true;
    }

    public synchronized void restart() {
        if (myFrames.isEmpty()) {
            return;
        }

        isStopped = false;
        myCurrentFrame = 0;
    }

    public synchronized void reset() {
        this.isStopped = true;
        this.myFrameCount = 0;
        this.myCurrentFrame = 0;
    }

    private void addFrame(final BufferedImage theFrame, final int theDuration) {
        if (theDuration <= 0) {
            throw new IllegalArgumentException("Invalid duration: " + theDuration +". Must be greater than zero.");
        }

        myFrames.add(new Frame(theFrame, theDuration));
        myCurrentFrame = 0;
    }

    public synchronized BufferedImage getSprite() {
        return myFrames.get(myCurrentFrame).getFrame();
    }

    public synchronized void update() {
        if (!isStopped) {
            myFrameCount++;

            if (myFrameCount > myFrameDelay) {
                myFrameCount = 0;
                myCurrentFrame += myAnimationDirection;

                if (myCurrentFrame > myTotalFrames - 1) {
                    myCurrentFrame = 0;
                }
                else if (myCurrentFrame < 0) {
                    myCurrentFrame = myTotalFrames - 1;
                }
            }
        }

    }

    public List<Frame> getFrames() {
        return Collections.unmodifiableList(myFrames);
    }

}