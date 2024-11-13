package controller;

import model.Combat.CombatEngine;
import model.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacters.Hero;
import model.DungeonCharacters.Priestess;
import view.CombatPanel;
import view.GamePanel;

public class CombatController {
    private final CombatEngine combatEngine;
    private final CombatPanel combatPanel;
    private final GamePanel gamePanel;  // Reference to the GamePanel
    private final Hero hero;
    private final DungeonCharacter enemy;

    public CombatController(Hero hero, DungeonCharacter enemy) {
        gamePanel = new GamePanel();
        this.hero = hero;
        this.enemy = enemy;
        this.combatEngine = new CombatEngine();
        this.combatPanel = new CombatPanel(this);

        updateHeroInfo();
        updateEnemyInfo();

        // Check if the hero is a Priestess and show healing options if so
        if (hero instanceof Priestess) {
            combatPanel.showHealButtons(true);
        }
    }

    public CombatPanel getCombatPanel() {
        return combatPanel;
    }

    public void handleAttack() {
        combatPanel.logAction(hero.getName() + " attacks " + enemy.getName());
        combatEngine.attack(hero, enemy);
        updateEnemyInfo();

        if (enemy.getHitPoints() <= 0) {
            combatPanel.logAction(enemy.getName() + " has been defeated!");
            return;
        }

        handleEnemyCounterattack();
    }

    public void handleDefend() {
        combatPanel.logAction(hero.getName() + " is defending.");
        combatEngine.handleDefend(hero);  // Call the defend logic
        handleEnemyCounterattack();
    }

    public void handleSpecialSkill() {
        if (hero instanceof Priestess) {
            combatPanel.showHealOptions();
        } else {
            combatPanel.logAction(hero.getName() + " uses special skill on " + enemy.getName());
            combatEngine.performSpecialSkill(hero, enemy);
            updateEnemyInfo();

            if (enemy.getHitPoints() <= 0) {
                combatPanel.logAction(enemy.getName() + " has been defeated!");
            } else {
                handleEnemyCounterattack();
            }
        }
    }

    public void handleHeal(int healRange) {
        if (hero instanceof Priestess) {
            ((Priestess) hero).useSpecialSkill(hero, combatEngine, healRange);
        } else {
            combatPanel.logAction("Healing is only available to the Priestess.");
        }

        // Show main action buttons after healing
        combatPanel.showActionButtons();
        handleEnemyCounterattack();
    }

    public void handleRetreat() {
        combatPanel.logAction(hero.getName() + " is retreating from combat!");
        // Logic for retreat: for example, end the combat and exit the screen
        combatPanel.displayGameOver(hero.getName() + " has retreated from combat.");
        // Optionally, you can trigger additional game mechanics like:
        // - Losing a portion of health or resources upon retreat
        // - Returning to a previous game state (like a dungeon or main menu)
    }

    private void handleEnemyCounterattack() {
        combatPanel.logAction(enemy.getName() + " counterattacks " + hero.getName());
        combatEngine.attack(enemy, hero);
        updateHeroInfo();

        if (hero.getHitPoints() <= 0) {
            combatPanel.displayGameOver(hero.getName() + " has been defeated!");
        }
    }

    private void updateHeroInfo() {
        String heroInfo = hero.getName() + " - HP: " + hero.getHitPoints() + "/" + hero.getMaxHitPoints() +
                ", MP: " + hero.getMagicPoints() + "/" + hero.getMaxMagicPoints();
        combatPanel.updateHeroInfo(heroInfo);
    }

    private void updateEnemyInfo() {
        String enemyInfo = enemy.getName() + " - HP: " + enemy.getHitPoints() + "/" + enemy.getMaxHitPoints();
        combatPanel.updateEnemyInfo(enemyInfo);
    }

    // Method to switch from CombatPanel to GamePanel
    public void switchToGamePanel() {
        // Assuming we have a way to switch to the GamePanel from wherever the CombatController is being used
        // You may need to use a JFrame or another method to handle panel switching
        gamePanel.setVisible(true);  // Make GamePanel visible
        combatPanel.setVisible(false);  // Hide CombatPanel
    }
}
