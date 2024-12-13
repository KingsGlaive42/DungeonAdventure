package controller;

import model.DungeonCharacters.Hero;
import model.DungeonCharacters.Monster;
import model.DungeonManager.Dungeon;
import model.DungeonManager.Room;
import model.Player.Player;
import model.PlayerInventory.Inventory;
import model.PlayerInventory.Item;
import model.PlayerInventory.ItemType;
import view.CardLayoutManager;
import view.UI;

import java.awt.*;

/**
 * This is the Game Controller
 *
 * @author Jayden Fausto
 */
public class GameController {
    private Player myPlayer;
    private Dungeon myDungeon;
    private Inventory myInventory;
    private UI myUI;
    private CardLayoutManager myCardLayoutManager;
    private CombatController myCombatController;

    /**
     * Game Controller Constructor
     *
     * @param thePlayer Player Character.
     * @param theDungeon Dungeon.
     * @param theInventory Player Inventory.
     */
    public GameController(final Player thePlayer, final Dungeon theDungeon, final Inventory theInventory) {
        myPlayer = thePlayer;
        myDungeon = theDungeon;
        myInventory = theInventory;
    }

    /**
     * This method updates the game anytime the player does anything.
     */
    public void update() {
        if ("CombatPanel".equals(myCardLayoutManager.getCurrentPanel())) {
            // Do nothing if the combat panel is active
            return;
        }
        if (myUI.getGameScreen().isMyInventoryVisible()) {
            myUI.getGameScreen().handleInventoryNavigation(InputListener.getInstance());
        } else {
            myPlayer.update();
            myDungeon.checkDoorCollisions(myPlayer, this);

            Monster collidedMonster = myDungeon.getMyCurrentRoom().checkPlayerCollisionWithMonsters(myPlayer);
            if (collidedMonster != null) {
                // start combat with monster
                myCombatController.startCombat(collidedMonster);
                myCardLayoutManager.switchToCombatPanel();
                myDungeon.getMyCurrentRoom().removeMonster(collidedMonster);
            }

            if (myDungeon.getMyCurrentRoom().checkPlayerCollisionWithTreasureChest(myPlayer)) {
                if (myInventory.hasAllPillars()) {
                    transitionToEndGameScreen();
                } else {
                    //System.out.println("You need all four pillars to open the treasure chest");
                    myUI.getGameScreen().showDialogue("You need all four pillars to \nopen the treasure chest!");
                }
            }
        }
    }

    /**
     * This method announces the player has won.
     */
    public void transitionToEndGameScreen() {
        myUI.getGameScreen().showDialogue("You win!");
        //System.out.println("you win");
    }

    /**
     * This method sets the CardLayoutManager for
     * transitioning to combat and back.
     *
     * @param theCardLayoutManager CardLayoutManager.
     */
    public void setCardLayoutManager(final CardLayoutManager theCardLayoutManager) {
        myCardLayoutManager = theCardLayoutManager;
    }

    /**
     * This method sets the CombatController for
     * setting up combat in the Combat MVC.
     *
     * @param theCombatController CombatController.
     */
    public void setCombatController(final CombatController theCombatController) {
        myCombatController = theCombatController;
    }

    /**
     * This method draws the view.
     *
     * @param theGraphics2D Graphics.
     */
    public void draw(final Graphics2D theGraphics2D) {
        myDungeon.getMyCurrentRoom().draw(theGraphics2D);
        myPlayer.draw(theGraphics2D);
        myUI.drawGameHUD(theGraphics2D);
    }

    /**
     * This method uses an item in the Player's inventory.
     *
     * @param theItem Item ot be used.
     * @param theHero Hero that is using item.
     * @param theDungeon the Dungeon.
     * @param theUI The UI.
     */
    public void useItem(final Item theItem, final Hero theHero, final Dungeon theDungeon, final UI theUI) {
        if (theItem != null) {
            myInventory.useItem(theItem, theHero, theDungeon, theUI);
        }
    }

    /**
     * This method sets the UI
     *
     * @param theUI UI to be set.
     */
    public void setUI(final UI theUI) {
        myUI = theUI;
    }

    /**
     * This method returns the UI
     *
     * @return UI.
     */
    public UI getUI() {
        return myUI;
    }

    /**
     * This method sets the Player.
     *
     * @param thePlayer Player to be set
     */
    public void setMyPlayer(final Player thePlayer) {
        this.myPlayer = thePlayer;
    }

    /**
     * This method sets the Dungeon.
     *
     * @param theDungeon Dungeon to be set.
     */
    public void setMyDungeon(final Dungeon theDungeon) {
        this.myDungeon = theDungeon;
    }

    /**
     * This method sets the Inventory.
     *
     * @param theInventory Inventory to be set.
     */
    public void setMyInventory(final Inventory theInventory) {
        this.myInventory = theInventory;
    }

    /**
     * This method returns the Player.
     *
     * @return Player.
     */
    public Player getPlayer() {
        return myPlayer;
    }

    /**
     * This method returns the Dungeon.
     *
     * @return the Dungeon
     */
    public Dungeon getDungeon() {
        return myDungeon;
    }

    /**
     * This method returns the Inventory.
     *
     * @return Inventory.
     */
    public Inventory getInventory() {
        return myInventory;
    }
}
