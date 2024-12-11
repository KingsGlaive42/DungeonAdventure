package controller;

import model.Combat.AttackResult;
import model.Combat.CombatEngine;
import model.DungeonCharacters.*;
import model.PlayerInventory.HealingPotion;
import view.CardLayoutManager;
import view.CombatPanel;

import javax.swing.*;
import java.awt.*;

/**
 * This is the controller class that connects
 * the combat panel and combat model.
 *
 * @author Thomas Le
 */
public class CombatController {
    private final CombatEngine combatEngine;
    private CombatPanel combatPanel;
    private Hero hero;
    private Monster enemy;
    private CardLayoutManager cardLayoutManager;
    private GameController gameController;

    /**
     * Controller Constructor.
     *
     * @param theHero Hero class
     * @param theEnemy Enemy class
     */
    public CombatController(final Hero theHero, Monster theEnemy) {
        hero = theHero;
        enemy = theEnemy;
        combatEngine = new CombatEngine();
        combatPanel = null;
        resetTurns();
    }

    public void setCombatPanel(final CombatPanel theCombatPanel) {
        combatPanel = theCombatPanel;
        updateHeroInfo();
        updateEnemyInfo();
    }

    public void setHero(final Hero theHero) {
        this.hero = theHero;
    }

    public void setGameController(final GameController theGameController) {
        gameController = theGameController;
    }

    public void startCombat(final Monster theEnemy) {
        enemy = theEnemy;
        updateHeroInfo();
        updateEnemyInfo();
        combatPanel.clearState();
        // Initialize combat logic, e.g., start turns
        System.out.println("Combat started between " + hero.getName() + " and " + enemy.getName());
    }


    public void setCardLayoutManager(final CardLayoutManager theCardLayoutManager) {
        cardLayoutManager = theCardLayoutManager;
    }

    /**
     * This method handles attacking
     */
    public void handleAttack() {
        resetTurns();
        Timer actionTimer = new Timer(500, null);
        final int[] state = {0};

        //Timer for pacing attacks
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
                        case HEAL:
                            combatPanel.logAction("Attack Landed!!");
                            combatPanel.logAction(enemy.getName() + " healed itself");
                            break;
                    }
                    if (enemy.getHitPoints() <= 0) {
                        actionTimer.stop(); // Stop the timer as victory is handled
                        handleVictory();
                    } else {
                        if (combatEngine.getNumberOfTurns() > 0 && combatEngine.isHeroFaster()) {
                            // Hero gets another turn
                            combatPanel.logAction(hero.getName() + " gets another turn!");
                            actionTimer.stop();
                            combatPanel.reactivateButtons();
                        } else {
                            delayReset(actionTimer, state);
                        }
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

    /**
     * This method delays the looping in repeated attacks
     * so animations can complete.
     *
     * @param actionTimer Main Timer that needs to be paused.
     * @param state last saved state of animation.
     */
    private void delayReset(Timer actionTimer, int[] state) {
        actionTimer.stop(); // Stop primary timer
        Timer delayTimer = new Timer(500, null); // Add a longer pause (0.5 seconds)
        delayTimer.addActionListener(delayEvent -> {
            state[0]++; // Proceed to enemy counterattack
            actionTimer.start(); // Restart the main timer
            delayTimer.stop(); // Stop the delay timer
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    /**
     * This method handles defending.
     */
    public void handleDefend() {
        combatPanel.logAction(hero.getName() + " is defending.");
        combatEngine.handleDefend(enemy);
        handleEnemyCounterattack();
        combatEngine.resetDefend(enemy);
    }

    /**
     * This method sets the correct sprite for the hero/enemy.
     *
     * @param theHero True if hero sprite is being set, false if enemy is being set.
     * @return the new image.
     */
    public ImageIcon setImage(final boolean theHero) {
        ImageIcon myImageIcon = null;
        if (theHero) {
            String theType = hero.getClass().getSimpleName();
            myImageIcon = switch (theType) {
                case "Priestess" -> new ImageIcon("src/resources/assets/player/Celes.png");
                case "Mage" -> new ImageIcon("src/resources/assets/player/Terra.png");
                case "Thief" -> new ImageIcon("src/resources/assets/player/Locke.png");
                case "Warrior" -> new ImageIcon("src/resources/assets/player/Edgar.png");
                case "Berserker" -> new ImageIcon("src/resources/assets/player/Setzer.png");
                default -> myImageIcon;
            };
        } else {
            String theType;
            if (enemy == null) {
                theType = "Skeleton"; //Default
            } else {
                theType = enemy.getClass().getSimpleName();
            }
            System.out.println(theType);
            myImageIcon = switch (theType) {
                case "Skeleton" -> new ImageIcon("src/resources/assets/Monsters/Skeleton.png");
                case "Ogre" ->  new ImageIcon("src/resources/assets/Monsters/Ogre.png");
                case "Gremlin" -> new ImageIcon("src/resources/assets/Monsters/Gremlin.png");
                default -> myImageIcon;
            };
        }
        return myImageIcon;
    }

    /**
     * This method resets turns based on attack speed after
     * the character with the faster attack speed has used up
     * their turns.
     */
    private void resetTurns() {
        int numberOfTurns;
        if (/*enemy.getAttackSpeed()*/5 > hero.getAttackSpeed()) {
            numberOfTurns = /*enemy.getAttackSpeed()*/5 / hero.getAttackSpeed();
            combatEngine.setHeroFaster(false);
            combatEngine.setNumberOfTurns(numberOfTurns, false);
        } else {
            numberOfTurns = hero.getAttackSpeed() / 5/*enemy.getAttackSpeed()*/;
            combatEngine.setHeroFaster(true);
            combatEngine.setNumberOfTurns(numberOfTurns, true);
        }
    }

    /**
     * This method handles the use of character
     * special skills.
     */
    public void handleSpecialSkill() {
        if (hero instanceof Priestess) {
            combatPanel.showHealOptions();
        } else {
            Timer actionTimer = new Timer(500, null);
            final int[] state = {0};

            actionTimer.addActionListener(e -> {
                switch (state[0]) {
                    case 0:
                        switch (hero.getClass().getSimpleName()) {
                            case "Warrior":
                                combatPanel.logAction(hero.getName() + " uses Crushing Blow on " + enemy.getName());
                                break;

                            case "Mage":
                                combatPanel.logAction(hero.getName() + " casts Ultima on " + enemy.getName());
                                break;

                            case "Thief":
                                combatPanel.logAction(hero.getName() + " sneaks up on " + enemy.getName());
                                break;

                            case "Berserker":
                                combatPanel.logAction(hero.getName() + " uses Blood Fiend on " + enemy.getName());
                                break;
                        }
                        AttackResult myResult = combatEngine.performSpecialSkill(hero, enemy);
                        combatPanel.attackAnimation(true, myResult);
                        switch (myResult) {
                            case MISS:
                                if (hero instanceof Thief) {
                                    combatPanel.logAction(hero.getName() + " was caught and attacked!");
                                } else {
                                    combatPanel.logAction("Attack Missed!!");
                                }
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
                            case HALF_HIT:
                                if (hero instanceof Thief) {
                                    combatPanel.logAction(enemy.getName() + " blocked second attack!");
                                } else {
                                    combatPanel.logAction(enemy.getName() + " barely dodged!");
                                }
                                break;
                        }
                        updateHeroInfo();
                        updateEnemyInfo();
                        state[0]++;
                        break;
                    case 1:
                        if (enemy.getHitPoints() <= 0) {
                            handleVictory();
                        } else {
                            handleEnemyCounterattack();
                        }
                        actionTimer.stop();
                        break;
                }
            });
            actionTimer.setRepeats(true); // Ensure the timer repeats for each state
            actionTimer.start(); // Start the timer
        }
    }

    public void handlePotion() {
        int health = hero.getHitPoints();
        gameController.getInventory().useItem(new HealingPotion(), gameController.getPlayer().getHeroClass(), gameController.getDungeon());
        if (hero.getHitPoints() == health) {
            combatPanel.logAction("No Potions!");
        } else {
            combatPanel.logAction(hero.getName() + " used a Potion!");
            updateHeroInfo();
        }
        handleEnemyCounterattack();
    }

    /**
     * This method handles the Priestess'
     * healing skills.
     *
     * @param healRange The range of healing.
     */
    public void handleHeal(int healRange) {
        AttackResult result = ((Priestess) hero).useSpecialSkill(healRange);
        combatPanel.logAction(hero.getName() + " uses heal");
        if (result == AttackResult.BONK) {
            combatPanel.logAction("Low MP!!");
            combatPanel.logAction("Healing Failed");
        }
        // Show main action buttons after healing
        combatPanel.showActionButtons();
        handleEnemyCounterattack();
    }

    /**
     * This method handles retreating from battle.
     */
    public void handleRetreat() {
        combatPanel.logAction(hero.getName() + " is retreating from combat!");
        combatPanel.displayGameOver(hero.getName() + " has retreated from combat.");
    }

    /**
     * This method handles combat end, whether through defeating the enemy or the hero's death.
     */
    public void handleVictory() {
        combatPanel.logAction(enemy.getName() + " has been slain!");
        combatPanel.deathAnimation(false);
        combatPanel.displayGameOver(enemy.getName() + " has been slain.");
    }

    /**
     * This method handles the enemy counter-attack after
     * the hero attacks the enemy.
     */
    private void handleEnemyCounterattack() {
        Timer actionTimer = new Timer(500, null);
        final int[] state = {0};

        //Timer for pacing attacks
        actionTimer.addActionListener(e -> {
            switch (state[0]) {
                case 0: // Log the attack action
                    combatPanel.logAction(enemy.getName() + " attacks " + hero.getName());
                    state[0]++;
                    break;

                case 1: // Perform the attack
                    AttackResult myResult = combatEngine.attack(enemy, hero);
                    combatPanel.attackAnimation(false, myResult);
                    updateHeroInfo();
                    updateEnemyInfo();
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
                    if (hero.getHitPoints() <= 0) {
                        actionTimer.stop(); // Stop the timer as victory is handled
                        combatPanel.deathAnimation(true);
                        combatPanel.displayGameOver(hero.getName() + " has been defeated!");
                    } else {
                        if (combatEngine.getNumberOfTurns() > 0 && !combatEngine.isHeroFaster()) {
                            System.out.println(combatEngine.getNumberOfTurns());
                            // Hero gets another turn
                            combatPanel.logAction(enemy.getName() + " gets another turn!");
                            delayReset(actionTimer, state);
                            handleEnemyCounterattack();
                        }
                    }
                    actionTimer.stop(); // Stop the timer after completing the sequence
                    break;
            }
        });
        actionTimer.setRepeats(true); // Ensure the timer repeats for each state
        actionTimer.start(); // Start the timer
    }

    /**
     * This method updates hero info after an action.
     */
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

    /**
     * This method updates monster info after an action.
     */
    private void updateEnemyInfo() {
        if (enemy == null) {
            combatPanel.updateEnemyInfo("");
            return;
        }
        String enemyInfo = enemy.getName() + " - HP: " + enemy.getHitPoints() + "/" + enemy.getMaxHitPoints();
        combatPanel.updateEnemyInfo(enemyInfo);
    }

    /**
     * This method switches to game panel when combat is finished
     */
    public void switchToGamePanel() {
        if (hero.getHitPoints() == 0) {
            Window window = SwingUtilities.getWindowAncestor(combatPanel);
            if (window instanceof JFrame) {
                ((JFrame) window).dispose();
            }
        } else {
            cardLayoutManager.switchToGamePanel();
        }
    }
}