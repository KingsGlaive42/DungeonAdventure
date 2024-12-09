package model.DungeonCharacters;

import model.Combat.AttackResult;

import java.awt.image.BufferedImage;
import java.io.Serial;
import java.io.Serializable;

public abstract class Monster extends DungeonCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final double myHealChance;
    private final int myMinHeal;
    private final int myMaxHeal;
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

    @Override
    public AttackResult takeDamage(final int theDamage) {
        int newHitPoints = getHitPoints() - theDamage;
        if (newHitPoints > 0) {
            setHitPoints(newHitPoints);
            int healAmount = heal();
            if (healAmount > 0) {
                return AttackResult.HEAL;
            }
        } else {
            setHitPoints(0);
        }
        return AttackResult.HIT;
    }

    public int heal() {
        if (Math.random() < myHealChance) {
            int healAmount = myMinHeal + (int) (Math.random() * (myMaxHeal - myMinHeal + 1));
            this.setHitPoints(Math.min(this.getHitPoints() + healAmount, getMaxHitPoints()));
            return healAmount;
        }
        return 5;
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
