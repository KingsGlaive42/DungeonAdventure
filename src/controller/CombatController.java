package controller;

import model.Combat.CombatEngine;
import model.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacters.Hero;
import view.CombatPanel;

public class CombatController {
    private final CombatEngine combatEngine;
    private final CombatPanel combatPanel;
    private final Hero hero;
    private final DungeonCharacter enemy;

    public CombatController(Hero hero, DungeonCharacter enemy) {
        this.hero = hero;
        this.enemy = enemy;
        this.combatEngine = new CombatEngine();
        this.combatPanel = new CombatPanel(this);

        // Initialize hero and enemy info on the panel
        updateHeroInfo();
        updateEnemyInfo();
    }

    public CombatPanel getCombatPanel() {
        return combatPanel;
    }

    public void handleAttack() {
        // Hero attacks the enemy
        combatPanel.logAction(hero.getName() + " attacks " + enemy.getName());
        combatEngine.attack(hero, enemy);
        updateEnemyInfo();

        if (enemy.getHitPoints() <= 0) {
            combatPanel.logAction(enemy.getName() + " has been defeated!");
            return;
        }

        // Enemy counterattacks
        handleEnemyCounterattack();
    }

    public void handleSpecialSkill() {
        combatPanel.logAction(hero.getName() + " uses special skill on " + enemy.getName());
        combatEngine.performSpecialSkill(hero, enemy);
        updateEnemyInfo();

        if (enemy.getHitPoints() <= 0) {
            combatPanel.logAction(enemy.getName() + " has been defeated!");
            return;
        }

        handleEnemyCounterattack();
    }

    public void handleDefend() {
        // Defend action could increase chance to block or similar; you may add logic here
        combatPanel.logAction(hero.getName() + " is defending.");
        handleEnemyCounterattack();
    }

    private void handleEnemyCounterattack() {
        // Enemy takes its turn to attack the hero
        combatPanel.logAction(enemy.getName() + " counterattacks " + hero.getName());
        combatEngine.attack(enemy, hero);
        updateHeroInfo();

        if (hero.getHitPoints() <= 0) {
            combatPanel.logAction(hero.getName() + " has been defeated!");
        }
    }

    private void updateHeroInfo() {
        String heroInfo = hero.getName() + " - HP: " + hero.getHitPoints() + ", MP: " + hero.getMagicPoints();
        combatPanel.updateHeroInfo(heroInfo);
    }

    private void updateEnemyInfo() {
        String enemyInfo = enemy.getName() + " - HP: " + enemy.getHitPoints();
        combatPanel.updateEnemyInfo(enemyInfo);
    }
}
