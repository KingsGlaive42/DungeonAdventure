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
        int damage = 75;
        //If we're still using the meter idea, that'll determine damage amount. Else, we'll do random number.
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 4) {
            //Maybe Copy attack method but with different damage numbers?
            System.out.println("SUCCESS!"); //Replace with however we'll announce it was succeeded
        } else {
            System.out.println("MISSED!"); //Replace with however we'll announce it was succeeded
        }
    }
}
