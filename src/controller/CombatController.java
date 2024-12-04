package controller;

import model.Combat.AttackResult;
import model.Combat.CombatEngine;
import model.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacters.Hero;
import model.DungeonCharacters.Mage;
import model.DungeonCharacters.Priestess;
import view.CombatPanel;
import javax.swing.Timer;

public class CombatController {
    private final CombatEngine combatEngine;
    private final CombatPanel combatPanel;
    private final Hero hero;
    private final DungeonCharacter enemy;

    public CombatController(Hero hero, DungeonCharacter enemy) {
        this.hero = hero;
        this.enemy = enemy;
        this.combatEngine = new CombatEngine();
        this.combatPanel = new CombatPanel(this);

        updateHeroInfo();
        updateEnemyInfo();
        resetTurns();
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
                    AttackResult myResult = combatEngine.attack(hero, enemy);
                    combatPanel.attackAnimation(true, myResult);
                    updateHeroInfo();
                    updateEnemyInfo();
                    switch(myResult) {
                        case MISS:
                            combatPanel.logAction("Attack Missed!!");
                            break;
                        case BLOCK:
                            combatPanel.logAction("Attack was Blocked!!");
                            break;
                        case BONK:
                            combatPanel.logAction("LOW MP!");
                            combatPanel.logAction(hero.getName() + " bonks " + enemy.getName());
                            break;
                        case HIT:
                            combatPanel.logAction("Attack Landed!!");
                            break;
                    }
                    if (enemy.getHitPoints() <= 0) {
                        actionTimer.stop(); // Stop the timer as victory is handled
                        handleVictory();
                    } else {
                        if (combatEngine.getNumberOfTurns() > 0 && combatEngine.isHeroFaster()) {
                            // Hero gets another turn
                            combatPanel.logAction(hero.getName() + " gets another turn!");
                            actionTimer.stop(); // Stop the timer after completing the sequence
                            //combatPanel.reactivateButtons();
                        } else {
                            state[0]++; // Proceed to enemy counterattack
                        }
                    }
                    break;

                case 2: // Handle enemy counterattack
                    while (combatEngine.getNumberOfTurns() > 0 && !combatEngine.isHeroFaster()) {
                        handleEnemyCounterattack();
                    }
                    resetTurns();
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
        combatEngine.resetDefend(hero);
    }

    private void resetTurns() {
        int numberOfTurns;
        if (enemy.getAttackSpeed() > hero.getAttackSpeed()) {
            numberOfTurns = enemy.getAttackSpeed() / hero.getAttackSpeed();
            combatEngine.setHeroFaster(false);
            combatEngine.setNumberOfTurns(numberOfTurns, false);
        } else {
            numberOfTurns = hero.getAttackSpeed() / enemy.getAttackSpeed();
            combatEngine.setHeroFaster(true);
            combatEngine.setNumberOfTurns(numberOfTurns, true);
        }
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
        combatPanel.displayGameOver(hero.getName() + " has retreated from combat.");
    }

    public void handleVictory() {
        combatPanel.logAction(enemy.getName() + " has been slain!");
        combatPanel.deathAnimation(false);
        combatPanel.displayGameOver(enemy.getName() + " has been slain.");
    }

    private void handleEnemyCounterattack() {
        combatPanel.logAction(enemy.getName() + " counterattacks " + hero.getName());
        AttackResult myResult = combatEngine.attack(enemy, hero);
        combatPanel.attackAnimation(false, myResult);
        switch(myResult) {
            case MISS:
                combatPanel.logAction("Attack Missed!!");
                break;
            case BLOCK:
                combatPanel.logAction("Attack was Blocked!!");
                break;
            case HIT:
                combatPanel.logAction("Attack Landed!!");
                break;
        }
        updateHeroInfo();

        if (hero.getHitPoints() <= 0) {
            combatPanel.deathAnimation(true);
            combatPanel.displayGameOver(hero.getName() + " has been defeated!");
        }
    }

    private void updateHeroInfo() {
        String heroInfo;
        if (hero instanceof Mage mageHero){
            heroInfo = mageHero.getName() + " - HP: " + mageHero.getHitPoints() + "/" + mageHero.getMaxHitPoints() +
                    ", MP: " + mageHero.getMagicPoints() + "/" + mageHero.getMaxMagicPoints();
        } else if (hero instanceof Priestess priestessHero){
            heroInfo = priestessHero.getName() + " - HP: " + priestessHero.getHitPoints() + "/" + priestessHero.getMaxHitPoints() +
                    ", MP: " + priestessHero.getMagicPoints() + "/" + priestessHero.getMaxMagicPoints();
        } else {
            heroInfo = hero.getName() + " - HP: " + hero.getHitPoints() + "/" + hero.getMaxHitPoints() +
                    ", MP: " + 0 + "/" + 0;
        }
        combatPanel.updateHeroInfo(heroInfo);
    }

    private void updateEnemyInfo() {
        String enemyInfo = enemy.getName() + " - HP: " + enemy.getHitPoints() + "/" + enemy.getMaxHitPoints();
        combatPanel.updateEnemyInfo(enemyInfo);
    }

    // Method to switch from CombatPanel to GamePanel
    public void switchToGamePanel() {
        //gamePanel.setVisible(true);  // Make GamePanel visible
        //combatPanel.setVisible(false);  // Hide CombatPanel
        combatPanel.dispose();
    }
}