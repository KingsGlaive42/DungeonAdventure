package model.DungeonCharacters;

import model.Combat.AttackResult;
import model.GameConfig;

import java.io.Serializable;
import java.util.Random;

/**
 * This is the Mage Class
 *
 * @author Thomas Le
 */
public class Mage extends Hero implements Serializable {
    private int myMagicPoints;
    private int myMaxMagicPoints;

    /**
     * This is the mage constructor
     *
     * @param theName Name of Mage.
     */
    public Mage(final String theName) {
        super(theName, 75, 30, 50, 4, 0.8, 0.3);
        myMagicPoints = 50;
        myMaxMagicPoints = 50;
    }

    /**
     * This method is the Mage's unique attack method that uses
     * MP for basic attacks.
     *
     * @param theTarget The character being attacked.
     * @return result of the attack.
     */
    @Override
    public AttackResult attack(final DungeonCharacter theTarget) {
        if(getMagicPoints() <= 0) {
            System.out.println("Out of MP!");
            System.out.println(getName() + " bonks " + theTarget.getName());
            theTarget.takeDamage(10);
            return AttackResult.BONK;
        }
        Random rand = new Random();
        if ((rand.nextInt(10) + 1) / 10.0 <= getChanceToHit() || GameConfig.isInfiniteDamage()) {
            int damage = calculateDamage(getMinDamage(), getMaxDamage());

            if (GameConfig.isInfiniteDamage()) {
                damage = 9999999;
            }

            theTarget.takeDamage(damage);

            return AttackResult.HIT;
        } else {
            return AttackResult.MISS;
        }
    }

    /**
     * This method is the mage's unique special skill.
     *
     * @param theTarget the target of the attack.
     * @return result of the attack.
     */
    @Override
    public AttackResult useSpecialSkill(final DungeonCharacter theTarget) {
        if (getMagicPoints() < 20) {
            return AttackResult.BONK;
        }
        setMagicPoints(getMagicPoints()-20);
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 1) { //10% chance to insta-kill enemy
            int damage = 999;
            return theTarget.takeDamage(damage);
        } else if (rand.nextInt(10) + 1 <= 3) {
            int damage = calculateDamage(getMinDamage(), getMaxDamage());
            theTarget.takeDamage(damage);
            return AttackResult.HALF_HIT;
        } else {
            return AttackResult.MISS;
        }
    }

    /**
     * This method returns the current amount of MP.
     *
     * @return current MP amount.
     */
    public int getMagicPoints() { return myMagicPoints; }

    /**
     * This method sets the current MP amount.
     *
     * @param theMagicPoints new MP amount.
     */
    public void setMagicPoints(final int theMagicPoints) {
        this.myMagicPoints = Math.min(theMagicPoints, myMaxMagicPoints); // Ensures MP does not exceed max
    }

    /**
     * This method gets the max MP amount.
     *
     * @return Max MP amount.
     */
    public int getMaxMagicPoints() { return myMaxMagicPoints; }

}
