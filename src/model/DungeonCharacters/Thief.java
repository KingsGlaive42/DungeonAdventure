package model.DungeonCharacters;

import java.util.Random;

public class Thief extends Hero {
    public Thief(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myAttackSpeed = 6;
        this.myChanceToHit = 0.8;
        this.myChanceToBlock = 0.4;
        this.myMinDamage = 20;
        this.myMaxDamage = 40;
    }

    @Override
    public void attack(DungeonCharacter theTarget) {

    }

    @Override
    public void useSpecialSkill(DungeonCharacter theTarget) {

    }
}
