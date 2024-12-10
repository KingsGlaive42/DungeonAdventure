package model.DungeonCharacters;

import model.Combat.AttackResult;
import model.GameConfig;

import java.io.Serializable;
import java.util.Random;

import static java.lang.Math.min;

/**
 * This is the Priestess Class.
 *
 * @author Thomas Le
 */
public class Priestess extends Hero implements Serializable {
    private int myMagicPoints;
    private final int myMaxMagicPoints;

    /**
     * Priestess Constructor that creates a Priestess
     * with the given parameters.
     *
     * @param theName Name of Priestess
     */
    public Priestess(final String theName) {
        super(theName, 75, 20, 40, 5, 0.7, 0.3);
        myMagicPoints = 50;
        myMaxMagicPoints = 50;
    }

    /**
     * This method overrides the base attack method,
     * making it so that the Priestess uses MP for
     * basic attacks.
     *
     * @param theTarget Character being attacked.
     * @return attack result.
     */
    @Override
    public AttackResult attack(final DungeonCharacter theTarget) {
        if(getMagicPoints() <= 0) {
            theTarget.takeDamage(10);
            return AttackResult.BONK;
        }
        Random rand = new Random();
        setMagicPoints(getMagicPoints() - 5);
        if ((rand.nextInt(10) + 1) / 10.0 <= getChanceToHit()) {
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
     * The Priestess' unique special skill
     * that heals herself with the given range.
     *
     * @param theHealRange Range of healing.
     * @return result of healing.
     */
    public AttackResult useSpecialSkill(final int theHealRange) {
        if (getMagicPoints() <= 0) {
            return AttackResult.BONK; //Reuse to show no MP.
        }

        int healAmount = 0;
        int mpCost = 0;

        switch (theHealRange) {
            case 1 -> { healAmount = 25; mpCost = 5; }
            case 2 -> { healAmount = 50; mpCost = 10; }
            case 3 -> { healAmount = 75; mpCost = 15; }
        }
        setHitPoints(min(getHitPoints() + healAmount, getMaxHitPoints()));
        setMagicPoints(getMagicPoints() - mpCost);
        return AttackResult.HEAL;
    }

    /**
     * This method returns the current MP value.
     *
     * @return Current MP value.
     */
    public int getMagicPoints() { return myMagicPoints; }

    /**
     * This method sets the current MP value.
     *
     * @param theMagicPoints new MP value.
     */
    public void setMagicPoints(final int theMagicPoints) {
        this.myMagicPoints = min(theMagicPoints, myMaxMagicPoints); // Ensures MP does not exceed max
    }

    /**
     * This method gets the max MP value.
     *
     * @return Max MP value.
     */
    public int getMaxMagicPoints() { return myMaxMagicPoints; }

}
