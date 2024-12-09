package model.DungeonCharacters;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestSerializationMonster {
    public static void main(String[] args) {
        try {
            // Create a Gremlin object
            Gremlin gremlin = new Gremlin(100, 10, 20, 2, 0.8, 0.3, 5, 15);
            gremlin.setPosition(5, 5);

            // Serialize the Gremlin object
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("monster_test.ser"))) {
                oos.writeObject(gremlin);
                System.out.println("Gremlin serialized successfully.");
            }

            // Deserialize the Gremlin object
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("monster_test.ser"))) {
                Gremlin deserializedGremlin = (Gremlin) ois.readObject();
                System.out.println("Gremlin deserialized successfully.");
                System.out.println("Position: (" + deserializedGremlin.getMonsterX() + ", " + deserializedGremlin.getMonsterY() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
