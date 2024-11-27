package model.DungeonCharacters;

import java.awt.image.BufferedImage;

public abstract class Monster extends DungeonCharacter{

    protected double myHealChance;
    protected int myMinHeal;
    protected int myMaxHeal;
    private int monsterX;
    private int monsterY;

    public Monster(String theName, int theHitPoints, int theMinDamage,
                   int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                   int theMinHeal, int theMaxHeal) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit);
        this.myHealChance = theHealChance;
        this.myMinHeal = theMinHeal;
        this.myMaxHeal = theMaxHeal;
    }

    public void heal() {
        if (Math.random() < myHealChance) {
            int healAmount = myMinHeal + (int) (Math.random() * (myMaxHeal - myMinHeal + 1));
            this.myHitPoints = Math.min(myHitPoints + healAmount, getMaxHitPoints());
            System.out.println(myName + " healed by " + healAmount + " points. Current HP: " + myHitPoints);

        } else {
            System.out.println(myName + " healing failed.");
        }
    }

    public void setPosition(final int theX, final int theY) {
        this.monsterX = theX;
        this.monsterY = theY;
    }

    public int getMonsterX() {
        return monsterX;
    }

    public int getMonsterY() {
        return monsterY;
    }

    public abstract BufferedImage getSprite();
}
