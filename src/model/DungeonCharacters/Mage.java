package model.DungeonCharacters;

import model.Combat.AttackResult;
import model.Combat.CombatEngine;

import java.util.Random;

public class Mage extends Hero {
    int myMagicPoints;
    int myMaxMagicPoints;

    public Mage(final String theName) {
        super(theName, 75, 30, 50, 4, 0.8, 0.3);
        myMagicPoints = 50;
        myMaxMagicPoints = 50;
    }

    @Override
    public AttackResult attack(final DungeonCharacter theDefender) {
        if(getMagicPoints() <= 0) {
            System.out.println("Out of MP!");
            System.out.println(getName() + " bonks " + theDefender.getName());
            theDefender.takeDamage(1);
            return AttackResult.BONK;
        }
        Random rand = new Random();
        if ((rand.nextInt(10) + 1) / 10.0 <= getChanceToHit()) {
            int damage = calculateDamage(getMinDamage(), getMaxDamage());
            theDefender.takeDamage(damage);
            return AttackResult.HIT;
        } else {
            return AttackResult.MISS;
        }
    }

    public AttackResult useSpecialSkill(final DungeonCharacter theTarget, final CombatEngine theEngine) {
        if (getMagicPoints() < 20) {
            return AttackResult.BONK;
        }
        setMagicPoints(getMagicPoints()-20);
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 1) { //10% chance to insta-kill enemy
            int damage = 999;
            return theTarget.takeDamage(damage);
        } else if (rand.nextInt(10) + 1 <= 3) {
            int damage = calculateDamage(getMinDamage(), getMaxDamage());
            theTarget.takeDamage(damage);
            return AttackResult.HALF_HIT;
        } else {
            return AttackResult.MISS;
        }
    }

    public int getMagicPoints() { return myMagicPoints; }
    public void setMagicPoints(final int theMagicPoints) {
        this.myMagicPoints = Math.min(theMagicPoints, myMaxMagicPoints); // Ensures MP does not exceed max
    }
    public int getMaxMagicPoints() { return myMaxMagicPoints; }
}
