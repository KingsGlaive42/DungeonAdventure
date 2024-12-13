package view;

import controller.GameStateManager;
import controller.InputListener;
import model.GameConfig;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This is the Game Panel Class
 *
 * @author Jayden Fausto
 */
public class GamePanel extends JPanel {
    private final GameStateManager myGameStateManager;
    private final UI myUI;

    /**
     * Game Panel Constructor.
     *
     * @param theGameStateManager GameStateManager.
     * @param theUI UI.
     */
    public GamePanel(final GameStateManager theGameStateManager, final UI theUI) {
        InputListener myInputListener = InputListener.getInstance();
        myGameStateManager = theGameStateManager;
        myUI = theUI;

        this.setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(myInputListener);
        this.setFocusable(true);

        this.addKeyListener(myInputListener);
        this.addKeyListener(new KeyInputAdapter());
        this.addMouseListener(new MouseInputAdapter());
        this.addMouseMotionListener(new MouseInputAdapter());
    }

    /**
     * This method paints the graphics.
     *
     * @param theGraphics theGraphics.
     */
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);

        Graphics2D graphics2D = (Graphics2D) theGraphics;

        myGameStateManager.paint(graphics2D);

        graphics2D.dispose();
    }

    /**
     * This method repaints based on mouse hovers or clicks.
     */
    private class MouseInputAdapter extends MouseAdapter {
        @Override
        public void mouseMoved(final MouseEvent theMouseEvent) {
            myUI.updateHoverStates(theMouseEvent.getPoint());
            repaint();
        }

        @Override
        public void mouseClicked(final MouseEvent theMouseEvent) {
            myUI.handleClicks(theMouseEvent.getPoint());
            repaint();
        }
    }

    /**
     * This method repaints the screen based on keys pressed.
     */
    private class KeyInputAdapter extends KeyAdapter {
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            myUI.handleKeyPress(theEvent.getKeyCode(), theEvent.getKeyChar());
            repaint();
        }
    }
}
