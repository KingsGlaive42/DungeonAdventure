package model.DungeonCharacters;

import model.Combat.AttackResult;
import model.Combat.CombatEngine;

import java.util.Random;

import static java.lang.Math.min;

public class Priestess extends Hero {
    private int myMagicPoints;
    private int myMaxMagicPoints;

    public Priestess(final String theName) {
        super(theName, 75, 20, 40, 5, 0.7, 0.3);
        myMagicPoints = 50;
        myMaxMagicPoints = 50;
    }

    @Override
    public AttackResult attack(final DungeonCharacter theDefender) {
        if(getMagicPoints() <= 0) {
            theDefender.takeDamage(1);
            return AttackResult.BONK;
        }
        Random rand = new Random();
        setMagicPoints(getMagicPoints() - 5);
        if ((rand.nextInt(10) + 1) / 10.0 <= getChanceToHit()) {
            int damage = calculateDamage(getMinDamage(), getMaxDamage());
            theDefender.takeDamage(damage);
            return AttackResult.HIT;
        } else {
            return AttackResult.MISS;
        }
    }


    public void useSpecialSkill(final DungeonCharacter theTarget, final CombatEngine theEngine, final int theHealRange) {
        if (getMagicPoints() <= 0) {
            System.out.println("Out of Mana!");
            return;
        }

        int healAmount, mpCost;

        switch (theHealRange) {
            case 1 -> { healAmount = 25; mpCost = 5; }
            case 2 -> { healAmount = 50; mpCost = 10; }
            case 3 -> { healAmount = 75; mpCost = 15; }
            default -> { System.out.println("Invalid heal range selected."); return; }
        }
        setHitPoints(min(getHitPoints() + healAmount, getMaxHitPoints()));
        setMagicPoints(getMagicPoints() - 5);

        //theEngine.heal(theTarget, healAmount, mpCost);
    }

    public int getMagicPoints() { return myMagicPoints; }
    public void setMagicPoints(final int theMagicPoints) {
        this.myMagicPoints = min(theMagicPoints, myMaxMagicPoints); // Ensures MP does not exceed max
    }
    public int getMaxMagicPoints() { return myMaxMagicPoints; }
}
