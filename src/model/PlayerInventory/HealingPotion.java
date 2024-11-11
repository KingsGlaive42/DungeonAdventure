package model.PlayerInventory;

import model.DungeonCharacters.Hero;

public class HealingPotion extends Item {

    private final int myHealingAmount;

    public HealingPotion() {
        super("Healing Potion", "Restores a random amount of health points.");
        this.myHealingAmount = (int)(Math.random() * 11 + 5); // 5-15 HP
    }

    @Override
    public void use(Hero theHero) {
        theHero.setHitPoints(Math.min(theHero.getHitPoints() + myHealingAmount, theHero.getMaxHitPoints()));
    }
}
