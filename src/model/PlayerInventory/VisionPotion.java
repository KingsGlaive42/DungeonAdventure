package model.PlayerInventory;

import model.DungeonCharacters.Hero;

public class VisionPotion extends Item {
    public VisionPotion() {
        super("Vision Potion", "Reveals nearby rooms.");
    }

    @Override
    public void use(Hero theHero) {
        System.out.println("You used a vision potion");
        // add functionality
    }
}
