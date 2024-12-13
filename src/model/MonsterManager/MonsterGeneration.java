package model.MonsterManager;

import model.Database.MonsterFactory;
import model.DungeonCharacters.Monster;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MonsterGeneration {
    //private final List<Monster> myMonsters;
    private final Map<String, Monster> myMonsterTemplates;
    private final Random rand;

    public MonsterGeneration() {
        //this.myMonsters = new ArrayList<>();
        //MonsterFactory factory = new MonsterFactory();
        this.myMonsterTemplates = new MonsterFactory().loadMonsterTemplates();
        this.rand = new Random();
        //loadMonsters();
    }

    public List<Monster> generateMonsters(int theNumOfMonsters) {
        List<Monster> generatedMonsters = new ArrayList<>();
        //List<String> monsterTypes = new ArrayList<>(myMonsterTemplates.keySet());

        for (int i = 0; i < theNumOfMonsters; i++) {
            //Monster monster = myMonsters.get(rand.nextInt(myMonsters.size()));
            //generatedMonsters.add(monster);
            //System.out.println("Generated monster: " + monster.getName());
            //String randomType = monsterTypes.get(rand.nextInt()(monsterTypes.size()));
            String[] keys = myMonsterTemplates.keySet().toArray(new String[0]);
            String selectedMonster = keys[rand.nextInt(keys.length)];
            Monster monster = myMonsterTemplates.get(selectedMonster).cloneMonster();

            //Monster newMonster = cloneMonster(template);
            if (monster != null) {
                generatedMonsters.add(monster);
            }
        }
        return generatedMonsters;
    }
}
