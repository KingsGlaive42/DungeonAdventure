package controller;

import model.Combat.AttackResult;
import model.Combat.CombatEngine;
import model.DungeonCharacters.*;
import model.PlayerInventory.HealingPotion;
import model.PlayerInventory.Item;
import model.PlayerInventory.ItemType;
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
    private final CombatEngine myCombatEngine;
    private CombatPanel myCombatPanel;
    private Hero myHero;
    private Monster myEnemy;
    private CardLayoutManager myCardLayoutManager;
    private GameController myGameController;

    /**
     * Controller Constructor.
     *
     * @param theHero Hero class
     * @param theEnemy Enemy class
     */
    public CombatController(final Hero theHero, Monster theEnemy) {
        myHero = theHero;
        myEnemy = theEnemy;
        myCombatEngine = new CombatEngine();
        myCombatPanel = null;
        resetTurns();
    }

    public void setCombatPanel(final CombatPanel theCombatPanel) {
        myCombatPanel = theCombatPanel;
        updateHeroInfo();
        updateEnemyInfo();
    }

    public void setHero(final Hero theHero) {
        this.myHero = theHero;
    }

    public void setGameController(final GameController theGameController) {
        myGameController = theGameController;
    }

    public void startCombat(final Monster theEnemy) {
        myEnemy = theEnemy;
        updateHeroInfo();
        updateEnemyInfo();
        myCombatPanel.clearState();
        // Initialize combat logic, e.g., start turns
        System.out.println("Combat started between " + myHero.getName() + " and " + myEnemy.getName());
    }


    public void setCardLayoutManager(final CardLayoutManager theCardLayoutManager) {
        myCardLayoutManager = theCardLayoutManager;
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
                    myCombatPanel.logAction(myHero.getName() + " attacks " + myEnemy.getName());
                    state[0]++;
                    break;

                case 1: // Perform the attack
                    AttackResult myResult = myCombatEngine.attack(myHero, myEnemy);
                    myCombatPanel.attackAnimation(true, myResult);
                    updateHeroInfo();
                    updateEnemyInfo();
                    switch(myResult) {
                        case MISS:
                            myCombatPanel.logAction("Attack Missed!!");
                            break;
                        case BLOCK:
                            myCombatPanel.logAction("Attack was Blocked!!");
                            break;
                        case BONK:
                            myCombatPanel.logAction("LOW MP!");
                            myCombatPanel.logAction(myHero.getName() + " bonks " + myEnemy.getName());
                            break;
                        case HIT:
                            myCombatPanel.logAction("Attack Landed!!");
                            break;
                        case HEAL:
                            myCombatPanel.logAction("Attack Landed!!");
                            myCombatPanel.logAction(myEnemy.getName() + " healed itself");
                            break;
                    }
                    if (myEnemy.getHitPoints() <= 0) {
                        actionTimer.stop(); // Stop the timer as victory is handled
                        handleVictory();
                    } else {
                        if (myCombatEngine.getNumberOfTurns() > 0 && myCombatEngine.isHeroFaster()) {
                            // Hero gets another turn
                            myCombatPanel.logAction(myHero.getName() + " gets another turn!");
                            actionTimer.stop();
                            myCombatPanel.reactivateButtons();
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
        myCombatPanel.logAction(myHero.getName() + " is defending.");
        myCombatEngine.handleDefend(myEnemy);
        handleEnemyCounterattack();
        myCombatEngine.resetDefend(myEnemy);
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
            String theType = myHero.getClass().getSimpleName();
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
            if (myEnemy == null) {
                theType = "Skeleton"; //Default
            } else {
                theType = myEnemy.getClass().getSimpleName();
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
        if (/*enemy.getAttackSpeed()*/5 > myHero.getAttackSpeed()) {
            numberOfTurns = /*enemy.getAttackSpeed()*/5 / myHero.getAttackSpeed();
            myCombatEngine.setHeroFaster(false);
            myCombatEngine.setNumberOfTurns(numberOfTurns, false);
        } else {
            numberOfTurns = myHero.getAttackSpeed() / 5/*enemy.getAttackSpeed()*/;
            myCombatEngine.setHeroFaster(true);
            myCombatEngine.setNumberOfTurns(numberOfTurns, true);
        }
    }

    /**
     * This method handles the use of character
     * special skills.
     */
    public void handleSpecialSkill() {
        if (myHero instanceof Priestess) {
            myCombatPanel.showHealOptions();
        } else {
            Timer actionTimer = new Timer(500, null);
            final int[] state = {0};

            actionTimer.addActionListener(e -> {
                switch (state[0]) {
                    case 0:
                        switch (myHero.getClass().getSimpleName()) {
                            case "Warrior":
                                myCombatPanel.logAction(myHero.getName() + " uses Crushing Blow on " + myEnemy.getName());
                                break;

                            case "Mage":
                                myCombatPanel.logAction(myHero.getName() + " casts Ultima on " + myEnemy.getName());
                                break;

                            case "Thief":
                                myCombatPanel.logAction(myHero.getName() + " sneaks up on " + myEnemy.getName());
                                break;

                            case "Berserker":
                                myCombatPanel.logAction(myHero.getName() + " uses Blood Fiend on " + myEnemy.getName());
                                break;
                        }
                        AttackResult myResult = myCombatEngine.performSpecialSkill(myHero, myEnemy);
                        myCombatPanel.attackAnimation(true, myResult);
                        switch (myResult) {
                            case MISS:
                                if (myHero instanceof Thief) {
                                    myCombatPanel.logAction(myHero.getName() + " was caught and attacked!");
                                } else {
                                    myCombatPanel.logAction("Attack Missed!!");
                                }
                                break;
                            case BLOCK:
                                myCombatPanel.logAction("Attack was Blocked!!");
                                break;
                            case BONK:
                                myCombatPanel.logAction("LOW MP!");
                                myCombatPanel.logAction(myHero.getName() + " bonks " + myEnemy.getName());
                                break;
                            case HIT:
                                myCombatPanel.logAction("Attack Landed!!");
                                break;
                            case HALF_HIT:
                                if (myHero instanceof Thief) {
                                    myCombatPanel.logAction(myEnemy.getName() + " blocked second attack!");
                                } else {
                                    myCombatPanel.logAction(myEnemy.getName() + " barely dodged!");
                                }
                                break;
                        }
                        updateHeroInfo();
                        updateEnemyInfo();
                        state[0]++;
                        break;
                    case 1:
                        if (myEnemy.getHitPoints() <= 0) {
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
        int health = myHero.getHitPoints();
        Item hPotion = myGameController.getInventory().getItem(ItemType.HEALING_POTION);
        if (hPotion != null) {
            myGameController.getInventory().useItem(hPotion, myGameController.getPlayer().getHeroClass(), myGameController.getDungeon(), myGameController.getUI());
        }
        if (myHero.getHitPoints() == health) {
            myCombatPanel.logAction("No healing potions in inventory!");
        } else {
            myCombatPanel.logAction(myHero.getName() + " used a healing potion!");
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
        AttackResult result = ((Priestess) myHero).useSpecialSkill(healRange);
        myCombatPanel.logAction(myHero.getName() + " uses heal");
        if (result == AttackResult.BONK) {
            myCombatPanel.logAction("Low MP!!");
            myCombatPanel.logAction("Healing Failed");
        }
        // Show main action buttons after healing
        myCombatPanel.showActionButtons();
        handleEnemyCounterattack();
    }

    /**
     * This method handles retreating from battle.
     */
    public void handleRetreat() {
        myCombatPanel.logAction(myHero.getName() + " is retreating from combat!");
        myCombatPanel.displayGameOver(myHero.getName() + " has retreated from combat.");
    }

    /**
     * This method handles combat end, whether through defeating the enemy or the hero's death.
     */
    public void handleVictory() {
        myCombatPanel.logAction(myEnemy.getName() + " has been slain!");
        myCombatPanel.deathAnimation(false);
        myCombatPanel.displayGameOver(myEnemy.getName() + " has been slain.");
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
                    myCombatPanel.logAction(myEnemy.getName() + " attacks " + myHero.getName());
                    state[0]++;
                    break;

                case 1: // Perform the attack
                    AttackResult myResult = myCombatEngine.attack(myEnemy, myHero);
                    myCombatPanel.attackAnimation(false, myResult);
                    updateHeroInfo();
                    updateEnemyInfo();
                    switch(myResult) {
                        case MISS:
                            myCombatPanel.logAction("Attack Missed!!");
                            break;
                        case BLOCK:
                            myCombatPanel.logAction("Attack was Blocked!!");
                            break;
                        case HIT:
                            myCombatPanel.logAction("Attack Landed!!");
                            break;
                    }
                    if (myHero.getHitPoints() <= 0) {
                        actionTimer.stop(); // Stop the timer as victory is handled
                        myCombatPanel.deathAnimation(true);
                        myCombatPanel.displayGameOver(myHero.getName() + " has been defeated!");
                    } else {
                        if (myCombatEngine.getNumberOfTurns() > 0 && !myCombatEngine.isHeroFaster()) {
                            System.out.println(myCombatEngine.getNumberOfTurns());
                            // Hero gets another turn
                            myCombatPanel.logAction(myEnemy.getName() + " gets another turn!");
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
        if (myHero instanceof Mage mageHero){
            heroInfo = mageHero.getName() + " - HP: " + mageHero.getHitPoints() + "/" + mageHero.getMaxHitPoints() +
                    ", MP: " + mageHero.getMagicPoints() + "/" + mageHero.getMaxMagicPoints();
        } else if (myHero instanceof Priestess priestessHero){
            heroInfo = priestessHero.getName() + " - HP: " + priestessHero.getHitPoints() + "/" + priestessHero.getMaxHitPoints() +
                    ", MP: " + priestessHero.getMagicPoints() + "/" + priestessHero.getMaxMagicPoints();
        } else {
            heroInfo = myHero.getName() + " - HP: " + myHero.getHitPoints() + "/" + myHero.getMaxHitPoints() +
                    ", MP: " + 0 + "/" + 0;
        }
        myCombatPanel.updateHeroInfo(heroInfo);
    }

    /**
     * This method updates monster info after an action.
     */
    private void updateEnemyInfo() {
        if (myEnemy == null) {
            myCombatPanel.updateEnemyInfo("");
            return;
        }
        String enemyInfo = myEnemy.getName() + " - HP: " + myEnemy.getHitPoints() + "/" + myEnemy.getMaxHitPoints();
        myCombatPanel.updateEnemyInfo(enemyInfo);
    }

    /**
     * This method switches to game panel when combat is finished
     */
    public void switchToGamePanel() {
        if (myHero.getHitPoints() == 0) {
            Window window = SwingUtilities.getWindowAncestor(myCombatPanel);
            if (window instanceof JFrame) {
                ((JFrame) window).dispose();
            }
        } else {
            myCardLayoutManager.switchToGamePanel();
        }
    }
}