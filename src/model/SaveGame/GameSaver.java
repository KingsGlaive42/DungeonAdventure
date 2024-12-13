package model.SaveGame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Saves Gamestates to a file
 */
public class GameSaver {
    /**
     * Saves GameStates to a save file
     *
     * @param theGameState the Game State to be saves.
     * @param theFileName the path where the game will be saved to.
     * @return true if it successfully saved, false otherwise
     */
    public static boolean saveGame(final GameState theGameState, final String theFileName) {
        File file = new File(theFileName);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new RuntimeException("Failed to create save directory: " + parentDir);
            }
        }

        try (FileOutputStream fus = new FileOutputStream(theFileName);
             ObjectOutputStream oos = new ObjectOutputStream(fus)) {
            oos.writeObject(theGameState);
            System.out.println("Game successfully saved to " + theFileName);
            return true;
        } catch (final IOException theException) {
            System.err.println("Error occurred while saving. Error: " + theException.getMessage());
            theException.printStackTrace();
            return false;
        }
    }
}
