package model.SaveGame;

import model.DungeonManager.Dungeon;
import model.Player.Player;
import model.PlayerInventory.Inventory;

import java.io.Serial;
import java.io.Serializable;

public class GameState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Player myPlayer;
    private Dungeon myDungeon;
    private Inventory myInventory;

    public Player getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Player myPlayer) {
        this.myPlayer = myPlayer;
    }

    public Dungeon getMyDungeon() {
        return myDungeon;
    }

    public void setMyDungeon(Dungeon myDungeon) {
        this.myDungeon = myDungeon;
    }

    public Inventory getMyInventory() {
        return myInventory;
    }

    public void setMyInventory(Inventory myInventory) {
        this.myInventory = myInventory;
    }

}
