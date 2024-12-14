/**
 * Represents a Monster in the dungeon game.
 * A Monster is a type of DungeonCharacter with additional healing abilities and position tracking.
 * @author Aileen
 */

package model.DungeonCharacters;

import model.Combat.AttackResult;

import java.awt.image.BufferedImage;
import java.io.Serial;
import java.io.Serializable;

public abstract class Monster extends DungeonCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** The probability that the monster heals after taking damage. */
    private final double myHealChance;

    /** The minimum amount of hit points the monster can heal. */
    private final int myMinHeal;

    /** The maximum amount of hit points the monster can heal. */
    private final int myMaxHeal;

    /** The x-coordinate of the monster's position in the dungeon. */
    private int monsterX;

    /** The y-coordinate of the monster's position in the dungeon. */
    private int monsterY;

    /**
     * Constructs a Monster with the specified attributes.
     *
     * @param theName         the name of the monster
     * @param theHitPoints    the initial hit points of the monster
     * @param theMinDamage    the minimum damage the monster can deal
     * @param theMaxDamage    the maximum damage the monster can deal
     * @param theAttackSpeed  the attack speed of the monster
     * @param theChanceToHit  the probability that the monster hits its target
     * @param theHealChance   the probability that the monster heals after taking damage
     * @param theMinHeal      the minimum amount of hit points the monster can heal
     * @param theMaxHeal      the maximum amount of hit points the monster can heal
     */
    public Monster(String theName, int theHitPoints, int theMinDamage,
                   int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                   int theMinHeal, int theMaxHeal) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit);
        this.myHealChance = theHealChance;
        this.myMinHeal = theMinHeal;
        this.myMaxHeal = theMaxHeal;
    }

    /**
     * Handles damage taken by the monster. If the monster heals after taking damage,
     * it will attempt to restore some hit points based on its heal chance.
     *
     * @param theDamage the amount of damage taken
     * @return an AttackResult indicating the result of the damage (e.g., HIT or HEAL)
     */
    @Override
    public AttackResult takeDamage(final int theDamage) {
        int newHitPoints = getHitPoints() - theDamage;
        if (newHitPoints > 0) {
            setHitPoints(newHitPoints);
            int healAmount = heal();
            if (healAmount > 0) {
                return AttackResult.HEAL;
            }
        } else {
            setHitPoints(0);
        }
        return AttackResult.HIT;
    }

    /**
     * Attempts to heal the monster based on its heal chance.
     * If successful, restores a random amount of hit points within the range of min and max heal.
     *
     * @return the amount of hit points healed, or 0 if no healing occurs
     */
    public int heal() {
        if (Math.random() < myHealChance) {
            int healAmount = myMinHeal + (int) (Math.random() * (myMaxHeal - myMinHeal + 1));
            this.setHitPoints(Math.min(this.getHitPoints() + healAmount, getMaxHitPoints()));
            return healAmount;
        }
        return 5;
    }

    /**
     * Sets the position of the monster in the dungeon.
     *
     * @param theX the x-coordinate of the monster's position
     * @param theY the y-coordinate of the monster's position
     */
    public void setPosition(final int theX, final int theY) {
        this.monsterX = theX;
        this.monsterY = theY;
    }

    /**
     * Gets the monster's heal chance.
     *
     * @return the heal chance
     */
    public double getMyHealChance() {
        return myHealChance;
    }

    /**
     * Gets the minimum amount of hit points the monster can heal.
     *
     * @return the minimum heal amount
     */
    public int getMyMinHeal() {
        return myMinHeal;
    }

    /**
     * Gets the maximum amount of hit points the monster can heal.
     *
     * @return the maximum heal amount
     */
    public int getMyMaxHeal() {
        return myMaxHeal;
    }

    /**
     * Gets the x-coordinate of the monster's position.
     *
     * @return the x-coordinate
     */
    public int getMonsterX() {
        return monsterX;
    }

    /**
     * Gets the y-coordinate of the monster's position.
     *
     * @return the y-coordinate
     */
    public int getMonsterY() {
        return monsterY;
    }

    /**
     * Gets the sprite image representing the monster.
     *
     * @return a BufferedImage representing the monster's sprite
     */
    public abstract BufferedImage getSprite();

    /**
     * Creates a clone of the monster.
     *
     * @return a new Monster instance that is a copy of the current monster
     */
    public abstract Monster cloneMonster();
}
