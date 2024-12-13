package view;

import controller.CombatController;
import controller.GameController;
import controller.GameStateManager;
import model.DungeonManager.Dungeon;
import model.GameConfig;
import model.Player.Player;

import java.awt.*;

/**
 * This is the Loading Screen Class
 *
 * @author Jayden Fausto
 */
public class LoadingScreen implements Screen{
    private final GameController myGameController;
    private final GameStateManager myGameStateManager;
    private final CombatController myCombatController;

    private boolean isLoading = false;

    /**
     * Loading Screen Constructor
     *
     * @param theGameController GameController.
     * @param theGameStateManager GameStateManager.
     * @param theCombatController CombatController.
     */
    public LoadingScreen(final GameController theGameController, final GameStateManager theGameStateManager, final CombatController theCombatController) {
        myGameController = theGameController;
        myGameStateManager = theGameStateManager;
        myCombatController = theCombatController;
    }

    /**
     * This method loads the game.
     *
     * @param thePlayer Player Character.
     * @param theDungeonWidth Dungeon Width.
     * @param theDungeonHeight Dungeon Height.
     * @param theNumRooms Number of Rooms.
     */
    public void loadGame(final Player thePlayer, final int theDungeonWidth, final int theDungeonHeight, final int theNumRooms) {
        isLoading = true;

        new Thread(() -> {
            try {
                Thread.sleep(1000);

                myGameController.setMyPlayer(thePlayer);
                myGameController.setMyDungeon(new Dungeon(theDungeonWidth, theDungeonHeight, theNumRooms));
                myCombatController.setHero(thePlayer.getHeroClass());

                myGameStateManager.setState(GameStateManager.State.GAME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                isLoading = false;
            }
        }).start();
    }

    /**
     * This method draws the screen.
     *
     * @param theGraphics2D Graphics.
     */
    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.BLACK);
        theGraphics2D.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.setFont(new Font("Arial", Font.BOLD, 48));
        theGraphics2D.drawString("LOADING...", GameConfig.SCREEN_WIDTH / 2 - 150, GameConfig.SCREEN_HEIGHT / 2);
    }

    /**
     * This method handles clicking in loading screen.
     *
     * @param theClickPoint Click point.
     */
    @Override
    public void handleClick(final Point theClickPoint) {

    }

    /**
     * This method handles hovering in loading screen.
     *
     * @param theMousePoint Hovering point.
     */
    @Override
    public void handleHoverUpdate(final Point theMousePoint) {

    }

    /**
     * This method checks if loading is happening.
     *
     * @return true if loading, false if not.
     */
    public boolean isLoading() {
        return isLoading;
    }
}
