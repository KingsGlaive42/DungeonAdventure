package model.DungeonCharacters;

import model.Combat.CombatEngine;

import java.io.Serial;
import java.io.Serializable;

public class Priestess extends Hero implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //TODO: Do not directly access fields, use a super() call for initialization
    public Priestess(final String theName) {
        this.myName = theName;
        this.myHitPoints = 75;
        this.myMaxHitPoints = 75;
        this.myMagicPoints =  50;
        this.myMaxMagicPoints = 50;
        this.myAttackSpeed = 5;
        this.myChanceToHit = 0.7;
        this.myChanceToBlock = 0.3;
        this.myBaseChanceToBlock = 0.3;
        this.myMinDamage = 30;
        this.myMaxDamage = 50;
    }

    public void useSpecialSkill(final DungeonCharacter theTarget, final CombatEngine theEngine, final int theHealRange) {
        if (getMagicPoints() <= 0) {
            System.out.println("Out of Mana!");
            return;
        }

        int healRange = theHealRange;
        int healAmount, mpCost;

        switch (healRange) {
            case 1 -> { healAmount = 25; mpCost = 5; }
            case 2 -> { healAmount = 50; mpCost = 10; }
            case 3 -> { healAmount = 75; mpCost = 15; }
            default -> { System.out.println("Invalid heal range selected."); return; }
        }

        theEngine.heal(theTarget, healAmount, mpCost);
    }

    @Override
    public void useSpecialSkill(DungeonCharacter theTarget, CombatEngine theEngine) {

    }
}
