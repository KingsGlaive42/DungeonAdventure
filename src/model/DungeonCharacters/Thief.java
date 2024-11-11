package model.DungeonCharacters;

import model.Combat.CombatEngine;

import java.util.Random;

public class Thief extends Hero {
    public Thief(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myMaxHitPoints = 75;
        this.myAttackSpeed = 6;
        this.myChanceToHit = 0.8;
        this.myChanceToBlock = 0.4;
        this.myBaseChanceToBlock = 0.4;
        this.myMinDamage = 20;
        this.myMaxDamage = 40;
    }

    @Override
    public void useSpecialSkill(final DungeonCharacter theTarget, final CombatEngine theEngine) {
        System.out.println(myName + " used Surprise Attack on " + theTarget.myName);
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 4) {
            System.out.println(theTarget.myName + " didn't see it coming!"); //Replace with however we'll announce it
            theEngine.attack(this, theTarget);
            theEngine.attack(this, theTarget);
        } else if (rand.nextInt(10) + 1 <= 8) {
            theEngine.attack(this, theTarget);
            System.out.println(theTarget.myName + " deflected second attack"); //Replace with however we'll announce it sort of succeeded
        } else {
            System.out.println(myName + " was caught!"); //Replace with however we'll announce it failed
            theEngine.attack(theTarget, this); //Added this just because lol.
        }
    }
}
