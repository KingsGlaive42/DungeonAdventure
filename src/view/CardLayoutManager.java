package view;

import controller.CombatController;
import controller.GameStateManager;
import controller.InputListener;
import model.GameConfig;

import javax.swing.*;
import java.awt.*;

/**
 * This is the CardLayoutManager class that
 * manages the shift between Game and Combat Panels.
 *
 * @author Thomas Le
 */
public class CardLayoutManager {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final GamePanel gamePanel;
    private final CombatPanel combatPanel;

    /**
     * CardLayoutManager Constructor
     *
     * @param theCombatController CombatController for Combat Panel.
     * @param theGameStateManager GameStateManager for Game Panel.
     * @param theUI UI for Game Panel.
     */
    public CardLayoutManager(final CombatController theCombatController, final GameStateManager theGameStateManager, final UI theUI) {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));

        // Initialize the panels
        gamePanel = new GamePanel(theGameStateManager, theUI);
        combatPanel = new CombatPanel(theCombatController);

        // Add panels to the card layout
        cardPanel.add(gamePanel, "GamePanel");
        cardPanel.add(combatPanel, "CombatPanel");

        // Set the initial screen
        cardLayout.show(cardPanel, "GamePanel"); // Show the regular game panel by default
    }

    /**
     * This method returns the card panel.
     *
     * @return card panel.
     */
    public JPanel getCardPanel() {
        return cardPanel;
    }

    /**
     * this method returns the name of the current
     * panel displayed in Card Panel.
     *
     * @return Name of current panel.
     */
    public String getCurrentPanel() {
        // Get the layout's current component by iterating through cardPanel's children
        for (Component comp : cardPanel.getComponents()) {
            if (comp.isVisible()) {
                if (comp == gamePanel) {
                    return "GamePanel";
                } else if (comp == combatPanel) {
                    return "CombatPanel";
                }
            }
        }
        return null; // If no panel is visible (unlikely), return null
    }

    /**
     * This method returns Game Panel.
     *
     * @return Game Panel.
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * This method returns Combat Panel.
     *
     * @return Combat Panel.
     */
    public CombatPanel getCombatPanel() {
        return combatPanel;
    }

    /**
     * This method switches to combat panel.
     */
    public void switchToCombatPanel() {
        cardLayout.show(cardPanel, "CombatPanel");
    }

    /**
     * This method switches to game panel.
     */
    public void switchToGamePanel() {
        cardLayout.show(cardPanel, "GamePanel");

        InputListener.getInstance().reset();
    }
}
