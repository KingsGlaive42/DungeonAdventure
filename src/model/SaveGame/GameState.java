package model.SaveGame;

import model.DungeonManager.Dungeon;
import model.Player.Player;
import model.PlayerInventory.Inventory;

import java.io.Serial;
import java.io.Serializable;

public class GameState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Classes that make up the entirety of a Game State
     */
    private Player myPlayer;
    private Dungeon myDungeon;
    private Inventory myInventory;

    /**
     * Getter for the player.
     * @return the saved Player
     */
    public Player getMyPlayer() {
        return myPlayer;
    }

    /**
     * Sets the player for the state.
     * @param thePlayer the player to save.
     */
    public void setMyPlayer(final Player thePlayer) {
        if (thePlayer == null ) {
            throw new IllegalArgumentException("Player cannot be null in game state");
        }

        this.myPlayer = thePlayer;
    }

    /**
     * Getter for the dungeon.
     * @return the saved dungeon
     */
    public Dungeon getMyDungeon() {
        return myDungeon;
    }

    /**
     * Sets the dungeon for the state.
     * @param theDungeon the dungeon to save.
     */
    public void setMyDungeon(final Dungeon theDungeon) {
        if (theDungeon == null ) {
            throw new IllegalArgumentException("Player cannot be null in game state");
        }

        this.myDungeon = theDungeon;
    }

    /**
     * Getter for the inventory.
     * @return the saved inventory
     */
    public Inventory getMyInventory() {
        return myInventory;
    }

    /**
     * Sets the inventory for the state.
     * @param theInventory inventory to save.
     */
    public void setMyInventory(final Inventory theInventory) {
        if (theInventory == null ) {
            throw new IllegalArgumentException("Player cannot be null in game state");
        }
        this.myInventory = theInventory;
    }

}
