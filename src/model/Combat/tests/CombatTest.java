package model.Combat.tests;

import controller.CombatController;
import model.Combat.CombatEngine;
import model.DungeonCharacters.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombatTest {
    private CombatEngine engine;
    private Hero myHero;
    private Monster myMonster;

    @BeforeEach
    void setUp() {
        myHero = new Warrior("Hero");
        myMonster = new Ogre(200, 30, 60, 2, 0.6, 0.1, 30, 60);
        engine = new CombatEngine();
    }

    @Test
    void testHeroAttack() {
        int health = myMonster.getHitPoints();
        myHero.setChanceToHit(1.0);
        engine.attack(myHero, myMonster);
        assertNotEquals(health, myMonster.getHitPoints());
    }

    @Test
    void testHeroMiss() {
        int health = myMonster.getHitPoints();
        myHero.setChanceToHit(0.0);
        engine.attack(myHero, myMonster);
        assertEquals(health, myMonster.getHitPoints());
    }

    @Test
    void testEnemyAttack() {
        int health = myHero.getHitPoints();
        myHero.setChanceToBlock(0.0);
        myMonster.setChanceToHit(1.0);
        engine.attack(myMonster, myHero);
        assertNotEquals(health, myHero.getHitPoints());
    }

    @Test
    void testEnemyMiss() {
        int health = myHero.getHitPoints();
        myHero.setChanceToBlock(0.0);
        myMonster.setChanceToHit(0.0);
        engine.attack(myMonster, myHero);
        assertEquals(health, myHero.getHitPoints());
    }

    @Test
    void testHeroBlock() {
        int health = myHero.getHitPoints();
        myHero.setChanceToBlock(1.0);
        myMonster.setChanceToHit(1.0);
        engine.attack(myMonster, myHero);
        assertEquals(health, myHero.getHitPoints());
    }

    @Test
    void testDefend() {
        int maxDamage = myMonster.getMaxDamage();
        engine.handleDefend(myMonster);
        assertNotEquals(maxDamage, myMonster.getMaxDamage());
    }

    @Test
    void resetDefend() {
        int maxDamage = myMonster.getMaxDamage();
        engine.handleDefend(myMonster);
        engine.resetDefend(myMonster);
        assertEquals(maxDamage, myMonster.getMaxDamage());
    }

    @Test
    void heroSpecialSKill() {
        myHero = new Berserker("Hero");
        int maxDamage = myHero.getMaxDamage();
        int health = myHero.getHitPoints();
        engine.performSpecialSkill(myHero, myMonster);
        assertNotEquals(health, myHero.getHitPoints());
        int healthDifference = myMonster.getMaxHitPoints() - myMonster.getHitPoints();
        boolean damageIncrease = healthDifference > maxDamage;
        assertTrue(damageIncrease);
    }
}
