package view;

import controller.CombatController;
import controller.GameController;
import controller.GameStateManager;
import model.DungeonManager.Dungeon;
import model.GameConfig;
import model.Player.Player;

import java.awt.*;

public class LoadingScreen {
    private final GameController myGameController;
    private final GameStateManager myGameStateManager;
    private final CombatController myCombatController;

    private boolean isLoading = false;

    public LoadingScreen(final GameController theGameController, final GameStateManager theGameStateManager, final CombatController theCombatController) {
        myGameController = theGameController;
        myGameStateManager = theGameStateManager;
        myCombatController = theCombatController;
    }

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

    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.BLACK);
        theGraphics2D.fillRect(0, 0, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);

        theGraphics2D.setColor(Color.WHITE);
        theGraphics2D.setFont(new Font("Arial", Font.BOLD, 48));
        theGraphics2D.drawString("LOADING...", GameConfig.SCREEN_WIDTH / 2 - 150, GameConfig.SCREEN_HEIGHT / 2);
    }

    public boolean isLoading() {
        return isLoading;
    }
}
