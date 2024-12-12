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
