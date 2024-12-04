package model.DungeonCharacters;

public class Skeleton extends Monster {
    public Skeleton(int theHitPoints, int theMinDamage,
                    int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                    int theMinHeal, int theMaxHeal) {
        super("Skeleton",theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                theHealChance, theMinHeal, theMaxHeal);
    }
}
