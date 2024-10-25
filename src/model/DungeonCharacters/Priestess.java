package model.DungeonCharacters;

public class Priestess extends Hero {
    public Priestess(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myAttackSpeed = 5;
        this.myChanceToHit = 0.7;
        this.myMinDamage = 25;
        this.myMaxDamage = 45;
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
