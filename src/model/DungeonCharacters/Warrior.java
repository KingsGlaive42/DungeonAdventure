package model.DungeonCharacters;

import model.Combat.AttackResult;
import model.GameConfig;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * This is the Warrior Class.
 *
 * @author Thomas Le
 */
public class Warrior extends Hero implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Warrior Constructor.
     *
     * @param theName Name of Warrior.
     */
    public Warrior(final String theName) {
        super(theName, 125, 35, 60, 4, 0.8, 0.2);
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
     * The Warrior Class' unique special skill.
     *
     * @param theTarget the target of the attack.
     * @return attack result.
     */
    @Override
    public AttackResult useSpecialSkill(final DungeonCharacter theTarget) {
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 4) {
            int damage = calculateDamage(getMinDamage(), getMaxDamage());

            if (GameConfig.isInfiniteDamage()) {
                damage = 9999999;
            }

            return theTarget.takeDamage(damage);
        } else {
            return AttackResult.MISS;
        }
    }
}
