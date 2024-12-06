package model.DungeonCharacters;

import model.Combat.AttackResult;

public abstract class Monster extends DungeonCharacter{

    protected double myHealChance;
    protected int myMinHeal;
    protected int myMaxHeal;

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
}
