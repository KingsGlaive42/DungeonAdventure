package model.SaveGame;

import java.io.File;

/**
 * Manages save files for the game, including ensuring the save directory exists and retrieving specific save files.
 */
public class SaveFileManager {
    /**
     * The directory where save files are stored.
     */
    private static final String SAVE_DIR = "saves";

    /**
     * Constructs a SaveFileManager instance and ensures the save directory exists.
     */
    public SaveFileManager() {
        ensureSaveDirExists();
    }

    /**
     * Retrieves the save file associated with the specified slot number.
     *
     * @param theSlotNumber the slot number of the save file (1, 2, or 3).
     * @return a File object representing the save file.
     * @throws IllegalArgumentException if the slot number is not 1, 2, or 3.
     */
    public File getSaveFile(final int theSlotNumber) {
        if (checkSlotNumberBounds(theSlotNumber)) {
            throw new IllegalArgumentException("Slot number must be 1, 2, or 3. Input: " + theSlotNumber);
        }

        return new File(SAVE_DIR, "slot" + theSlotNumber + ".sav");
    }

    /**
     * Ensures that the save directory exists. If it does not exist, attempts to create it.
     * Logs a message indicating success or failure.
     */
    private void ensureSaveDirExists() {
        File saveDir = new File(SAVE_DIR);
        if (!saveDir.exists()) {
            if (saveDir.mkdir()) {
                System.out.println("Created save directory: " + SAVE_DIR);
            } else {
                System.err.println("Failed to create save directory: " + SAVE_DIR);
            }
        }
    }

    /**
     * Checks if the given slot number is valid (1, 2, or 3).
     *
     * @param theSlotNumber the slot number to check.
     * @return true if the slot number is invalid; false otherwise.
     */
    private boolean checkSlotNumberBounds(final int theSlotNumber) {
        return theSlotNumber != 1 && theSlotNumber != 2 && theSlotNumber != 3;
    }
}
