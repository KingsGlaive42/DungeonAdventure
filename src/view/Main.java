package view;

import controller.GameController;
import controller.GameEngine;
import controller.GameStateManager;
import model.DungeonManager.Dungeon;
import model.Player.Player;
import model.PlayerInventory.Inventory;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Game");

        Dungeon dungeon = new Dungeon(20, 20, 20);
        Inventory inventory = new Inventory(dungeon);
        Player player = new Player("Warrior", "Warrior", inventory);

        GameController gameController = new GameController(player, dungeon);
        GameStateManager gameStateManager = new GameStateManager(gameController);
        UI ui = new UI(gameStateManager, inventory);

        GamePanel gamePanel = new GamePanel(gameStateManager, ui);
        gameController.setUI(ui);
        gameStateManager.setUI(ui);

        window.add(gamePanel);

        window.pack(); // sizes window to fit preferred size and layouts of subcomponents

        window.setLocationRelativeTo(null); //display at center of screen
        window.setVisible(true);

        GameEngine engine = new GameEngine(gamePanel, gameStateManager);
        engine.startGame();
    }
}
