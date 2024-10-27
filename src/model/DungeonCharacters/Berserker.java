package model.DungeonCharacters;

import java.util.Random;

public class Berserker extends Hero {
    public Berserker(final String theName) {
        this.myName = theName;
        this.myHitPoints = 200;
        this.myAttackSpeed = 3;
        this.myChanceToHit = 0.7;
        this.myChanceToBlock = 0.3;
        this.myMinDamage = 45;
        this.myMaxDamage = 70;
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
        System.out.println(myName + " used Blood Fiend!"); //Replace with however we'll announce it
        takeDamage(50);
        Random rand = new Random();
        int tempMin = getMinDamage();
        int tempMax = getMaxDamage();
        setMinDamage(65);
        setMaxDamage(90);
        attack(theTarget);
        setMinDamage(tempMin);
        setMaxDamage(tempMax);
    }

    private int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }
}
