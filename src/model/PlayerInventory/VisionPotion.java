package model.PlayerInventory;

import controller.InputListener;
import controller.SoundManager;
import model.DungeonManager.Dungeon;
import model.DungeonManager.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

public class VisionPotion extends Item implements Serializable {
    private transient BufferedImage IMAGE;
    public VisionPotion() {
        super("Vision Potion", "Reveals up to 8 nearby rooms.", ItemType.VISION_POTION);
        loadSprite();
    }

    public void use(final Dungeon theDungeon) {
        Room currRoom = theDungeon.getMyCurrentRoom();
        Map<Point, Room> surroundingRooms = theDungeon.getSurroundingRooms(currRoom);

        System.out.println("Revealing surrounding rooms:");
        for (Map.Entry<Point, Room> entry : surroundingRooms.entrySet()) {
            Room room = entry.getValue();
            System.out.println("Room at " + entry.getKey() + " -> Items: " + room.getRoomItems().size() + " item(s)");
        }
    }

    private void loadSprite() {
        BufferedImage temp;
        try {
            temp = ImageIO.read(new File("src/resources/assets/Inventory/potion_blue.png"));
        } catch (IOException e) {
            temp = null;
        }
        IMAGE = temp;
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
