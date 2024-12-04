package model.DungeonCharacters;

import model.Combat.AttackResult;

import java.util.Random;

public class Hero extends DungeonCharacter {
    private double myChanceToBlock;
    private final double myBaseChanceToBlock;
    private final Random rand;

    public Hero(final String theName, final int theHitPoints, final int theMinDamage, final int theMaxDamage,
                final int theAttackSpeed, final double theChanceToHit, final double theBaseChanceToBlock) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit);
        myChanceToBlock = theBaseChanceToBlock;
        myBaseChanceToBlock = theBaseChanceToBlock;
        rand = new Random();
    }

    @Override
    public AttackResult takeDamage(final int theDamage) {
        if (rand.nextInt(10) + 1 <= (int) getChanceToBlock() * 10) {
            return AttackResult.BLOCK;
        } else {
            int newHitPoints = getHitPoints() - theDamage;
            // Ensure HP does not go below zero
            setHitPoints(Math.max(newHitPoints, 0));
        }
        return AttackResult.HIT;
    }

    public AttackResult useSpecialSkill(DungeonCharacter theTarget) {
        return attack(theTarget);
    }

    public double getChanceToBlock() {
        return myChanceToBlock;
    }

    public double getBaseChanceToBlock() {
        return myBaseChanceToBlock;
    }

    public void setChanceToBlock(final double theChanceToBlock) {
        myChanceToBlock = theChanceToBlock;
    }
}
