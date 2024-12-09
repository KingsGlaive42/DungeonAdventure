package model.PlayerInventory;

import model.DungeonCharacters.Hero;
import model.DungeonManager.Dungeon;
import model.DungeonManager.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Map;

public class VisionPotion extends Item {
    private static final BufferedImage IMAGE;
    public VisionPotion() {
        super("Vision Potion", "Reveals up to 8 nearby rooms.", ItemType.VISION_POTION);
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

    static {
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
}
