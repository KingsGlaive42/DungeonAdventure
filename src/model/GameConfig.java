package model;

public class GameConfig {
    //GAME SIZES
    public static final int ORIGINAL_TILE_SIZE = 32;
    public static final double PIXEL_SCALAR = 1.0;
    public static final int TILE_SIZE = (int) (ORIGINAL_TILE_SIZE * PIXEL_SCALAR);
    public static final int MAX_SCREEN_COLUMNS = 17;
    public static final int MAX_SCREEN_ROWS = 13;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLUMNS;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    // Game loop settings
    public static final int FPS = 30;
    public static final int NANO_IN_SECONDS = 1_000_000_000;
    public static final double DRAW_INTERVAL = (double) NANO_IN_SECONDS / FPS;

    //SOUNDS
    public static final String MENU_THEME = "src/resources/sounds/MenuTheme.wav";
    public static final String GAME_THEME = "src/resources/sounds/GameTheme.wav";
    public static final String COMBAT_THEME = "src/resources/sounds/CombatTheme.wav";
    public static final String PAUSE_SOUND = "src/resources/sounds/PauseSound.wav";

    //PREVENT INSTANTIATION
    private GameConfig() {
        throw new UnsupportedOperationException("GameConfig is a utility class and cannot be instantiated");
    }
}
