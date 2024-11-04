package model.Database;

import model.DungeonCharacters.Monster;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MonsterDatabaseTest {

    private MonsterDatabase monsterDatabase;

    @BeforeEach
    public void setUp() {
        monsterDatabase = new MonsterDatabase();
    }

    @Test
    public void testLoadMonsterList() {
        List<Monster> monsters = monsterDatabase.loadMonsters();
        assertNotNull(monsters, "Monster list should not be null");
        assertFalse(monsters.isEmpty(), "Monster list should not be empty");

        boolean expected = monsters.stream().anyMatch(monster -> monster.getName().equals("Ogre"));
        assertTrue(expected, "Expected monster not found in list.");
    }

    @Test
    public void testMonsterCount() {
        List<Monster> monsters = monsterDatabase.loadMonsters();
        int expected = 3;
        assertEquals(expected, monsters.size(), "The number of monsters loaded should be 3.");
    }

    @Test
    public void testMonsterAttributes() {
        List<Monster> monsters = monsterDatabase.loadMonsters();
        for (Monster monster : monsters) {
            assertNotNull(monster.getName(), "Monster name should not be null.");
            assertFalse(monster.getName().isEmpty(), "Monster name should not be empty.");
            assertTrue(monster.getHitPoints() > 0, "Monster HP should be greater than 0.");
            assertTrue(monster.getMinDamage() > 0, "Monster min damage should be greater than 0.");
            assertTrue(monster.getMaxDamage() > 0, "Monster max damage should be greater than 0.");
        }
    }
}
