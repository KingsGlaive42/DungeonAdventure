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
        if (theAttacker.getClass().getSimpleName() == "Mage" || theAttacker.getClass().getSimpleName() == "Priestess") {
            if (theAttacker.getMagicPoints() <= 0) {
                System.out.println("Out of MP!");
                System.out.println(theAttacker.getName() + " bonks" + theDefender.getName());
                takeDamage(theDefender, 1);
                return;
            }
        }

        System.out.println(theAttacker.getName() + " attacks " + theDefender.getName()); //Replace with however we'll announce it
        Random rand = new Random();
        if ((double) (rand.nextInt(10) + 1)/10 <= theAttacker.getChanceToHit()) {
            if (theAttacker.getClass().getSimpleName() == "Mage" || theAttacker.getClass().getSimpleName() == "Priestess") {
                theAttacker.setMagicPoints(theAttacker.getMagicPoints() - 5);
            }
            System.out.println("Attack Landed!"); //Replace with however we'll announce it
            int damage = calculateDamage(theAttacker.getMinDamage(), theAttacker.getMaxDamage());
            takeDamage(theDefender, damage);
        } else {
            System.out.println("MISSED!"); //Replace with however we'll announce it missed
        }
    }

    public void performSpecialSkill(final Hero theAttacker, final DungeonCharacter theDefender) {
        theAttacker.useSpecialSkill(theDefender, this);
    }

    public void takeDamage(final DungeonCharacter theTarget, final int theDamage) {
        if (rand.nextInt(10) + 1 <= (int) theTarget.getChanceToBlock() * 10) {
            System.out.println(theTarget.getName() + "BLOCKED THE ATTACK!"); //Replace with however we'll announce it was blocked
        } else {
            theTarget.setHitPoints(theTarget.getHitPoints() - theDamage);
        }
    }

    public int calculateDamage(final int theMinDamage, final int theMaxDamage) {
        return theMinDamage + (int) (Math.random() * ((theMaxDamage - theMinDamage) + 1));
    }

}
