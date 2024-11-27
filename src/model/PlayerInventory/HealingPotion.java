package model.PlayerInventory;

import model.DungeonCharacters.Hero;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HealingPotion extends Item {

    private final int myHealingAmount;
    private static final BufferedImage IMAGE;

    public HealingPotion() {
        super("Healing Potion", "Restores a random amount of health points.", ItemType.HEALING_POTION);
        this.myHealingAmount = (int)(Math.random() * 11 + 5); // 5-15 HP
    }

    public void use(final Hero theHero) {
        int newHealth = Math.min(theHero.getHitPoints() + myHealingAmount, theHero.getMaxHitPoints());
        theHero.setHitPoints(newHealth);
        System.out.println(theHero.getName() + " uses a Healing Potion and restores " + myHealingAmount);
    }

    static {
        BufferedImage temp;
        try {
            temp = ImageIO.read(new File("src/resources/assets/Inventory/health_potion.png"));
        } catch (IOException e) {
            temp = null;
        }
        IMAGE = temp;
    }

    public BufferedImage getImage() {
        return IMAGE;
    }
}
