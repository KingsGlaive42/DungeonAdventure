package model.DungeonCharacters;

public class Thief extends Hero {
    public Thief(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myAttackSpeed = 6;
        this.myChanceToHit = 0.8;
        this.myMinDamage = 20;
        this.myMaxDamage = 40;
    }

    @Override
    public void attack(DungeonCharacter theTarget) {

    }

    @Override
    public void takeDamage(int theDamage) {

    }

    @Override
    public void useSpecialSkill(DungeonCharacter theTarget) {

    }
}
