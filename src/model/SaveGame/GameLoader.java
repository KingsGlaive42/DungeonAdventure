package model.SaveGame;

import java.io.*;

public class GameLoader {
    public static GameState loadGame(final String theFileName) {
        File file = new File(theFileName);
        if (!file.exists()) {
            throw new IllegalArgumentException(theFileName + " does not exist");
        }

        try (FileInputStream fileInputStream = new FileInputStream(theFileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (GameState) objectInputStream.readObject();
        } catch (final IOException | ClassNotFoundException theException) {
            System.err.println("Error occurred while loading: " + theException.getMessage());
            return null;
        }
    }
}
