package model.DungeonManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Room {
    final private int ROOM_COLUMNS = 16;
    final private int ROOM_ROWS = 12;

    private char[][] myLayout;
    private boolean isEntrance = false;
    private boolean isExit = false;

    public Room(final String theResourceFileName) throws IOException {
        this.myLayout = new char[ROOM_ROWS][ROOM_COLUMNS];
        loadRoomFromResource(theResourceFileName);
    }

    private void loadRoomFromResource(final String theResourceFileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(theResourceFileName);
        if (inputStream == null) {
            throw new IOException(("Resource file not found: " + theResourceFileName));
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int row = 0;

        while ((line = bufferedReader.readLine()) != null && row < ROOM_ROWS) {
            for (int col = 0; col < line.length() && col < ROOM_COLUMNS; col++) {
                myLayout[row][col] = line.charAt(col);

                if (line.charAt(col) == 'E') {
                    isEntrance = true;
                }
                if (line.charAt(col) == 'X') {
                    isExit = true;
                }
            }

            row++;
        }
        bufferedReader.close();
    }

    public boolean isEntrance() {
        return isEntrance;
    }

    public boolean isExit() {
        return isExit;
    }
}
