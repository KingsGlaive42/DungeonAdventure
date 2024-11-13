package model.DungeonCharacters;

public abstract class DungeonCharacter {
    protected String myName;
    protected int myHitPoints;
    protected int myMagicPoints;
    protected int myMinDamage;
    protected int myMaxDamage;
    protected int myAttackSpeed;
    protected double myChanceToHit;
    protected double myChanceToBlock;

    // New fields for maximum HP and MP
    protected int myMaxHitPoints;
    protected int myMaxMagicPoints;
    protected double myBaseChanceToBlock;

    protected DungeonCharacter() {
        this.myName = "";
        this.myHitPoints = 0;
        this.myMagicPoints = 0;
        this.myMinDamage = 0;
        this.myMaxDamage = 0;
        this.myAttackSpeed = 0;
        this.myChanceToHit = 0;
        this.myChanceToBlock = 0;
        this.myMaxHitPoints = 0;
        this.myMaxMagicPoints = 0;
    }

    protected DungeonCharacter(String theName, int theHitPoints, int theMinDamage,
                               int theMaxDamage, int theAttackSpeed, double theChanceToHit) {
        this.myName = theName;
        this.myHitPoints = theHitPoints;
        this.myMaxHitPoints = theHitPoints;  // Set max HP to initial HP
        this.myMagicPoints = 0;
        this.myMaxMagicPoints = 0;           // Initialize max MP to 0 or a specific value
        this.myMinDamage = theMinDamage;
        this.myMaxDamage = theMaxDamage;
        this.myAttackSpeed = theAttackSpeed;
        this.myChanceToHit = theChanceToHit;
    }

    public String getName() { return myName; }
    public int getHitPoints() { return myHitPoints; }
    public void setHitPoints(final int theHitPoints) {
        this.myHitPoints = Math.min(theHitPoints, myMaxHitPoints); // Ensures HP does not exceed max
    }

    public int getMagicPoints() { return myMagicPoints; }
    public void setMagicPoints(final int theMagicPoints) {
        this.myMagicPoints = Math.min(theMagicPoints, myMaxMagicPoints); // Ensures MP does not exceed max
    }
    public double getBaseChanceToBlock() { return myBaseChanceToBlock; }

    public int getMinDamage() { return myMinDamage; }
    public void setMinDamage(final int theMinDamage) { this.myMinDamage = theMinDamage; }
    public int getMaxDamage() { return myMaxDamage; }
    public void setMaxDamage(final int theMaxDamage) { this.myMaxDamage = theMaxDamage; }
    public double getChanceToHit() { return myChanceToHit; }
    public void setChanceToHit(final double theChanceToHit) { this.myChanceToHit = theChanceToHit; }
    public double getChanceToBlock() { return myChanceToBlock; }
    public void setChanceToBlock(final double theChanceToBlock) { this.myChanceToBlock = theChanceToBlock; }
    public boolean isAlive() { return myHitPoints > 0; }
    public int getMaxHitPoints() { return myMaxHitPoints; }
    public int getMaxMagicPoints() { return myMaxMagicPoints; }
}
