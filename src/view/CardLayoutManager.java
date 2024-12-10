package view;

import controller.CombatController;
import controller.GameStateManager;
import model.GameConfig;

import javax.swing.*;
import java.awt.*;

public class CardLayoutManager {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final GamePanel gamePanel;
    private final CombatPanel combatPanel;

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

    public JPanel getCardPanel() {
        return cardPanel;
    }

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


    public GamePanel getGamePanel() {
        return gamePanel;
    }
    public CombatPanel getCombatPanel() {
        return combatPanel;
    }

    public void switchToCombatPanel() {
        cardLayout.show(cardPanel, "CombatPanel");
    }

    public void switchToGamePanel() {
        cardLayout.show(cardPanel, "GamePanel");
    }
}
