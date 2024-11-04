package model.MonsterManager;

import model.Database.MonsterDatabase;
import model.DungeonCharacters.Monster;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterGenerator {
    private final List<Monster> monsters;
    private final Random rand;

    public MonsterGenerator() {
        this.monsters = new ArrayList<>();
        this.rand = new Random();
        loadMonsters();
    }

    private void loadMonsters() {
        MonsterDatabase dataB = new MonsterDatabase();
        List<Monster> loadedMonsters = dataB.loadMonsters();
        this.monsters.addAll(loadedMonsters);
        System.out.println("Loaded " + loadedMonsters.size() + " monsters into the generator/");
    }

    public List<Monster> generateMonsters(int theNumOfMonsters) {
        List<Monster> generatedMonsters = new ArrayList<>();

        for (int i = 0; i < theNumOfMonsters; i++) {
            Monster monster = monsters.get(rand.nextInt(monsters.size()));
            generatedMonsters.add(monster);
            System.out.println("Generated monster: " + monster.getName());
        }
        return generatedMonsters;
    }

    // maybe adding more methods for monster generation managements
}
