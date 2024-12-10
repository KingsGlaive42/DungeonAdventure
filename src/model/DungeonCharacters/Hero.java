package model.DungeonCharacters;

import model.Combat.AttackResult;
import model.GameConfig;

import java.io.Serializable;
import java.util.Random;

/**
 * This is the hero class that the user will use.
 *
 * @author Thomas Le
 */
public class Hero extends DungeonCharacter implements Serializable {
    private double myChanceToBlock;
    private final Random rand;

    /**
     * Hero class constructor
     *
     * @param theName Hero Name
     * @param theHitPoints Hero HP
     * @param theMinDamage Hero Min Damage
     * @param theMaxDamage Hero Max Damage
     * @param theAttackSpeed Hero Attack Speed
     * @param theChanceToHit Hero Chance To Hit
     * @param theChanceToBlock Hero Chance To Block
     */
    public Hero(final String theName, final int theHitPoints, final int theMinDamage, final int theMaxDamage,
                final int theAttackSpeed, final double theChanceToHit, final double theChanceToBlock) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit);
        myChanceToBlock = theChanceToBlock;
        rand = new Random();
    }

    /**
     * This is the take damage method that occurs when the
     * hero is attacked. Could potentially block the attack.
     *
     * @param theDamage damage number.
     * @return result of the attack.
     */
    @Override
    public AttackResult takeDamage(final int theDamage) {
        if (rand.nextInt(10) + 1 <= (int) getChanceToBlock() * 10) {
            return AttackResult.BLOCK;
        } else {
            int newHitPoints = getHitPoints() - theDamage;

            if (GameConfig.isInfiniteHealth()) {
                newHitPoints += theDamage;
            }

            // Ensure HP does not go below zero
            setHitPoints(Math.max(newHitPoints, 0));
        }
        return AttackResult.HIT;
    }

    /**
     * This method contains the class's special skill.
     *
     * @param theTarget the target of the attack.
     * @return result of the attack.
     */
    public AttackResult useSpecialSkill(DungeonCharacter theTarget) {
        return attack(theTarget);
    }

    /**
     * This method returns the hero's current chance to block.
     *
     * @return current chance to block.
     */
    public double getChanceToBlock() {
        return myChanceToBlock;
    }
}
