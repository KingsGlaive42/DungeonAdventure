package controller;

import model.Combat.CombatEngine;
import model.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacters.Hero;
import model.DungeonCharacters.Priestess;
import view.CombatPanel;
import view.GamePanel;
import javax.swing.Timer;

public class CombatController {
    private final CombatEngine combatEngine;
    private final CombatPanel combatPanel;
    private final GamePanel gamePanel;  // Reference to the GamePanel
    private final Hero hero;
    private final DungeonCharacter enemy;
    private Timer animationTimer;

    public CombatController(Hero hero, DungeonCharacter enemy) {
        gamePanel = new GamePanel();
        this.hero = hero;
        this.enemy = enemy;
        this.combatEngine = new CombatEngine();
        this.combatPanel = new CombatPanel(this);

        updateHeroInfo();
        updateEnemyInfo();
    }

    public CombatPanel getCombatPanel() {
        return combatPanel;
    }

    public void handleAttack() {
        Timer actionTimer = new Timer(500, null);
        final int[] state = {0};

        actionTimer.addActionListener(e -> {
            switch (state[0]) {
                case 0: // Log the attack action
                    combatPanel.logAction(hero.getName() + " attacks " + enemy.getName());
                    state[0]++;
                    break;

                case 1: // Perform the attack
                    combatEngine.attack(hero, enemy);
                    combatPanel.attackAnimation(true);
                    updateEnemyInfo();
                    if (enemy.getHitPoints() <= 0) {
                        actionTimer.stop(); // Stop the timer as victory is handled
                        handleVictory();
                    } else {
                        state[0]++;
                    }
                    break;

                case 2: // Handle enemy counterattack
                    handleEnemyCounterattack();
                    actionTimer.stop(); // Stop the timer after completing the sequence
                    break;
            }
        });
        actionTimer.setRepeats(true); // Ensure the timer repeats for each state
        actionTimer.start(); // Start the timer
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
                handleVictory();
            } else {
                handleEnemyCounterattack();
            }
        }
    }

    public void handleHeal(int healRange) {
        if (hero instanceof Priestess) {
            ((Priestess) hero).useSpecialSkill(hero, combatEngine, healRange);
            combatPanel.logAction(hero.getName() + " uses heal");
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

    public void handleVictory() {
        combatPanel.logAction(enemy.getName() + " has been slain!");
        combatPanel.deathAnimation(false);
        combatPanel.displayGameOver(enemy.getName() + " has been slain.");
    }

    private void handleEnemyCounterattack() {
        combatPanel.logAction(enemy.getName() + " counterattacks " + hero.getName());
        combatEngine.attack(enemy, hero);
        combatPanel.attackAnimation(false);
        if (combatEngine.didDefend()) {
            combatPanel.logAction(hero.getName() + " blocked the attack");
        }
        updateHeroInfo();

        if (hero.getHitPoints() <= 0) {
            combatPanel.displayGameOver(hero.getName() + " has been defeated!");
            combatPanel.deathAnimation(true);
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
        //gamePanel.setVisible(true);  // Make GamePanel visible
        //combatPanel.setVisible(false);  // Hide CombatPanel
        combatPanel.dispose();
    }
}
