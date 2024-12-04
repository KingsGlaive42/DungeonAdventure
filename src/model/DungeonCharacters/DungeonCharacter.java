package model.DungeonCharacters;

import model.Combat.AttackResult;

import java.util.Random;

public abstract class DungeonCharacter {
    private String myName;
    private int myCurrentHitPoints;
    private int myMinDamage;
    private int myMaxDamage;
    private int myAttackSpeed;
    private double myChanceToHit;
    private Random rand;

    // New fields for maximum HP and MP
    private int myMaxHitPoints;

    protected DungeonCharacter() {
        myName = "";
        myCurrentHitPoints = myMaxHitPoints;
        myMinDamage = 0;
        myMaxDamage = 0;
        myAttackSpeed = 0;
        myChanceToHit = 0;
        rand = new Random();
    }

    public DungeonCharacter(final String theName, final int theHitPoints, final int theMinDamage, final int theMaxDamage,
                               final int theAttackSpeed, final double theChanceToHit) {
        myName = theName;
        myCurrentHitPoints = theHitPoints;
        myMaxHitPoints = theHitPoints;  // Set max HP to initial HP
        myMinDamage = theMinDamage;
        myMaxDamage = theMaxDamage;
        myAttackSpeed = theAttackSpeed;
        myChanceToHit = theChanceToHit;
        rand = new Random();
    }

    public AttackResult attack(final DungeonCharacter theDefender) {
        if ((rand.nextInt(10) + 1) / 10.0 <= getChanceToHit()) {
            int damage = calculateDamage(getMinDamage(), getMaxDamage());
            return theDefender.takeDamage(damage);
        } else {
            return AttackResult.MISS;
        }
    }

    public AttackResult takeDamage(final int theDamage) {
        int newHitPoints = getHitPoints() - theDamage;
        setHitPoints(Math.max(newHitPoints, 0));
        return AttackResult.HIT;
    }

    public int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }

    public String getName() { return myName; }
    public int getHitPoints() { return myCurrentHitPoints; }
    public void setHitPoints(final int theCurrentHitPoints) {
        this.myCurrentHitPoints = Math.min(theCurrentHitPoints, myMaxHitPoints); // Ensures HP does not exceed max
    }
    public int getAttackSpeed() { return myAttackSpeed; }
    public int getMinDamage() { return myMinDamage; }
    public void setMinDamage(final int theMinDamage) { this.myMinDamage = theMinDamage; }
    public int getMaxDamage() { return myMaxDamage; }
    public void setMaxDamage(final int theMaxDamage) { this.myMaxDamage = theMaxDamage; }
    public double getChanceToHit() { return myChanceToHit; }
    public void setChanceToHit(final double theChanceToHit) { this.myChanceToHit = theChanceToHit; }
    public boolean isAlive() { return myCurrentHitPoints > 0; }
    public int getMaxHitPoints() { return myMaxHitPoints; }

}
