package model.DungeonCharacters;

import model.Combat.AttackResult;

import java.io.Serializable;
import java.util.Random;

/**
 * This is the Dungeon Character Class
 *
 * @author Thomas Le
 */
public abstract class DungeonCharacter implements Serializable {
    private final String myName;
    private int myCurrentHitPoints;
    private int myMinDamage;
    private int myMaxDamage;
    private final int myAttackSpeed;
    private double myChanceToHit;
    private final Random rand;
    private final int myMaxHitPoints;

    /**
     * This is a constructor method that creates a Dungeon Character
     * with the given parameters.
     *
     * @param theName Name of Dungeon Character.
     * @param theHitPoints HP of Dungeon Character.
     * @param theMinDamage MinDamage of Dungeon Character.
     * @param theMaxDamage MaxDamage of Dungeon Character.
     * @param theAttackSpeed Attack Speed of Dungeon Character.
     * @param theChanceToHit Chance to Hit of Dungeon Character.
     */
    public DungeonCharacter(final String theName, final int theHitPoints, final int theMinDamage, final int theMaxDamage,
                               final int theAttackSpeed, final double theChanceToHit) {
        myName = theName;
        myCurrentHitPoints = theHitPoints;
        myMaxHitPoints = theHitPoints;
        myMinDamage = theMinDamage;
        myMaxDamage = theMaxDamage;
        myAttackSpeed = theAttackSpeed;
        myChanceToHit = theChanceToHit;
        rand = new Random();
    }

    /**
     * This is the attack method that attacks the
     * given target.
     *
     * @param theTarget Character being attacked.
     * @return Result of the attack.
     */
    public AttackResult attack(final DungeonCharacter theTarget) {
        if ((rand.nextInt(10) + 1) / 10.0 <= getChanceToHit()) {
            int damage = calculateDamage(getMinDamage(), getMaxDamage());
            return theTarget.takeDamage(damage);
        } else {
            return AttackResult.MISS;
        }
    }

    /**
     * This is the takeDamage method that lowers the current
     * HP by the given damage number.
     *
     * @param theDamage Damage taken
     * @return attack result.
     */
    public AttackResult takeDamage(final int theDamage) {
        int newHitPoints = getHitPoints() - theDamage;
        setHitPoints(Math.max(newHitPoints, 0));
        return AttackResult.HIT;
    }

    /**
     * This method calculates the damage amount.
     *
     * @param theMinDamage MinDamage Character can do.
     * @param theMaxDamage MaxDamage Character can do
     * @return damage number.
     */
    public int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }

    /**
     * This method returns the name of the character.
     *
     * @return Character name.
     */
    public String getName() { return myName; }

    /**
     * This method returns current hit points.
     *
     * @return current hit points.
     */
    public int getHitPoints() { return myCurrentHitPoints; }

    /**
     * This method sets hit points.
     *
     * @param theCurrentHitPoints New Current Hit Points.
     */
    public void setHitPoints(final int theCurrentHitPoints) {
        this.myCurrentHitPoints = Math.min(theCurrentHitPoints, myMaxHitPoints); // Ensures HP does not exceed max
    }

    /**
     * This method gets the attack speed.
     *
     * @return attack speed.
     */
    public int getAttackSpeed() { return myAttackSpeed; }

    /**
     * This method returns min damage.
     *
     * @return min damage.
     */
    public int getMinDamage() { return myMinDamage; }

    /**
     * This method sets min damage.
     *
     * @param theMinDamage new min damage.
     */
    public void setMinDamage(final int theMinDamage) { this.myMinDamage = theMinDamage; }

    /**
     * This method gets max damage.
     *
     * @return Max Damage.
     */
    public int getMaxDamage() { return myMaxDamage; }

    /**
     * This method sets max damage.
     *
     * @param theMaxDamage new max damage.
     */
    public void setMaxDamage(final int theMaxDamage) { this.myMaxDamage = theMaxDamage; }

    /**
     * This method gets chance to hit.
     *
     * @return returns chance to hit.
     */
    public double getChanceToHit() { return myChanceToHit; }

    /**
     * This method gets max hit points.
     *
     * @return max hit points.
     */
    public int getMaxHitPoints() { return myMaxHitPoints; }

    /**
     * This method sets the chance to hit for testing.
     *
     * @param theChanceToHit New Chance To Hit.
     */
    public void setChanceToHit(final double theChanceToHit) {
        myChanceToHit = theChanceToHit;
    }
}
