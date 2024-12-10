package controller;

import model.DungeonCharacters.Hero;
import model.DungeonCharacters.Monster;
import model.DungeonManager.Dungeon;
import model.Player.Player;
import model.PlayerInventory.Inventory;
import model.PlayerInventory.Item;
import model.PlayerInventory.ItemType;
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
        if (myUI.getGameScreen().isMyInventoryVisible()) {
            myUI.getGameScreen().handleInventoryNavigation(InputListener.getInstance());
        } else {
            myPlayer.update();
            myDungeon.checkDoorCollisions(myPlayer, this);

            Monster collidedMonster = myDungeon.getMyCurrentRoom().checkPlayerCollisionWithMonsters(myPlayer);
            if (collidedMonster != null) {
                // start combat with monster
                System.out.println("COLLISION");
            }
        }
    }

    public void draw(final Graphics2D theGraphics2D) {
        myDungeon.getMyCurrentRoom().draw(theGraphics2D);
        myPlayer.draw(theGraphics2D);
        myUI.drawGameHUD(theGraphics2D);
    }


    public void useItem(final Item theItem, final Hero theHero, final Dungeon theDungeon, UI theUI) {
        if (theItem != null) {
            myInventory.useItem(theItem, theHero, theDungeon);
        }
    }

    public void setUI(final UI theUI) {
        myUI = theUI;
    }

    public UI getUI() {
        return myUI;
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
