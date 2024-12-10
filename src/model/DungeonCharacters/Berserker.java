package model.DungeonCharacters;

import model.Combat.AttackResult;
import model.GameConfig;

import java.io.Serializable;
import java.util.Random;

/**
 * This is the Berserker Class.
 *
 * @author Thomas Le
 */
public class Berserker extends Hero implements Serializable {

    /**
     * Berserker Constructor.
     *
     * @param theName Name of Berserker.
     */
    public Berserker(final String theName) {
        super(theName, 200, 45, 70, 3, 0.7, 0.3);
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
     * Berserker Class' unique special skill.
     *
     * @param theTarget the target of the attack.
     * @return attack result.
     */
    @Override
    public AttackResult useSpecialSkill(final DungeonCharacter theTarget) {
        this.takeDamage(GameConfig.isInfiniteHealth() ? 0 : 10);
        int tempMin = getMinDamage();
        int tempMax = getMaxDamage();
        setMinDamage(65);
        setMaxDamage(90);

        if (GameConfig.isInfiniteDamage()) {
            setMinDamage(99998);
            setMaxDamage(99999);
        }

        AttackResult theResult = attack(theTarget);
        setMinDamage(tempMin);
        setMaxDamage(tempMax);
        return theResult;
    }
}
