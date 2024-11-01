package model.DungeonCharacters;

import model.Combat.CombatEngine;

public abstract class Hero extends DungeonCharacter {

    public Hero() {
        this.myName = "Hero";
    }

    public abstract void useSpecialSkill(DungeonCharacter theTarget, CombatEngine theEngine);
}
