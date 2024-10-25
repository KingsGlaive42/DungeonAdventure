package model.DungeonCharacters;

public abstract class DungeonCharacter {
    protected String myName;
    protected int myHitPoints;
    protected int myMinDamage;
    protected int myMaxDamage;
    protected int myAttackSpeed;
    protected double myChanceToHit;
    protected double myChanceToBlock;

    public abstract void attack(final DungeonCharacter theTarget);

    public abstract void takeDamage(final int theDamage);

    public abstract void useSpecialSkill(final DungeonCharacter theTarget);

    public String getName() { return myName; }
    public int getHitPoints() { return myHitPoints; }
    public void setHitPoints(final int theHitPoints) { this.myHitPoints = theHitPoints; }
    public boolean isAlive() { return myHitPoints > 0; }
}
