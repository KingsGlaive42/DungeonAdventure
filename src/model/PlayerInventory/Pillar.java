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
 * Represents a Pillar item in the inventory.
 * The Pillar is required to exit the dungeon and cannot be used for other purposes.
 * @author Aileen
 */
public class Pillar extends Item {
    private final String myName;
    private static final String IMAGE_PATH = "src/resources/assets/Inventory/";
    private transient BufferedImage pillarImage;

    /**
     * Constructs a Pillar with a given name.
     * The name defines the pillar's identity, and the image is loaded based on the name.
     *
     * @param theName The name of the pillar (e.g., "Fire", "Water").
     */
    public Pillar(String theName) {
        super("'" + theName.charAt(0) + "' pillar", "The pillar of " + theName.toLowerCase() + ". \nRequired to exit the dungeon.", ItemType.PILLAR);
        this.myName = theName;
        loadPillarImage();
    }

    /**
     * Loads the image associated with the pillar based on its name.
     * The image is loaded from a specific file path, and if an error occurs, it sets the image to null.
     */
    private void loadPillarImage() {
        String imageName = myName.toLowerCase();
        try {
            pillarImage = ImageIO.read(new File(IMAGE_PATH + imageName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            pillarImage = null;
        }
    }

    /**
     * Uses the pillar item.
     * Since pillars cannot be used, this method shows a message saying that pillars cannot be used.
     *
     * @param theHero    The hero using the item.
     * @param theDungeon The dungeon where the item is being used.
     * @param theUI      The UI for showing any relevant messages.
     */
    @Override
    public void use(Hero theHero, Dungeon theDungeon, UI theUI) {
        theUI.getGameScreen().showDialogue("Cannot use pillars!");
    }

    /**
     * Returns the image representation of the pillar.
     *
     * @return The image of the pillar.
     */
    @Override
    public BufferedImage getImage() {
        return pillarImage;
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

        loadPillarImage();
    }
}

