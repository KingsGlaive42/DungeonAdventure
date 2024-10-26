package model.DungeonCharacters;

import java.util.Random;

public class Priestess extends Hero {
    public Priestess(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myMagicPoints =  50;
        this.myAttackSpeed = 5;
        this.myChanceToHit = 0.7;
        this.myChanceToBlock = 0.3;
        this.myMinDamage = 30;
        this.myMaxDamage = 50;
    }

    @Override
    public void attack(DungeonCharacter theTarget) {
        System.out.println(myName + " bonks" + theTarget.myName); //Would be fun to randomize attack name
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
        if (myMagicPoints <= 0) {
            System.out.println("Out of Mana!");
            return;
        }
        //There is supposed to be a range of healing, I guess it'll be sort of like different skills?
        // Heal I: heal 25, Heal II: heal 50, Heal III: heal 75
        int healRange = 1; //Replace with however it's chosen by user
        if (healRange == 1) {
            if (myMagicPoints < 5) {
                System.out.println("Not enough MP!");
            } else {
                System.out.println(myName + " heals " + theTarget.myName);
                theTarget.myHitPoints = theTarget.myHitPoints + 25;
                myMagicPoints = myMagicPoints - 5;
            }
        } else if (healRange == 2) {
            if (myMagicPoints < 10) {
                System.out.println("Not enough MP!");
            } else {
                System.out.println(myName + " heals " + theTarget.myName);
                theTarget.myHitPoints = theTarget.myHitPoints + 50;
                myMagicPoints = myMagicPoints - 10;
            }
        } else {
            if (myMagicPoints < 15) {
                System.out.println("Not enough MP!");
            } else {
                System.out.println(myName + " heals " + theTarget.myName);
                theTarget.myHitPoints = theTarget.myHitPoints + 75;
                myMagicPoints = myMagicPoints - 15;
            }
        }
    }

    private int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }
}
