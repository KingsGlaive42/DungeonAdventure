package controller;

import model.DungeonManager.Dungeon;
import model.Player.Player;
import view.UI;

import java.awt.*;

public class GameController {
    private Player myPlayer;
    private Dungeon myDungeon;
    private UI myUI;

    public GameController(final Player thePlayer, final Dungeon theDungeon) {
        myPlayer = thePlayer;
        myDungeon = theDungeon;
    }

    public void update() {
        myPlayer.update();
        myDungeon.checkDoorCollisions(myPlayer);
    }

    public void draw(final Graphics2D theGraphics2D) {
        myDungeon.getMyCurrentRoom().draw(theGraphics2D);
        myPlayer.draw(theGraphics2D);
        myUI.drawGameHUD(theGraphics2D);
    }

    public void setUI(final UI theUI) {
        myUI = theUI;
    }

    public void setMyPlayer(final Player thePlayer) {
        this.myPlayer = thePlayer;
    }

    public void setMyDungeon(final Dungeon theDungeon) {
        this.myDungeon = theDungeon;
    }

    public Player getPlayer() {
        return myPlayer;
    }

    public Dungeon getDungeon() {
        return myDungeon;
    }
}
