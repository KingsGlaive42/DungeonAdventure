package model.DungeonCharacters;

import model.Combat.AttackResult;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class Warrior extends Hero implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    public Warrior(final String theName) {
        super(theName, 125, 35, 60, 4, 0.8, 0.2);
    }

    @Override
    public AttackResult useSpecialSkill(final DungeonCharacter theTarget) {
        //System.out.println(myName + " used Crushing Blow!"); //Replace with however we'll announce it
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 4) {
            //System.out.println(theTarget.myName + " was crushed!"); //Replace with however we'll announce it
            int damage = calculateDamage(getMinDamage(), getMaxDamage());
            return theTarget.takeDamage(damage);
        } else {
            System.out.println("Crushing Blow Missed!"); //Replace with however we'll announce it missed
            return AttackResult.MISS;
        }
    }
}
