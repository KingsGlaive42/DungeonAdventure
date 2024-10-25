package model.DungeonCharacters;

import java.util.Random;

public abstract class Hero extends DungeonCharacter {

    public Hero() {
        this.myName = "Hero";
    }

    @Override
    public void attack(DungeonCharacter theTarget) {

    }

    @Override
    public void takeDamage(int theDamage) {
        Random rand = new Random();
        if (rand.nextInt(10) + 1 <= myChanceToBlock * 10) {
            System.out.println("BLOCKED!"); //Replace with however we'll announce it was blocked
        } else {
            myHitPoints = myHitPoints - theDamage;
        }
    }

    public void useSpecialSkill(DungeonCharacter theTarget) {

    }
}
