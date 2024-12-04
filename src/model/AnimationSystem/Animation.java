package model.AnimationSystem;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an animation consisting of multiple frames. Supports playback control such as start, stop, restart, and update.
 */
public class Animation implements Serializable {

    // Animation properties
    private int myFrameCount;
    private final int myFrameDelay;
    private int myCurrentFrame;
    private final int myAnimationDirection;
    private final int myTotalFrames;

    private boolean isStopped;

    private final List<Frame> myFrames = new ArrayList<>();

    /**
     * Constructs an Animation object with the given frames and frame delay.
     *
     * @param theFrames an array of BufferedImage objects representing the animation frames, must not be null or empty.
     * @param theFrameDelay the delay between frames in animation, must be greater than zero.
     * @throws IllegalArgumentException if theFrames is  null, empty, or if theFrameDelay is less than or equal to zero.
     */
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

    /**
     * Starts the animation playback.
     */
    public synchronized void start() {
        if (!isStopped) {
            return;
        }

        if (myFrames.isEmpty()) {
            return;
        }

        isStopped = false;
    }

    /**
     * Stops the animation playback.
     */
    public synchronized void stop() {
        if (myFrames.isEmpty()) {
            return;
        }

        isStopped = true;
    }

    /**
     * Restarts the animation playback from the first frame.
     */
    public synchronized void restart() {
        if (myFrames.isEmpty()) {
            return;
        }

        isStopped = false;
        myCurrentFrame = 0;
    }

    /**
     * Resets the animation to its initial state and stops playback.
     */
    public synchronized void reset() {
        this.isStopped = true;
        this.myFrameCount = 0;
        this.myCurrentFrame = 0;
    }

    /**
     * Adds a frame to the animation with the specified duration.
     *
     * @param theFrame the BufferedImage representing the frame, must not be null.
     * @param theDuration the duration of the frame in milliseconds, must be greater than zero.
     * @throws IllegalArgumentException if theDuration is less than or equal to zero or theFrame is null.
     */
    private void addFrame(final BufferedImage theFrame, final int theDuration) {
        if (theDuration <= 0) {
            throw new IllegalArgumentException("Invalid duration: " + theDuration +". Must be greater than zero.");
        }

        myFrames.add(new Frame(theFrame, theDuration));
        myCurrentFrame = 0;
    }

    /**
     * Returns the current frame's image.
     *
     * @return the BufferedImage of the current frame.
     */
    public synchronized BufferedImage getSprite() {
        return myFrames.get(myCurrentFrame).getFrame();
    }

    /**
     * Updates the animation, advancing to the next frame if the delay is met.
     */
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

    /**
     * Returns an unmodifiable list of the frames in the animation.
     *
     * @return an unmodifiable List of Frame objects.
     */
    public List<Frame> getFrames() {
        return Collections.unmodifiableList(myFrames);
    }

    /**
     * Returns if the animation is currently stopped.
     *
     * @return true if the animation is currently stopped
     */
    public boolean isStopped() {
        return isStopped;
    }

    /**
     * Returns frame count of Animation.
     *
     * @return the frame count.
     */
    public int getFrameCount() {
        return myFrameCount;
    }

    /**]
     * Returns the current frame of the animation
     *
     * @return the current frame.
     */
    public int getCurrentFrame() {
        return myCurrentFrame;
    }

    @Override
    public String toString() {
        return "Animation [Total Frames=" + myTotalFrames + ", Frame Delay=" + myFrameDelay + "ms]";
    }
}