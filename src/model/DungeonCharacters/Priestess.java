package model.DungeonCharacters;

import model.Combat.CombatEngine;

import java.util.Random;

public class Priestess extends Hero {
    public Priestess(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myMagicPoints =  50;
        this.myAttackSpeed = 5;
        this.myChanceToHit = 0.7;
        this.myChanceToBlock = 0.3;
        this.myMinDamage = 30;
        this.myMaxDamage = 50;
    }

    @Override
    public void useSpecialSkill(final DungeonCharacter theTarget, final CombatEngine theEngine) {
        if (getMagicPoints() <= 0) {
            System.out.println("Out of Mana!");
            return;
        }
        //There is supposed to be a range of healing, I guess it'll be sort of like different skills?
        // Heal I: heal 25, Heal II: heal 50, Heal III: heal 75
        int healRange = 1; //Replace with however it's chosen by user
        if (healRange == 1) {
            if (getMagicPoints() < 5) {
                System.out.println("Not enough MP!");
            } else {
                System.out.println(myName + " heals " + theTarget.myName);
                theTarget.myHitPoints = theTarget.myHitPoints + 25;
                setMagicPoints(getMagicPoints() - 5);
            }
        } else if (healRange == 2) {
            if (getMagicPoints() < 10) {
                System.out.println("Not enough MP!");
            } else {
                System.out.println(myName + " heals " + theTarget.myName);
                theTarget.myHitPoints = theTarget.myHitPoints + 50;
                setMagicPoints(getMagicPoints() - 10);
            }
        } else {
            if (getMagicPoints() < 15) {
                System.out.println("Not enough MP!");
            } else {
                System.out.println(myName + " heals " + theTarget.myName);
                theTarget.myHitPoints = theTarget.myHitPoints + 75;
                setMagicPoints(getMagicPoints() - 15);
            }
        }
    }
}
