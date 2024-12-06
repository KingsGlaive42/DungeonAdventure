package model.DungeonCharacters;

import model.Combat.AttackResult;

import java.io.Serializable;

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

    /**
     * Berserker Class' unique special skill.
     *
     * @param theTarget the target of the attack.
     * @return attack result.
     */
    @Override
    public AttackResult useSpecialSkill(final DungeonCharacter theTarget) {
        this.takeDamage(10);
        int tempMin = getMinDamage();
        int tempMax = getMaxDamage();
        setMinDamage(65);
        setMaxDamage(90);
        AttackResult theResult = attack(theTarget);
        setMinDamage(tempMin);
        setMaxDamage(tempMax);
        return theResult;
    }
}
