package model.Database.Tests;

import model.Database.MonsterFactory;
import model.DungeonCharacters.Monster;
import org.junit.jupiter.api.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterDatabaseTest {

    private MonsterFactory monsterDatabase;

    @BeforeEach
    public void setUp() {
        monsterDatabase = new MonsterFactory();
    }

    @Test
    public void testLoadMonster() {
        Map<String, Monster> monsters = monsterDatabase.loadMonsterTemplates();
        assertNotNull(monsters, "Monster list should not be null");
        assertFalse(monsters.isEmpty(), "Monster list should not be empty");

        //boolean expected = monsters.stream().anyMatch(monster -> monster.getName().equals("Ogre"));
        boolean expected = monsters.containsKey("Ogre");
        assertTrue(expected, "Expected monster not found in list.");
    }

    @Test
    public void testMonsterCount() {
        Map<String, Monster> monsters = monsterDatabase.loadMonsterTemplates();
        int expected = 3;
        assertEquals(expected, monsters.size(), "The number of monsters loaded should be 3.");
    }

    @Test
    public void testMonsterAttributes() {
        Map<String, Monster> monsters = monsterDatabase.loadMonsterTemplates();
        for (Monster monster : monsters.values()) {
            assertNotNull(monster.getName(), "Monster name should not be null.");
            assertFalse(monster.getName().isEmpty(), "Monster name should not be empty.");
            assertTrue(monster.getHitPoints() > 0, "Monster HP should be greater than 0.");
            assertTrue(monster.getMinDamage() > 0, "Monster min damage should be greater than 0.");
            assertTrue(monster.getMaxDamage() > 0, "Monster max damage should be greater than 0.");
        }
    }
}
