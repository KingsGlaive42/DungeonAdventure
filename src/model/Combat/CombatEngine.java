package model.Combat;

import model.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacters.Hero;

import static java.lang.Math.min;

public class CombatEngine {
    private int numberOfTurns;
    private boolean isHeroFaster;

    public CombatEngine() {
        numberOfTurns = 0;
        isHeroFaster = true;
    }

    public AttackResult attack(final DungeonCharacter theAttacker, final DungeonCharacter theDefender) {
        if (isHeroFaster && theAttacker instanceof Hero || !isHeroFaster && !(theAttacker instanceof Hero)) {
            numberOfTurns--;
        }
        return theAttacker.attack(theDefender);
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public boolean isHeroFaster() {
        return isHeroFaster;
    }

    public void setHeroFaster(final boolean theFasterOne) {
        isHeroFaster = theFasterOne;
    }

    public void setNumberOfTurns(final int theNumberOfTurns, final boolean theHeroFaster) {
        numberOfTurns = theNumberOfTurns;
        isHeroFaster = theHeroFaster;
    }

    public void performSpecialSkill(final Hero theAttacker, final DungeonCharacter theDefender) {
        theAttacker.useSpecialSkill(theDefender);
    }

    public void handleDefend(final Hero hero) {
        // Increase chance to block or reduce damage taken temporarily
        hero.setChanceToBlock(1.0); // set to 100% temporarily for testing
    }

    public void resetDefend(final Hero hero) {
        hero.setChanceToBlock(hero.getBaseChanceToBlock()); // Reset to the original block chance
    }

    public void heal(final DungeonCharacter theTarget) {
        theTarget.setHitPoints(min(theTarget.getHitPoints() + 20, theTarget.getMaxHitPoints()));
    }
}
