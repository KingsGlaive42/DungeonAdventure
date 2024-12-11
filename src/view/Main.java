package view;

import controller.CombatController;
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
        Dungeon dungeon = new Dungeon(20, 20, 20);
        gameState.setMyDungeon(dungeon);
        Inventory inventory = new Inventory(gameState.getMyDungeon());
        gameState.setMyInventory(inventory);
        gameState.setMyPlayer(new Player("Priestess", "Phil", inventory));

        //testSaveFunctionality(saveFileManager, gameState);
        CombatController combatController = new CombatController(gameState.getMyPlayer().getHeroClass(), null);
        GameController gameController = new GameController(gameState.getMyPlayer(), gameState.getMyDungeon(), gameState.getMyInventory());
        GameStateManager gameStateManager = new GameStateManager(gameController);
        UI ui = new UI(gameStateManager, assetManager, saveFileManager, gameController, combatController);

        CardLayoutManager cardLayoutManager = new CardLayoutManager(combatController, gameStateManager, ui);
        combatController.setCardLayoutManager(cardLayoutManager);
        gameController.setCardLayoutManager(cardLayoutManager);
        gameController.setCombatController(combatController);
        combatController.setCombatPanel(cardLayoutManager.getCombatPanel());
        combatController.setGameController(gameController);
        gameController.setUI(ui);
        gameStateManager.setUI(ui);

        window.add(cardLayoutManager.getCardPanel());

        window.pack(); // sizes window to fit preferred size and layouts of subcomponents

        window.setLocationRelativeTo(null); //display at center of screen
        window.setVisible(true);

        GameEngine engine = new GameEngine(cardLayoutManager.getGamePanel(), gameStateManager);
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
        theAssetManager.loadAsset("abstractionImage", "src/resources/assets/Buttons/AbstractionImage.png");
        theAssetManager.loadAsset("encapsulationImage", "src/resources/assets/Buttons/EncapsulationImage.png");
        theAssetManager.loadAsset("inheritanceImage", "src/resources/assets/Buttons/InheritanceButton.png");
        theAssetManager.loadAsset("polymorphismImage", "src/resources/assets/Buttons/PolymorphismButton.png");
        theAssetManager.loadAsset("warriorImage", "src/resources/assets/player/EdgarPortrait.png");
        theAssetManager.loadAsset("thiefImage", "src/resources/assets/player/SetzerPortrait.png");
        theAssetManager.loadAsset("priestessImage", "src/resources/assets/player/CelesPortrait.png");
        theAssetManager.loadAsset("berserkerImage", "src/resources/assets/player/LockePortrait.png");
        theAssetManager.loadAsset("mageImage", "src/resources/assets/player/TerraPortrait.png");
        theAssetManager.loadAsset("createGameButton", "src/resources/assets/Buttons/forwardButton.png");
        theAssetManager.loadAsset("easyButton", "src/resources/assets/Buttons/easyButton.png");
        theAssetManager.loadAsset("mediumButton", "src/resources/assets/Buttons/mediumButton.png");
        theAssetManager.loadAsset("hardButton", "src/resources/assets/Buttons/hardButton.png");
        theAssetManager.loadAsset("infiniteHealthImage", "src/resources/assets/Buttons/infiniteHealthImage.png");
        theAssetManager.loadAsset("infiniteDamageImage", "src/resources/assets/Buttons/infiniteDamageImage.png");
        theAssetManager.loadAsset("highSpeedImage", "src/resources/assets/Buttons/highSpeedImage.png");
        theAssetManager.loadAsset("showHitboxesImage", "src/resources/assets/Buttons/showHitboxImage.png");
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
