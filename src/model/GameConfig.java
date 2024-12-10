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

    // CHARACTER INFO
    public static final String[] theGraphics2D = {"warrior", "thief", "priestess", "berserker", "mage"};
    public static final int NUM_CHARACTERS = theGraphics2D.length;

    // Game loop settings
    public static final int FPS = 30;
    public static final int NANO_IN_SECONDS = 1_000_000_000;
    public static final double DRAW_INTERVAL = (double) NANO_IN_SECONDS / FPS;

    //SOUNDS
    public static final String MENU_THEME = "src/resources/sounds/MenuTheme.wav";
    public static final String GAME_THEME = "src/resources/sounds/GameTheme.wav";
    public static final String COMBAT_THEME = "src/resources/sounds/CombatTheme.wav";
    public static final String PAUSE_SOUND = "src/resources/sounds/PauseSound.wav";

    //Global Cheat Settings
    public static final String[] CHEAT_NAMES = {"infiniteHealth", "infiniteDamage", "highSpeed", "showHitboxes"};
    private static boolean infiniteHealth = false;
    private static boolean infiniteDamage = false;
    private static boolean highSpeed = false;
    private static boolean showHitboxes = false;

    public static boolean isInfiniteHealth() {
        return infiniteHealth;
    }

    public static void setInfiniteHealth(final boolean infiniteHealth) {
        GameConfig.infiniteHealth = infiniteHealth;
    }

    public static boolean isInfiniteDamage() {
        return infiniteDamage;
    }

    public static void setInfiniteDamage(final boolean infiniteDamage) {
        GameConfig.infiniteDamage = infiniteDamage;
    }

    public static boolean isHighSpeed() {
        return highSpeed;
    }

    public static void setHighSpeed(final boolean highSpeed) {
        GameConfig.highSpeed = highSpeed;
    }

    public static boolean isShowHitboxes() {
        return showHitboxes;
    }

    public static void setShowHitboxes(final boolean showHitboxes) {
        GameConfig.showHitboxes = showHitboxes;
    }



    //PREVENT INSTANTIATION
    private GameConfig() {
        throw new UnsupportedOperationException("GameConfig is a utility class and cannot be instantiated");
    }
}
