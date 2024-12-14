package model.MonsterManager;

import model.Database.MonsterFactory;
import model.DungeonCharacters.Monster;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The MonsterGeneration class is responsible for generating a list of monsters
 * based on templates loaded from the MonsterFactory.
 *
 * It uses a random selection process to create a specified number of monsters,
 * ensuring each monster is a clone of a template from the available monster types.
 * @author Aileen
 */
public class MonsterGeneration {
    // A map containing the monster templates loaded from the database
    private final Map<String, Monster> myMonsterTemplates;

    // Random number generator for selecting monsters randomly
    private final Random rand;

    /**
     * Constructs a new MonsterGeneration instance.
     * It initializes the monster templates by loading them from the database
     * and creates a new instance of the Random class for random selections.
     */
    public MonsterGeneration() {
        this.myMonsterTemplates = new MonsterFactory().loadMonsterTemplates();
        this.rand = new Random();
    }

    /**
     * Generates a list of monsters based on the templates loaded from the database.
     * The number of monsters generated is specified by the parameter theNumOfMonsters.
     *
     * A random monster is selected from the available templates for each generated monster.
     * Each selected monster is cloned to ensure that the original templates remain unchanged.
     *
     *
     * @param theNumOfMonsters The number of monsters to generate.
     * @return A list of randomly generated Monster objects.
     */
    public List<Monster> generateMonsters(int theNumOfMonsters) {
        List<Monster> generatedMonsters = new ArrayList<>();

        for (int i = 0; i < theNumOfMonsters; i++) {
            String[] keys = myMonsterTemplates.keySet().toArray(new String[0]);
            String selectedMonster = keys[rand.nextInt(keys.length)];
            Monster monster = myMonsterTemplates.get(selectedMonster).cloneMonster();

            if (monster != null) {
                generatedMonsters.add(monster);
            }
        }
        return generatedMonsters;
    }

    public Map<String, Monster> getMyMonsterTemplates() {
        return myMonsterTemplates;
    }
}
