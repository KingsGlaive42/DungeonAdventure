package model.DungeonCharacters;

import model.Combat.CombatEngine;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class Thief extends Hero implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public Thief(final String theName) {
        super(theName, 75, 20, 40, 6, 0.8, 0.4);
    }

    public void useSpecialSkill(final DungeonCharacter theTarget, final CombatEngine theEngine) {
        //System.out.println(myName + " used Surprise Attack on " + theTarget.myName);
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= 4) {
            //System.out.println(theTarget.myName + " didn't see it coming!"); //Replace with however we'll announce it
            theEngine.attack(this, theTarget);
            theEngine.attack(this, theTarget);
        } else if (rand.nextInt(10) + 1 <= 8) {
            theEngine.attack(this, theTarget);
            //System.out.println(theTarget.myName + " deflected second attack"); //Replace with however we'll announce it sort of succeeded
        } else {
            //System.out.println(myName + " was caught!"); //Replace with however we'll announce it failed
            theEngine.attack(theTarget, this); //Added this just because lol.
        }
    }
}
