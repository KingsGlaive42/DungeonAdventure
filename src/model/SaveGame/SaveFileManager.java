package model.SaveGame;

import java.io.File;

public class SaveFileManager {
    private static final String SAVE_DIR = "saves";

    public SaveFileManager() {
        ensureSaveDirExists();
    }

    public File getSaveFile(final int theSlotNumber) {
        if (checkSlotNumberBounds(theSlotNumber)) {
            throw new IllegalArgumentException("Slot number must be 1, 2, or 3. Input: " + theSlotNumber);
        }

        return new File(SAVE_DIR, "slot" + theSlotNumber + ".sav");
    }

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

    public boolean saveFileExists(final int theSlotNumber) {
        if (checkSlotNumberBounds(theSlotNumber)) {
            throw new IllegalArgumentException("Slot number must be 1, 2, or 3. Input: " + theSlotNumber);
        }
        File saveFile = getSaveFile(theSlotNumber);
        return saveFile.exists();
    }

    public boolean deleteSaveFile(final int theSlotNumber) {
        if (checkSlotNumberBounds(theSlotNumber)) {
            throw new IllegalArgumentException("Slot number must be 1, 2, or 3. Input: " + theSlotNumber);
        }

        File saveFile = getSaveFile(theSlotNumber);
        return saveFile.exists() && saveFile.delete();
    }

    public int countExistingSaveFiles() {
        File saveDir = new File(SAVE_DIR);
        File[] files = saveDir.listFiles((dir, name) -> name.endsWith(".sav"));
        return files != null ? files.length : 0;
    }

    private boolean checkSlotNumberBounds(final int theSlotNumber) {
        return theSlotNumber != 1 && theSlotNumber != 2 && theSlotNumber != 3;
    }
}
