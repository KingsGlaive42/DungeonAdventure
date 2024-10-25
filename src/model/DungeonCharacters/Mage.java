package model.DungeonCharacters;

import java.util.Random;

public class Mage extends Hero {
    public Mage(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myAttackSpeed = 4;
        this.myChanceToHit = 0.8;
        this.myChanceToBlock = 0.3;
        this.myMinDamage = 25;
        this.myMaxDamage = 45;
    }

    @Override
    public void attack(DungeonCharacter theTarget) {
        System.out.println(myName + " casts fireball!"); //Would be fun to randomize attack name
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
        System.out.println(myName + " used Ultima on " + theTarget.myName);
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 1) { //10% chance to insta-kill enemy
            System.out.println("Direct Hit!"); //Replace with however we'll announce it
            int damage = 999;
            theTarget.takeDamage(damage);
        } else if (rand.nextInt(10) + 1 <= 3) {
            int damage = calculateDamage(75, 150);
            theTarget.takeDamage(damage);
            System.out.println(theTarget.myName + " partially dodged!"); //Replace with however we'll announce it
        } else {
            System.out.println("Ultima missed!"); //Replace with however we'll announce it
        }
    }

    private int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }
}
