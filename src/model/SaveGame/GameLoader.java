package model.SaveGame;

import java.io.*;

/**
 * Class that loads Gamestates from given save files
 */
public class GameLoader {
    /**
     * Loads and returns a GameState from the given file.
     *
     * @param theFileName the PATH to the save file.
     * @return a GameState object containing the information of the save Game State.
     */
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
