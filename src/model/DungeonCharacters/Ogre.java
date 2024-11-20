package model.DungeonCharacters;

public class Ogre extends Monster{
    public Ogre(int theHitPoints, int theMinDamage,
                int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                int theMinHeal, int theMaxHeal) {
        super("Ogre", theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                theHealChance, theMinHeal, theMaxHeal);
    }
}
