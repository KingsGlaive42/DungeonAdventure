package view;

import controller.GameController;
import controller.GameEngine;
import controller.GameStateManager;
import model.AnimationSystem.AssetManager;
import model.DungeonManager.Dungeon;
import model.Player.Player;
import model.PlayerInventory.Inventory;
import model.SaveGame.GameSaver;
import model.SaveGame.GameState;
import model.SaveGame.SaveFileManager;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        AssetManager assetManager = new AssetManager();
        initializeAssets(assetManager);

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Game");

        GameState gameState = new GameState();
        SaveFileManager saveFileManager = new SaveFileManager();

        gameState.setMyDungeon(new Dungeon(20, 20, 20));
        gameState.setMyInventory(new Inventory(gameState.getMyDungeon()));
        gameState.setMyPlayer(new Player("Priestess", "Phil"));

        //testSaveFunctionality(saveFileManager, gameState);

        GameController gameController = new GameController(gameState.getMyPlayer(), gameState.getMyDungeon(), gameState.getMyInventory());
        GameStateManager gameStateManager = new GameStateManager(gameController);
        UI ui = new UI(gameStateManager, assetManager, saveFileManager, gameController);

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

    private static void initializeAssets(final AssetManager theAssetManager) {
        theAssetManager.loadAsset("playButton", "src/resources/assets/Buttons/Royal Buttons/Gold/royal gold button (not pressed).png");
        theAssetManager.loadAsset("loadButton", "src/resources/assets/Buttons/loadButtonImage.png");
        theAssetManager.loadAsset("saveButton", "src/resources/assets/Buttons/saveButtonImage.png");
        theAssetManager.loadAsset("optionButton", "src/resources/assets/Buttons/Colored Buttons/light orange/options.png");
        theAssetManager.loadAsset("exitButton", "src/resources/assets/Buttons/Colored Buttons/light orange/exit.png");
        theAssetManager.loadAsset("mapButton", "src/resources/assets/map-icon.png");
        theAssetManager.loadAsset("inventoryButton", "src/resources/assets/Potato_seeds.png");
        theAssetManager.loadAsset("slotButton", "src/resources/test_image.png");
        theAssetManager.loadAsset("backButton", "src/resources/assets/Buttons/backButton.png");
    }

    private static void testSaveFunctionality(SaveFileManager saveFileManager, GameState gameState) {
        String saveFileName = "saves/slot1.sav";

        // Ensure the directory for saving exists
        File saveDir = new File("saves");
        if (!saveDir.exists()) {
            if (!saveDir.mkdirs()) {
                System.err.println("Failed to create save directory!");
                return;
            }
        }

        // Test saving the game
        System.out.println("Testing Save Functionality...");
        try {
            GameSaver.saveGame(gameState, saveFileName);
            System.out.println("Game saved successfully to " + saveFileName);
        } catch (Exception e) {
            System.err.println("Error during saving: " + e.getMessage());
        }
    }
}
