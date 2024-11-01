package model.DungeonCharacters;

import model.Combat.CombatEngine;

import java.util.Random;

public class Mage extends Hero {
    public Mage(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myMagicPoints =  50;
        this.myAttackSpeed = 4;
        this.myChanceToHit = 0.8;
        this.myChanceToBlock = 0.3;
        this.myMinDamage = 25;
        this.myMaxDamage = 45;
    }

    @Override
    public void useSpecialSkill(final DungeonCharacter theTarget, final CombatEngine theEngine) {
        if (getMagicPoints() <= 0) {
            System.out.println("Out of Mana!");
            return;
        } else if (getMagicPoints() <= 20) {
            System.out.println("Not enough MP!");
            return;
        }
        System.out.println(myName + " used Ultima on " + theTarget.myName);
        setMagicPoints(getMagicPoints()-20);
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 1) { //10% chance to insta-kill enemy
            System.out.println("Direct Hit!"); //Replace with however we'll announce it
            int damage = 999;
            theEngine.takeDamage(theTarget, damage);
        } else if (rand.nextInt(10) + 1 <= 3) {
            int damage = theEngine.calculateDamage(75, 150);
            theEngine.takeDamage(theTarget, damage);
            System.out.println(theTarget.myName + " partially dodged!"); //Replace with however we'll announce it
        } else {
            System.out.println("Ultima missed!"); //Replace with however we'll announce it
        }
    }
}
