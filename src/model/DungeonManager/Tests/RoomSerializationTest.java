package model.DungeonManager.Tests;

import model.DungeonManager.Room;
import model.DungeonManager.RoomType;

import java.io.*;

public class RoomSerializationTest {
    public static void main(String[] args) {
        Room room = new Room(0, 0, RoomType.FILLER);

        // Save the room to a file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("room.ser"))) {
            oos.writeObject(room);
            System.out.println("Room serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load the room from the file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("room.ser"))) {
            Room loadedRoom = (Room) ois.readObject();
            System.out.println("Room deserialized successfully.");
            // Optionally test drawing or accessing fields
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
