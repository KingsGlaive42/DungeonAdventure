package model.MonsterManager.Tests;

import model.DungeonCharacters.Monster;
import model.MonsterManager.MonsterGeneration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterGenerationTest {

    private MonsterGeneration monsterGeneration;

    @BeforeEach
    void setUp() {
        monsterGeneration = new MonsterGeneration() {
            @Override
            public List<Monster> generateMonsters(int theNumOfMonsters) {
                return super.generateMonsters(theNumOfMonsters);
            }
        };
    }

    // Ensure that the generated monsters are distinct objects
    @Test
    void testMonstersAreClones1() {
        List<Monster> generatedMonsters = monsterGeneration.generateMonsters(3);

        Monster firstMonster = generatedMonsters.get(0);
        assertNotSame(firstMonster, generatedMonsters.get(1), "Generated monsters should be distinct objects");
    }

    // Test that each monster is a clone
    @Test
    void testMonstersAreClones2() {
        List<Monster> generatedMonsters = monsterGeneration.generateMonsters(3);
        assertNotNull(generatedMonsters, "Generated monsters list should not be null");
        assertEquals(3, generatedMonsters.size(), "Number of generated monsters should match the requested number");

        // clone
        Monster originalMonster = generatedMonsters.get(0);
        originalMonster.setHitPoints(50); // Modify hit points

        System.out.println("Available monster templates: " + monsterGeneration.getMyMonsterTemplates().keySet());

        // Check original template monster's hit points
        Monster templateMonster = monsterGeneration.getMyMonsterTemplates().get("Gremlin");
        assertNotNull(templateMonster, "Template monster should not be null");
        assertNotEquals(originalMonster.getHitPoints(), templateMonster.getHitPoints(), "Cloned monster's hit points should not affect the template");
    }

    // Test that the generated monsters list is not empty
    @Test
    void testGenerateMonsters_NotEmpty() {
        List<Monster> generatedMonsters = monsterGeneration.generateMonsters(5);
        assertNotNull(generatedMonsters, "Generated monsters list should not be null");
        assertEquals(5, generatedMonsters.size(), "Number of generated monsters should match the requested number");
    }

    // Test generating 0 monsters
    @Test
    void testGenerateMonsters_ZeroMonsters() {
        List<Monster> generatedMonsters = monsterGeneration.generateMonsters(0);
        assertNotNull(generatedMonsters, "Generated monsters list should not be null");
        assertTrue(generatedMonsters.isEmpty(), "Generated monsters list should be empty when generating zero monsters");
    }
}
