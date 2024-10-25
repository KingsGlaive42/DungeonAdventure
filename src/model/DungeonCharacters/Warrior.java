package model.DungeonCharacters;

import java.util.Random;

public class Warrior extends Hero {
    public Warrior(final String theName) {
        this.myName = theName;
        this.myHitPoints = 125;
        this.myAttackSpeed = 4;
        this.myChanceToHit = 0.8;
        this.myChanceToBlock = 0.2;
        this.myMinDamage = 35;
        this.myMaxDamage = 60;
    }

    @Override
    public void attack(DungeonCharacter theTarget) {

    }

    @Override
    public void useSpecialSkill(DungeonCharacter theTarget) {

    }
}
