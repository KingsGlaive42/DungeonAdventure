package model.DungeonCharacters;

import model.Combat.CombatEngine;

import java.io.Serial;
import java.io.Serializable;

public abstract class Hero extends DungeonCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //TODO: Add constructor to init all fields in DungeonCharacter
    public Hero() {
        this.myName = "Hero";
    }

    public abstract void useSpecialSkill(DungeonCharacter theTarget, CombatEngine theEngine);
}