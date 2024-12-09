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
    private final double myChanceToHit;
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
    public int getHitPoints() { return myCurrentHitPoints; }
    public void setHitPoints(final int theCurrentHitPoints) {
        this.myCurrentHitPoints = Math.min(theCurrentHitPoints, myMaxHitPoints); // Ensures HP does not exceed max
    }
    public int getAttackSpeed() { return myAttackSpeed; }
    public int getMinDamage() { return myMinDamage; }
    public void setMinDamage(final int theMinDamage) { this.myMinDamage = theMinDamage; }
    public int getMaxDamage() { return myMaxDamage; }
    public void setMaxDamage(final int theMaxDamage) { this.myMaxDamage = theMaxDamage; }
    public double getChanceToHit() { return myChanceToHit; }
    public int getMaxHitPoints() { return myMaxHitPoints; }

}
