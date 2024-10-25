package model.DungeonCharacters;

import java.util.Random;

public class Priestess extends Hero {
    public Priestess(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myAttackSpeed = 5;
        this.myChanceToHit = 0.7;
        this.myChanceToBlock = 0.3;
        this.myMinDamage = 25;
        this.myMaxDamage = 45;
    }

    @Override
    public void attack(DungeonCharacter theTarget) {

    }

    @Override
    public void useSpecialSkill(DungeonCharacter theTarget) {
        int healingAmount = 1;
        //If we're still using the meter idea, that'll determine healing amount. Else, we'll do random number.
        theTarget.myHitPoints = theTarget.myHitPoints + healingAmount;
    }
}
