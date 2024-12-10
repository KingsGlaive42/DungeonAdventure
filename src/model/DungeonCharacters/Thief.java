package model.DungeonCharacters;

import model.Combat.AttackResult;
import model.GameConfig;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * This is the Thief class
 *
 * @author Thomas Le
 */
public class Thief extends Hero implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Thief class constructor.
     *
     * @param theName Name of Thief.
     */
    public Thief(final String theName) {
        super(theName, 75, 20, 40, 6, 0.8, 0.4);
    }

    @Override
    public AttackResult attack(final DungeonCharacter theTarget) {
        Random rand = new Random();
        if ((rand.nextInt(10) + 1) / 10.0 <= getChanceToHit() || GameConfig.isInfiniteDamage()) {
            int damage = calculateDamage(getMinDamage(), getMaxDamage());

            if (GameConfig.isInfiniteDamage()) {
                damage = 99999;
            }

            return theTarget.takeDamage(damage);
        } else {
            return AttackResult.MISS;
        }
    }


    /**
     * This method is the thief's unique special skill.
     *
     * @param theTarget the target of the attack.
     * @return result of the attack.
     */
    @Override
    public AttackResult useSpecialSkill(final DungeonCharacter theTarget) {
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 4) {
            attack(theTarget);
            attack(theTarget);
            return AttackResult.HIT;
        } else if (rand.nextInt(10) + 1 <= 8) {
            attack(theTarget);
            return AttackResult.HALF_HIT;
        } else {
            theTarget.attack(this);
            return AttackResult.MISS;
        }
    }
}
