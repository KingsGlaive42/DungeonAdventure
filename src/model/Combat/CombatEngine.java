package model.Combat;

import model.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacters.Hero;
import model.DungeonCharacters.Monster;

import static java.lang.Math.min;

/**
 * This is the Combat Engine class that houses
 * the model for the combat system.
 *
 * @author Thomas Le
 */
public class CombatEngine {
    private int numberOfTurns;
    private boolean isHeroFaster;

    /**
     * Combat Engine Constructor
     */
    public CombatEngine() {
        numberOfTurns = 0;
        isHeroFaster = true;
    }

    /**
     * This method handles attacking
     *
     * @param theAttacker The character attacking.
     * @param theDefender The character being attacked.
     * @return Result of the attack.
     */
    public AttackResult attack(final DungeonCharacter theAttacker, final DungeonCharacter theDefender) {
        if (isHeroFaster && theAttacker instanceof Hero || !isHeroFaster && theAttacker instanceof Monster) {
            numberOfTurns--;
        }
        return theAttacker.attack(theDefender);
    }

    /**
     * This method returns number of turns the faster opponent has.
     *
     * @return number of turns.
     */
    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    /**
     * This method returns whether the hero is the faster character.
     *
     * @return True if hero is faster, false if enemy is.
     */
    public boolean isHeroFaster() {
        return isHeroFaster;
    }

    /**
     * This method sets whether hero is faster/
     */
    public void setHeroFaster(final boolean theFasterOne) {
        isHeroFaster = theFasterOne;
    }

    /**
     * This method sets the number of turns.
     *
     * @param theNumberOfTurns number of turns.
     * @param theHeroFaster who is faster.
     */
    public void setNumberOfTurns(final int theNumberOfTurns, final boolean theHeroFaster) {
        numberOfTurns = theNumberOfTurns;
        isHeroFaster = theHeroFaster;
    }

    /**
     * This method handles using a character's special skill.
     *
     * @param theAttacker The hero using the skill.
     * @param theDefender The enemy.
     * @return result of using special skill
     */
    public AttackResult performSpecialSkill(final Hero theAttacker, final DungeonCharacter theDefender) {
        return theAttacker.useSpecialSkill(theDefender);
    }

    /**
     * This method increases the block chance when defending.
     *
     * @param theEnemy the hero defending.
     */
    public void handleDefend(final Monster theEnemy) {
        theEnemy.setMaxDamage(theEnemy.getMaxDamage() - 20);
        theEnemy.setMinDamage(theEnemy.getMinDamage() - 20);
    }

    /**
     * This method resets the defend chance.
     *
     * @param theEnemy the hero that was defending.
     */
    public void resetDefend(final Monster theEnemy) {
        theEnemy.setMaxDamage(theEnemy.getMaxDamage() + 20);
        theEnemy.setMinDamage(theEnemy.getMinDamage() + 20);
    }

    /**
     * This method heals the user when a potion is used.
     *
     * @param theTarget person who drank potion.
     */
    public void heal(final DungeonCharacter theTarget) {
        theTarget.setHitPoints(min(theTarget.getHitPoints() + 20, theTarget.getMaxHitPoints()));
    }
}
