package view;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIButton {
    private final BufferedImage myImage;
    private final Rectangle myBounds;
    private boolean isHovered;

    public UIButton(final BufferedImage theImage, final Rectangle theBounds) {
        this.myImage = theImage;
        this.myBounds = theBounds;
    }

    public void draw(final Graphics2D theGraphics2D) {
        float alpha = isHovered ? 0.5f : 1f;
        theGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        theGraphics2D.drawImage(myImage, myBounds.x, myBounds.y, myBounds.width, myBounds.height, null);
        theGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void setHovered(final boolean isHovered) {
        this.isHovered = isHovered;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public boolean contains(final Point theClickPoint) {
        return myBounds.contains(theClickPoint);
    }
}
