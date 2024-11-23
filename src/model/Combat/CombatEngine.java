package model.Combat;

import model.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacters.Hero;

import java.util.Random;
public class CombatEngine {
    private Random rand;

    public CombatEngine() {
        this.rand = new Random();
    }

    public void attack(final DungeonCharacter theAttacker, final DungeonCharacter theDefender) {
        if (theAttacker.getClass().getSimpleName().equals("Mage") || theAttacker.getClass().getSimpleName().equals("Priestess")) {
            if (theAttacker.getMagicPoints() <= 0) {
                System.out.println("Out of MP!");
                System.out.println(theAttacker.getName() + " bonks " + theDefender.getName());
                takeDamage(theDefender, 1);
                return;
            }
        }

        System.out.println(theAttacker.getName() + " attacks " + theDefender.getName());
        if ((rand.nextInt(10) + 1) / 10.0 <= theAttacker.getChanceToHit()) {
            if (theAttacker.getClass().getSimpleName().equals("Mage") || theAttacker.getClass().getSimpleName().equals("Priestess")) {
                theAttacker.setMagicPoints(theAttacker.getMagicPoints() - 5);
            }
            System.out.println("Attack Landed!");
            int damage = calculateDamage(theAttacker.getMinDamage(), theAttacker.getMaxDamage());
            takeDamage(theDefender, damage);
        } else {
            System.out.println("MISSED!");
        }
    }

    public void performSpecialSkill(final Hero theAttacker, final DungeonCharacter theDefender) {
        theAttacker.useSpecialSkill(theDefender, this);
    }

    public void takeDamage(final DungeonCharacter theTarget, final int theDamage) {
        if (rand.nextInt(10) + 1 <= (int) theTarget.getChanceToBlock() * 10) {
            System.out.println(theTarget.getName() + " BLOCKED THE ATTACK!");
        } else {
            int newHitPoints = theTarget.getHitPoints() - theDamage;
            // Ensure HP does not go below zero
            theTarget.setHitPoints(Math.max(newHitPoints, 0));
        }
    }

    public void handleDefend(Hero hero) {
        // Increase chance to block or reduce damage taken temporarily
        hero.setChanceToBlock(hero.getChanceToBlock() + 0.2); // Example of a temporary defense boost
        System.out.println(hero.getName() + " is defending, increasing block chance temporarily.");
    }

    // Optional: Reset the chance to block after defense round if needed
    public void resetDefend(Hero hero) {
        hero.setChanceToBlock(hero.getBaseChanceToBlock()); // Reset to the original block chance
    }

    public void heal(final DungeonCharacter theTarget, final int theAmount, final int mpCost) {
        // Check if the character has enough magic points
        if (theTarget.getMagicPoints() < mpCost) {
            System.out.println("Not enough MP!");
            return;
        }

        // Deduct magic points and increase health
        theTarget.setMagicPoints(theTarget.getMagicPoints() - mpCost);
        theTarget.setHitPoints(Math.min(theTarget.getHitPoints() + theAmount, theTarget.getMaxHitPoints()));

        System.out.println(theTarget.getName() + " heals for " + theAmount + " HP!");
    }


    public int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }
}
