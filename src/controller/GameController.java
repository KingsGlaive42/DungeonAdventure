package controller;

import model.DungeonManager.Dungeon;
import model.Player.Player;
import view.UI;

import java.awt.*;

public class GameController {
    private final Player myPlayer;
    private final Dungeon myDungeon;
    private UI myUI;
    private final InputListener myInputListener;

    public GameController(final Player thePlayer, final Dungeon theDungeon) {
        myPlayer = thePlayer;
        myDungeon = theDungeon;
        myInputListener = InputListener.getInstance();
    }

    public void update() {
        myPlayer.update();
        myDungeon.checkDoorCollisions(myPlayer);

        if (myInputListener.isInventoryJustPressed()) {
            myUI.toggleInventoryScreen();
        }
        if (myInputListener.isMapJustPressed()) {
            myUI.toggleMapScreen();
        }
    }

    public void draw(final Graphics2D theGraphics2D) {
        myDungeon.getMyCurrentRoom().draw(theGraphics2D);
        myPlayer.draw(theGraphics2D);
        myUI.drawHUD(theGraphics2D);
    }

    public void setUI(final UI theUI) {
        myUI = theUI;
    }
}
