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
        System.out.println(myName + " performed an attack on " + theTarget.myName); //Replace with however we'll announce it
        Random rand = new Random();
        if ((double) (rand.nextInt(10) + 1)/10 <= myChanceToHit) {
            System.out.println("Attack Landed!"); //Replace with however we'll announce it
            int damage = calculateDamage(myMinDamage, myMaxDamage);
            theTarget.takeDamage(damage);
        } else {
            System.out.println("MISSED!"); //Replace with however we'll announce it missed
        }
    }

    @Override
    public void useSpecialSkill(DungeonCharacter theTarget) {
        System.out.println(myName + " used Crushing Blow!"); //Replace with however we'll announce it
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 4) {
            System.out.println(theTarget.myName + " was crushed!"); //Replace with however we'll announce it
            int damage = calculateDamage(75, 175);
            theTarget.takeDamage(damage);
        } else {
            System.out.println("Crushing Blow Missed!"); //Replace with however we'll announce it missed
        }
    }

    private int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }
}
