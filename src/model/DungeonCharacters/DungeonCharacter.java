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

    public abstract void attack(final DungeonCharacter theTarget);

    public abstract void takeDamage(final int theDamage);

    public String getName() { return myName; }
    public int getHitPoints() { return myHitPoints; }
    public void setHitPoints(final int theHitPoints) { this.myHitPoints = theHitPoints; }
    public boolean isAlive() { return myHitPoints > 0; }
}
