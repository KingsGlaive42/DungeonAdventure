package model.PlayerInventory;

import controller.InputListener;
import controller.SoundManager;
import model.DungeonCharacters.Hero;
import model.DungeonManager.Dungeon;
import view.UI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
/**
 * Represents a healing potion item in the inventory.
 * <p>
 * This item restores a random amount of health points (between 5 and 15) to a hero when used.
 * The healing potion also has an associated image for display in the UI.
 * </p>
 * @author Aileen
 */
public class HealingPotion extends Item {

    // The amount of health points this potion restores
    private final int myHealingAmount;

    // The image associated with this healing potion
    private transient BufferedImage IMAGE;

    /**
     * Constructs a new HealingPotion object with a random healing amount between 5 and 15 HP.
     * Also loads the potion's sprite image.
     */
    public HealingPotion() {
        super("Healing Potion", "Restores a random amount of \nhealth points. \n(5-15 HP)", ItemType.HEALING_POTION);
        this.myHealingAmount = (int)(Math.random() * 11 + 5); // 5-15 HP
        loadSprite();
    }

    /**
     * Loads the sprite image for the healing potion.
     * The image is read from the file system and stored in the IMAGE field.
     * If an error occurs during loading, the image will be set to null.
     */
    private void loadSprite() {
        BufferedImage temp;
        try {
            temp = ImageIO.read(new File("src/resources/assets/Inventory/health_potion.png"));
        } catch (IOException e) {
            temp = null;
        }
        IMAGE = temp;
    }

    /**
     * Uses the healing potion on the given hero.
     * The hero's health is restored by a random amount (between 5 and 15 HP), but it will not exceed the hero's maximum health.
     * A dialogue is displayed to the user indicating how much health was restored or if the hero is already at full health.
     *
     *
     * @param theHero The hero who will use the healing potion.
     * @param theDungeon The current dungeon (not used in this method, but included for consistency).
     * @param theUI The UI object used to display messages to the user.
     */
    @Override
    public void use(final Hero theHero, final Dungeon theDungeon, final UI theUI) {
        int oldHealth = theHero.getHitPoints();
        if (theHero.getHitPoints() < theHero.getMaxHitPoints()) {
            int newHealth = Math.min(theHero.getHitPoints() + myHealingAmount, theHero.getMaxHitPoints());
            theHero.setHitPoints(newHealth);
            if (oldHealth + myHealingAmount < theHero.getMaxHitPoints()) {
                theUI.getGameScreen().showDialogue("Healing potion restored \n" + myHealingAmount + " HP!");
            } else {
                theUI.getGameScreen().showDialogue("Full health has been \nrestored!");
            }
        } else {
            theUI.getGameScreen().showDialogue("You are already at maximum \nHP!");
        }
    }

    /**
     * Returns the image associated with this healing potion.
     *
     * @return The BufferedImage representing the healing potion sprite.
     */
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
