package view;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This is the UIButton Class
 *
 * @author Jayden Fausto
 */
public class UIButton {
    private BufferedImage myImage;
    private final Rectangle myBounds;
    private boolean isHovered;

    /**
     * UIButton Constructor
     *
     * @param theImage Image.
     * @param theBounds Bounds.
     */
    public UIButton(final BufferedImage theImage, final Rectangle theBounds) {
        this.myImage = theImage;
        this.myBounds = theBounds;
    }

    /**
     * This method draws the screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void draw(final Graphics2D theGraphics2D) {
        float alpha = isHovered ? 0.5f : 1f;
        theGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        theGraphics2D.drawImage(myImage, myBounds.x, myBounds.y, myBounds.width, myBounds.height, null);
        theGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    /**
     * This method sets isHovered boolean.
     *
     * @param isHovered boolean to set isHovered to.
     */
    public void setHovered(final boolean isHovered) {
        this.isHovered = isHovered;
    }

    /**
     * This method returns isHoveed.
     *
     * @return true if hovering, false if not.
     */
    public boolean isHovered() {
        return isHovered;
    }

    /**
     * This method checks if bounds contains the clicked point.
     *
     * @param theClickPoint Clicked point
     * @return True if bounds contains clicked point, false if not.
     */
    public boolean contains(final Point theClickPoint) {
        return myBounds.contains(theClickPoint);
    }

    /**
     * This method sets the image.
     *
     * @param theImage Image to be set.
     */
    public void setImage(final BufferedImage theImage) {
        myImage = theImage;
    }
}
