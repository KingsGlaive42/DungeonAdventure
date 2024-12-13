package model.Database;

import model.DungeonCharacters.Gremlin;
import model.DungeonCharacters.Monster;
import model.DungeonCharacters.Ogre;
import model.DungeonCharacters.Skeleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsterFactory {
    private static final String db = "jdbc:sqlite:data/monster.db";

    public Map<String, Monster> loadMonsterTemplates() {
        //List<Monster> monsters = new ArrayList<>();
        Map<String, Monster> templates = new HashMap<>();
        try (Connection con = DriverManager.getConnection(db)) {
            String sql = "SELECT name, hit_points, min_damage, max_damage, " +
                    "attack_speed, chance_to_hit, heal_chance, min_heal, max_heal FROM monsters";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                int hitPoints = rs.getInt("hit_points");
                int minDamage = rs.getInt("min_damage");
                int maxDamage = rs.getInt("max_damage");
                int attackSpeed = rs.getInt("attack_speed");
                double chanceToHit = rs.getDouble("chance_to_hit");
                double healChance = rs.getDouble("heal_chance");
                int minHeal = rs.getInt("min_heal");
                int maxHeal = rs.getInt("max_heal");

                Monster monster = createMonster(name, hitPoints, minDamage, maxDamage, attackSpeed, chanceToHit,
                        healChance, minHeal, maxHeal);
                if (monster != null) {
                    //monsters.add(monster);
                    templates.put(name, monster);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return templates;
    }

    private Monster createMonster(String theName, int theHitPoints, int theMinDamage, int theMaxDamage, int theAttackSpeed,
                                  double theChanceToHit, double theHealChance, int theMinHeal, int theMaxHeal) {
        switch (theName) {
            case "Ogre":
                return new Ogre(theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                        theHealChance, theMinHeal, theMaxHeal);
            case "Skeleton":
                return new Skeleton(theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                        theHealChance, theMinHeal, theMaxHeal);
            case "Gremlin":
                return new Gremlin(theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                        theHealChance, theMinHeal, theMaxHeal);
            default:
                System.out.println("Unknown monster type: " + theName);
                return null;
        }

    }
}
