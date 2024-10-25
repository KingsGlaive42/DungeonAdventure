package model.DungeonCharacters;

import java.util.Random;

public class Priestess extends Hero {
    public Priestess(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
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
        int healingAmount = 1;
        //If we're still using the meter idea, that'll determine healing amount. Else, we'll do random number.
        theTarget.myHitPoints = theTarget.myHitPoints + healingAmount;
    }

    private int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }
}
