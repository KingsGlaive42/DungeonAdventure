package model.DungeonCharacters;

public class Warrior extends DungeonCharacter {
    public Warrior(final String theName) {
        this.myName = theName;
        this.myHitPoints = 125;
        this.myAttackSpeed = 4;
        this.myChanceToHit = 0.8;
        this.myMinDamage = 35;
        this.myMaxDamage = 60;
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
