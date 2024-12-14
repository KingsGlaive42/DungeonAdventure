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

/**
 * The class is responsible for loading monster templates from a database
 * and creating specific monster objects based on those templates.
 * It connects to a SQLite database to retrieve information about different monsters and then
 * generates corresponding Monster objects such as Ogre, Skeleton, or Gremlin.
 * @author Aileen
 */
public class MonsterFactory {

    // Database connection URL
    private static final String db = "jdbc:sqlite:data/monster.db";

    /**
     * Loads the monster templates from the database and creates the corresponding {@link Monster} objects.
     *
     * @return A map where the key is the monster's name and the value is the corresponding {@link Monster} object.
     */
    public Map<String, Monster> loadMonsterTemplates() {
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

    /**
     * Creates a Monster object based on the given parameters.
     *
     * @param theName The name of the monster.
     * @param theHitPoints The hit points of the monster.
     * @param theMinDamage The minimum damage the monster can deal.
     * @param theMaxDamage The maximum damage the monster can deal.
     * @param theAttackSpeed The attack speed of the monster.
     * @param theChanceToHit The chance the monster has to hit its target.
     * @param theHealChance The chance the monster will heal itself.
     * @param theMinHeal The minimum amount the monster can heal.
     * @param theMaxHeal The maximum amount the monster can heal.
     * @return A new Monster object of the appropriate type, or null if the monster type is unknown.
     */
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
