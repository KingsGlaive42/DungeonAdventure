package model.DungeonCharacters;

import model.Combat.CombatEngine;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class Warrior extends Hero implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    public Warrior(final String theName) {
        this.myName = theName;
        this.myHitPoints = 125; //125
        this.myMaxHitPoints = 125;
        this.myAttackSpeed = 4;
        this.myChanceToHit = 0.8;
        this.myChanceToBlock = 0.2;
        this.myBaseChanceToBlock = 0.2;
        this.myMinDamage = 35;
        this.myMaxDamage = 60;
    }

    @Override
    public void useSpecialSkill(final DungeonCharacter theTarget, final CombatEngine theEngine) {
        System.out.println(myName + " used Crushing Blow!"); //Replace with however we'll announce it
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 4) {
            System.out.println(theTarget.myName + " was crushed!"); //Replace with however we'll announce it
            int damage = theEngine.calculateDamage(75, 175);
            theEngine.takeDamage(theTarget, damage);
        } else {
            System.out.println("Crushing Blow Missed!"); //Replace with however we'll announce it missed
        }
    }
}
