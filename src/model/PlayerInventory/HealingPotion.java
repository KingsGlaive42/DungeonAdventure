package model.PlayerInventory;

import model.DungeonCharacters.Hero;

public class HealingPotion extends Item {

    private final int myHealingAmount;

    public HealingPotion() {
        super("Healing Potion", "Restores a random amount of health points.", ItemType.HEALING_POTION);
        this.myHealingAmount = (int)(Math.random() * 11 + 5); // 5-15 HP
    }

    public void use(final Hero theHero) {
        int newHealth = Math.min(theHero.getHitPoints() + myHealingAmount, theHero.getMaxHitPoints());
        theHero.setHitPoints(newHealth);
        System.out.println(theHero.getName() + " uses a Healing Potion and restores " + myHealingAmount);
    }
}
