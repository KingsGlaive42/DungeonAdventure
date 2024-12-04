package controller;

import model.DungeonManager.Dungeon;
import model.Player.Player;
import model.PlayerInventory.Inventory;
import view.UI;

import java.awt.*;

public class GameController {
    private Player myPlayer;
    private Dungeon myDungeon;
    private Inventory myInventory;
    private UI myUI;

    public GameController(final Player thePlayer, final Dungeon theDungeon, final Inventory theInventory) {
        myPlayer = thePlayer;
        myDungeon = theDungeon;
        myInventory = theInventory;
    }

    public void update() {
        myPlayer.update();
        myDungeon.checkDoorCollisions(myPlayer, this);
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

    public void setMyInventory(final Inventory theInventory) {
        this.myInventory = theInventory;
    }

    public Player getPlayer() {
        return myPlayer;
    }

    public Dungeon getDungeon() {
        return myDungeon;
    }

    public Inventory getInventory() {
        return myInventory;
    }
}
