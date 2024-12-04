package model.DungeonCharacters;

import model.Combat.AttackResult;

public class Berserker extends Hero {
    public Berserker(final String theName) {
        super(theName, 200, 45, 70, 3, 0.7, 0.3);
    }

    @Override
    public AttackResult useSpecialSkill(final DungeonCharacter theTarget) {
        //System.out.println(myName + " used Blood Fiend!"); //Replace with however we'll announce it
        this.takeDamage(25);
        int tempMin = getMinDamage();
        int tempMax = getMaxDamage();
        setMinDamage(65);
        setMaxDamage(90);
        AttackResult theResult = attack(theTarget);
        setMinDamage(tempMin);
        setMaxDamage(tempMax);
        return theResult;
    }
}
