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

public class Pillar extends Item {
    private final String myName;
    private static final String IMAGE_PATH = "src/resources/assets/Inventory/";
    private transient BufferedImage pillarImage;

    public Pillar(String theName) {
        super("'" + theName.charAt(0) + "' pillar", "The pillar of " + theName.toLowerCase() + ". \nRequired to exit the dungeon.", ItemType.PILLAR);
        this.myName = theName;
        loadPillarImage();
    }

    public String getPillarName() {
        return myName;
    }

    private void loadPillarImage() {
        String imageName = myName.toLowerCase();
        try {
            pillarImage = ImageIO.read(new File(IMAGE_PATH + imageName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            pillarImage = null;
        }
    }

    @Override
    public void use(Hero theHero, Dungeon theDungeon, UI theUI) {
        //System.out.println("Cannot use Pillars!");
        theUI.getGameScreen().showDialogue("Cannot use pillars!");
    }

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

