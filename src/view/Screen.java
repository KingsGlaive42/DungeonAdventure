package view;

import java.awt.*;

public interface Screen {
    void draw(final Graphics2D theGraphics2D);
    void handleClick(final Point theClickPoint);
    void handleHoverUpdate(final Point theMousePoint);
}
