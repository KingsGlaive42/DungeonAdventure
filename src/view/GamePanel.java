package view;

import controller.GameStateManager;
import controller.InputListener;
import utilities.GameConfig;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private final GameStateManager myGameStateManager;
    private final UI myUI;

    public GamePanel(final GameStateManager theGameStateManager, final UI theUI) {
        InputListener myInputListener = InputListener.getInstance();
        myGameStateManager = theGameStateManager;
        myUI = theUI;

        this.setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(myInputListener);
        this.setFocusable(true);

        //this.addKeyListener(myInputListener);
        this.addMouseListener(new MouseInputAdapter());
        this.addMouseMotionListener(new MouseInputAdapter());
    }

    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);

        Graphics2D graphics2D = (Graphics2D) theGraphics;

        myGameStateManager.paint(graphics2D);

        graphics2D.dispose();
    }

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
}
