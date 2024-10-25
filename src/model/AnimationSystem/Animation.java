package model.AnimationSystem;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {

    private int myFrameCount;
    private int myFrameDelay;
    private int myCurrentFrame;
    private final int myAnimationDirection;
    private final int myTotalFrames;

    private boolean isStopped;

    private final List<Frame> myFrames = new ArrayList<>();

    public Animation(final BufferedImage[] theFrames, final int theFrameDelay) {
        this.myFrameDelay = theFrameDelay;
        this.isStopped = true;

        for (BufferedImage frame : theFrames) {
            addFrame(frame, theFrameDelay);
        }

        this.myFrameCount = 0;
        this.myFrameDelay = theFrameDelay;
        this.myCurrentFrame = 0;
        this.myAnimationDirection = 1;
        this.myTotalFrames = this.myFrames.size();

    }

    public void start() {
        if (!isStopped) {
            return;
        }

        if (myFrames.isEmpty()) {
            return;
        }

        isStopped = false;
    }

    public void stop() {
        if (myFrames.isEmpty()) {
            return;
        }

        isStopped = true;
    }

    public void restart() {
        if (myFrames.isEmpty()) {
            return;
        }

        isStopped = false;
        myCurrentFrame = 0;
    }

    public void reset() {
        this.isStopped = true;
        this.myFrameCount = 0;
        this.myCurrentFrame = 0;
    }

    private void addFrame(final BufferedImage theFrame, final int theDuration) {
        if (theDuration <= 0) {
            System.err.println("Invalid duration: " + theDuration);
            throw new RuntimeException("Invalid duration: " + theDuration);
        }

        myFrames.add(new Frame(theFrame, theDuration));
        myCurrentFrame = 0;
    }

    public BufferedImage getSprite() {
        return myFrames.get(myCurrentFrame).getFrame();
    }

    public void update() {
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

}