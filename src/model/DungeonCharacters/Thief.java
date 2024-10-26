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
        System.out.println(myName + " attacks " + theTarget.myName); //Replace with however we'll announce it
        Random rand = new Random();
        if ((double) (rand.nextInt(10) + 1)/10 <= myChanceToHit) {
            System.out.println("Attack Landed!"); //Replace with however we'll announce it
            int damage = calculateDamage(myMinDamage, myMaxDamage);
            theTarget.takeDamage(damage);
        } else {
            System.out.println("MISSED!"); //Replace with however we'll announce it
        }
    }

    @Override
    public void useSpecialSkill(DungeonCharacter theTarget) {
        System.out.println(myName + " used Surprise Attack on " + theTarget.myName);
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 4) {
            System.out.println(theTarget.myName + " didn't see it coming!"); //Replace with however we'll announce it
            attack(theTarget);
            attack(theTarget);
        } else if (rand.nextInt(10) + 1 <= 8) {
            attack(theTarget);
            System.out.println(theTarget.myName + " deflected second attack"); //Replace with however we'll announce it sort of succeeded
        } else {
            System.out.println(myName + " was caught!"); //Replace with however we'll announce it failed
            theTarget.attack(this); //Added this just because lol.
        }
    }

    private int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }
}
