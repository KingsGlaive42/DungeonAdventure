package model.DungeonCharacters;

import model.GameObject;

public abstract class DungeonCharacter {
    protected String myName;
    protected int myHitPoints;
    protected int myMagicPoints;
    protected int myMinDamage;
    protected int myMaxDamage;
    protected int myAttackSpeed;
    protected double myChanceToHit;
    protected double myChanceToBlock;

    protected DungeonCharacter() {
        this.myName = "";
        this.myHitPoints = 0;
        this.myMagicPoints = 0;
        this.myMinDamage = 0;
        this.myMaxDamage = 0;
        this.myAttackSpeed = 0;
        this.myChanceToHit = 0;
        this.myChanceToBlock = 0;
    }

    protected DungeonCharacter(String theName, int theHitPoints, int theMinDamage,
                               int theMaxDamage, int theAttackSpeed, double theChanceToHit) {
        this.myName = theName;
        this.myHitPoints = theHitPoints;
        this.myMinDamage = theMinDamage;
        this.myMaxDamage = theMaxDamage;
        this.myAttackSpeed = theAttackSpeed;
        this.myChanceToHit = theChanceToHit;
    }

    public abstract void attack(final DungeonCharacter theTarget);

    public abstract void takeDamage(final int theDamage);

    public String getName() { return myName; }
    public int getHitPoints() { return myHitPoints; }
    public void setHitPoints(final int theHitPoints) { this.myHitPoints = theHitPoints; }
    public int getMagicPoints() { return myMagicPoints; }
    public void setMagicPoints(final int theMagicPoints) { this.myMagicPoints = theMagicPoints; }
    public int getMinDamage() { return myMinDamage; }
    public void setMinDamage(final int theMinDamage) { this.myMinDamage = theMinDamage; }
    public int getMaxDamage() { return myMaxDamage; }
    public void setMaxDamage(final int theMaxDamage) { this.myMaxDamage = theMaxDamage; }
    public boolean isAlive() { return myHitPoints > 0; }
}
