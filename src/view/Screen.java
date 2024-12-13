package view;

import java.awt.*;

/**
 * Screen Interface
 *
 * @author Jayden Fausto
 */
public interface Screen {
    /**
     * This method draws the screen.
     *
     * @param theGraphics2D Graphics.
     */
    void draw(final Graphics2D theGraphics2D);

    /**
     * This method handles clicking.
     *
     * @param theClickPoint Click point.
     */
    void handleClick(final Point theClickPoint);

    /**
     * This method handles hovering.
     *
     * @param theMousePoint Hovering point.
     */
    void handleHoverUpdate(final Point theMousePoint);
}
