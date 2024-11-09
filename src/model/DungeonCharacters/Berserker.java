package model.DungeonCharacters;

import model.Combat.CombatEngine;

import java.util.Random;

public class Berserker extends Hero {
    public Berserker(final String theName) {
        this.myName = theName;
        this.myHitPoints = 200;
        this.myMaxHitPoints = 200;
        this.myAttackSpeed = 3;
        this.myChanceToHit = 0.7;
        this.myChanceToBlock = 0.3;
        this.myBaseChanceToBlock = 0.3;
        this.myMinDamage = 45;
        this.myMaxDamage = 70;
    }

    @Override
    public void useSpecialSkill(final DungeonCharacter theTarget, final CombatEngine theEngine) {
        System.out.println(myName + " used Blood Fiend!"); //Replace with however we'll announce it
        theEngine.takeDamage(this, 50);
        Random rand = new Random();
        int tempMin = getMinDamage();
        int tempMax = getMaxDamage();
        setMinDamage(65);
        setMaxDamage(90);
        theEngine.attack(this, theTarget);
        setMinDamage(tempMin);
        setMaxDamage(tempMax);
    }
}
