package model.PlayerInventory;

import controller.InputListener;
import controller.SoundManager;
import model.DungeonCharacters.Hero;
import model.DungeonManager.Dungeon;
import model.DungeonManager.Room;
import view.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

/**
 * Represents a Vision Potion item in the inventory.
 * The Vision Potion reveals up to 8 nearby rooms in the dungeon when used.
 * @author Aileen
 */
public class VisionPotion extends Item implements Serializable {
    private transient BufferedImage IMAGE;

    /**
     * Constructs a Vision Potion with a default name, description, and type.
     * The image representing the potion is also loaded.
     */
    public VisionPotion() {
        super("Vision Potion", "Reveals up to 8 nearby rooms.", ItemType.VISION_POTION);
        loadSprite();
    }

    /**
     * Loads the image representing the Vision Potion from the specified file path.
     * If the image cannot be loaded, it sets the image to null.
     */
    private void loadSprite() {
        BufferedImage temp;
        try {
            temp = ImageIO.read(new File("src/resources/assets/Inventory/potion_blue.png"));
        } catch (IOException e) {
            temp = null;
        }
        IMAGE = temp;
    }

    /**
     * Uses the Vision Potion to reveal surrounding rooms in the dungeon.
     * Each surrounding room is set to visible, and the player is notified.
     *
     * @param theHero The hero using the potion.
     * @param theDungeon The dungeon in which the potion is used.
     * @param theUI The UI to display messages to the player.
     */
    @Override
    public void use(Hero theHero, Dungeon theDungeon, UI theUI) {
        Room currRoom = theDungeon.getMyCurrentRoom();
        Map<Point, Room> surroundingRooms = theDungeon.getSurroundingRooms(currRoom);

        for (Map.Entry<Point, Room> entry : surroundingRooms.entrySet()) {
            Room room = entry.getValue();
            room.setVisibility(true);
        }
        theUI.getGameScreen().showDialogue("Revealed surrounding rooms!");

    }

    /**
     * Returns the image representing the Vision Potion.
     *
     * @return The image of the Vision Potion.
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
