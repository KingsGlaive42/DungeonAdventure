package model.PlayerInventory;

import controller.InputListener;
import controller.SoundManager;
import model.DungeonCharacters.Hero;
import model.DungeonManager.Dungeon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;

public class HealingPotion extends Item {

    private final int myHealingAmount;
    private transient BufferedImage IMAGE;

    public HealingPotion() {
        super("Healing Potion", "Restores a random amount of \nhealth points. \n(5-15 HP)", ItemType.HEALING_POTION);
        this.myHealingAmount = (int)(Math.random() * 11 + 5); // 5-15 HP
        loadSprite();
    }

    private void loadSprite() {
        BufferedImage temp;
        try {
            temp = ImageIO.read(new File("src/resources/assets/Inventory/health_potion.png"));
        } catch (IOException e) {
            temp = null;
        }
        IMAGE = temp;
    }

    @Override
    public void use(Hero theHero, Dungeon theDungeon) {
        int oldHealth = theHero.getHitPoints();
        System.out.println("Health: " + oldHealth);
        if (theHero.getHitPoints() < theHero.getMaxHitPoints()) {
            int newHealth = Math.min(theHero.getHitPoints() + myHealingAmount, theHero.getMaxHitPoints());
            theHero.setHitPoints(newHealth);
            if (oldHealth + myHealingAmount <= theHero.getMaxHitPoints()) {
                System.out.println(theHero.getName() + " uses a Healing Potion and restores " + myHealingAmount);
            } else {
                System.out.println(theHero.getName() + "'s full health has been restored!");
            }
            System.out.println("Health: " + newHealth);
        } else {
            System.out.println("You are already at maximum HP!");
        }
    }

    @Override
    public BufferedImage getImage() {
        return IMAGE;
    }

    /**
     * Custom deserialization method to restore transient fields.
     *
     * @param in The ObjectInputStream used to read the object.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class cannot be found.
     */
    @Serial
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        //System.out.println("Deserialized Room object.");

        loadSprite();
    }
}
