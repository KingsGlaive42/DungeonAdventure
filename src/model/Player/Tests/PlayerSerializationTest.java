package model.Player.Tests;


import model.DungeonManager.Dungeon;
import model.Player.Player;
import model.PlayerInventory.Inventory;

import java.io.*;

public class PlayerSerializationTest {
    public static void main(String[] args) {
        Dungeon dungeon = new Dungeon(10, 10, 20);
        Inventory inventory = new Inventory(dungeon);
        Player player = new Player("warrior", "warrior", inventory);

        // Save the room to a file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("room.ser"))) {
            oos.writeObject(player);
            System.out.println("Player serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load the room from the file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("room.ser"))) {
            Player loadedPlayer = (Player) ois.readObject();
            System.out.println("Player deserialized successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
