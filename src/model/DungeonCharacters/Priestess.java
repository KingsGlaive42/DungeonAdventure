package model.DungeonCharacters;

public class Priestess extends Hero {
    public Priestess(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myAttackSpeed = 5;
        this.myChanceToHit = 0.7;
        this.myChanceToBlock = 0.3;
        this.myMinDamage = 25;
        this.myMaxDamage = 45;
    }

    @Override
    public void attack(DungeonCharacter theTarget) {
        System.out.println(myName + " performs a strong attack!");
        int damage = calculateDamage(myMinDamage, myMaxDamage);
        theTarget.takeDamage(damage);
    }

    @Override
    public void useSpecialSkill(DungeonCharacter theTarget) {
        int healingAmount = 1;
        //If we're still using the meter idea, that'll determine healing amount. Else, we'll do random number.
        theTarget.myHitPoints = theTarget.myHitPoints + healingAmount;
    }

    private int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }
}
