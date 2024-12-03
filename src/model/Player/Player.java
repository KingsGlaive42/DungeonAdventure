package model.Player;

import controller.InputListener;
import model.AnimationSystem.Animation;
import model.AnimationSystem.Sprite;
import model.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacters.Priestess;
import model.DungeonCharacters.Thief;
import model.DungeonCharacters.Warrior;
import model.DungeonManager.DoorDirection;
import model.GameObject;
import model.PlayerInventory.Inventory;
import utilities.SoundManager;
import utilities.GameConfig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

public class Player extends GameObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Constants
    private static final int MIN_X = -32;
    private static final int MAX_X = 480;
    private static final int MIN_Y = -32;
    private static final int MAX_Y = 330;
    private static final int STEP_DELAY = 300;

    // Transient Dependencies
    private transient InputListener myInputListener;
    private transient SoundManager mySoundManager;

    // Configurations
    private final int TILE_SIZE;
    private final int PLAYER_SIZE;

    // Sprites
    private final Sprite myWalkingSpritesheet = new Sprite();
    private final Sprite myIdleSpritesheet = new Sprite();

    // Hero and Inventory
    private DungeonCharacter myHeroClass;
    private Inventory myInventory;

    // Animations
    private Animation myWalkUpAnimation;
    private Animation myWalkDownAnimation;
    private Animation myWalkLeftAnimation;
    private Animation myWalkRightAnimation;
    private Animation myIdleUpAnimation;
    private Animation myIdleDownAnimation;
    private Animation myIdleLeftAnimation;
    private Animation myIdleRightAnimation;

    private Animation myAnimation;

    //Player State
    private boolean isMoving = false;
    private boolean wasMoving = false;
    private GameObject.FacingDirection myFacingDirection = GameObject.FacingDirection.DOWN;

    private long lastStepTime = 0;

    /**
     * Constructs a Player object with the specified Hero Class, Player Name, and Inventory.
     *
     * @param theCharacterClass The character class of the player (e.g., "Warrior", "Thief", "Priestess").
     * @param thePlayerName The name of the player character.
     * @param theInventory the inventory assigned to the player.
     */
    public Player(final String theCharacterClass, final String thePlayerName, final Inventory theInventory) {
        validateInput(theCharacterClass, "Character class");
        validateInput(thePlayerName, "Player name");

        myInputListener = InputListener.getInstance();
        mySoundManager = SoundManager.getInstance();

        TILE_SIZE = GameConfig.TILE_SIZE;
        PLAYER_SIZE = TILE_SIZE * 3; // Default Scale

        myInventory = theInventory;
        setHeroClass(theCharacterClass, thePlayerName);
        loadSpriteSheets();
        initializeAnimations();
        loadSoundEffects();
        setDefaultValues();
    }

    /**
     * Validates the input string to ensure it is not null or empty.
     *
     * @param theInput The input to validate.
     * @param theParameterName The name of the parameter for error reporting.
     * @throws IllegalArgumentException if the input is null or empty
     */
    private void validateInput(final String theInput, final String theParameterName) {
        if (theInput == null || theInput.isEmpty()) {
            throw new IllegalArgumentException(theParameterName + " cannot be null or empty.");
        }
    }

    /**
     * Sets the hero class based on the provided class name.
     *
     * @param theCharacterClass The character class name.
     * @param thePlayerName The name of the player.
     */
    private void setHeroClass(final String theCharacterClass, final String thePlayerName) {
        switch (theCharacterClass.toLowerCase()) {
            case "warrior" -> myHeroClass = new Warrior(thePlayerName);
            case "thief" -> myHeroClass = new Thief(thePlayerName);
            case "priestess" -> myHeroClass = new Priestess(thePlayerName);
            default -> throw new IllegalArgumentException("Invalid character class: " + theCharacterClass);
        }
    }

    /**
     * Loads sound effect for the player character.
     */
    private void loadSoundEffects() {
        mySoundManager.loadSoundEffect("step1", "src/resources/sounds/step.wav");
    }

    /**
     * Loads sprite sheets for the player character's walking and idle states.
     */
    private void loadSpriteSheets() {
        myWalkingSpritesheet.loadSprite("src/resources/assets/player/player_walking.png");
        myIdleSpritesheet.loadSprite("src/resources/assets/player/player_idle.png");
    }

    /**
     * Initializes animations for the player's walking and idle states in all directions.
     */
    private void initializeAnimations() {
        // Images for each animation
        BufferedImage[] myWalkingDownSprites = new BufferedImage[]{
                myWalkingSpritesheet.getSprite(0, 0),
                myWalkingSpritesheet.getSprite(1, 0),
                myWalkingSpritesheet.getSprite(2, 0),
                myWalkingSpritesheet.getSprite(3, 0),
                myWalkingSpritesheet.getSprite(4, 0),
                myWalkingSpritesheet.getSprite(5, 0),
        };

        BufferedImage[] myWalkingUpSprites = new BufferedImage[]{
                myWalkingSpritesheet.getSprite(0, 1),
                myWalkingSpritesheet.getSprite(1, 1),
                myWalkingSpritesheet.getSprite(2, 1),
                myWalkingSpritesheet.getSprite(3, 1),
                myWalkingSpritesheet.getSprite(4, 1),
                myWalkingSpritesheet.getSprite(5, 1)
        };
        BufferedImage[] myWalkingLeftSprites = new BufferedImage[]{
                myWalkingSpritesheet.getSprite(0, 2),
                myWalkingSpritesheet.getSprite(1, 2),
                myWalkingSpritesheet.getSprite(2, 2),
                myWalkingSpritesheet.getSprite(3, 2),
                myWalkingSpritesheet.getSprite(4, 2),
                myWalkingSpritesheet.getSprite(5, 2)
        };
        BufferedImage[] myWalkingRightSprites = new BufferedImage[]{
                myWalkingSpritesheet.getSprite(0, 3),
                myWalkingSpritesheet.getSprite(1, 3),
                myWalkingSpritesheet.getSprite(2, 3),
                myWalkingSpritesheet.getSprite(3, 3),
                myWalkingSpritesheet.getSprite(4, 3),
                myWalkingSpritesheet.getSprite(5, 3)
        };

        BufferedImage[] myIdleDownSprites = new BufferedImage[]{
                myIdleSpritesheet.getSprite(0, 0),
                myIdleSpritesheet.getSprite(1, 0),
                myIdleSpritesheet.getSprite(2, 0),
                myIdleSpritesheet.getSprite(3, 0),
                myIdleSpritesheet.getSprite(4, 0),
                myIdleSpritesheet.getSprite(5, 0),
                myIdleSpritesheet.getSprite(6, 0),
                myIdleSpritesheet.getSprite(7, 0),
                myIdleSpritesheet.getSprite(8, 0),
                myIdleSpritesheet.getSprite(9, 0),
                myIdleSpritesheet.getSprite(10, 0),
                myIdleSpritesheet.getSprite(11, 0)
        };
        BufferedImage[] myIdleUpSprites = new BufferedImage[]{
                myIdleSpritesheet.getSprite(0, 1),
                myIdleSpritesheet.getSprite(1, 1),
                myIdleSpritesheet.getSprite(2, 1),
                myIdleSpritesheet.getSprite(3, 1),
                myIdleSpritesheet.getSprite(4, 1),
                myIdleSpritesheet.getSprite(5, 1),
                myIdleSpritesheet.getSprite(6, 1),
                myIdleSpritesheet.getSprite(7, 1),
                myIdleSpritesheet.getSprite(8, 1),
                myIdleSpritesheet.getSprite(9, 1),
                myIdleSpritesheet.getSprite(10, 1),
                myIdleSpritesheet.getSprite(11, 1)
        };
        BufferedImage[] myIdleLeftSprites = new BufferedImage[]{
                myIdleSpritesheet.getSprite(0, 2),
                myIdleSpritesheet.getSprite(1, 2),
                myIdleSpritesheet.getSprite(2, 2),
                myIdleSpritesheet.getSprite(3, 2),
                myIdleSpritesheet.getSprite(4, 2),
                myIdleSpritesheet.getSprite(5, 2),
                myIdleSpritesheet.getSprite(6, 2),
                myIdleSpritesheet.getSprite(7, 2),
                myIdleSpritesheet.getSprite(8, 2),
                myIdleSpritesheet.getSprite(9, 2),
                myIdleSpritesheet.getSprite(10, 2),
                myIdleSpritesheet.getSprite(11, 2)
        };
        BufferedImage[] myIdleRightSprites = new BufferedImage[]{
                myIdleSpritesheet.getSprite(0, 3),
                myIdleSpritesheet.getSprite(1, 3),
                myIdleSpritesheet.getSprite(2, 3),
                myIdleSpritesheet.getSprite(3, 3),
                myIdleSpritesheet.getSprite(4, 3),
                myIdleSpritesheet.getSprite(5, 3),
                myIdleSpritesheet.getSprite(6, 3),
                myIdleSpritesheet.getSprite(7, 3),
                myIdleSpritesheet.getSprite(8, 3),
                myIdleSpritesheet.getSprite(9, 3),
                myIdleSpritesheet.getSprite(10, 3),
                myIdleSpritesheet.getSprite(11, 3)
        };

        myWalkUpAnimation = new Animation(myWalkingUpSprites, 5);
        myWalkDownAnimation = new Animation(myWalkingDownSprites, 5);
        myWalkLeftAnimation = new Animation(myWalkingLeftSprites, 5);
        myWalkRightAnimation = new Animation(myWalkingRightSprites, 5);

        myIdleUpAnimation = new Animation(myIdleUpSprites, 5);
        myIdleDownAnimation = new Animation(myIdleDownSprites, 5);
        myIdleLeftAnimation = new Animation(myIdleLeftSprites, 5);
        myIdleRightAnimation = new Animation(myIdleRightSprites, 5);

        // Set default animation
        myAnimation = myIdleDownAnimation;
        myAnimation.start();
    }

    /**
     * Updates the player's state, including movement and animations.
     */
    public void update() {
        applyMovement();
        applyMovementAnimations();
    }

    /**
     * Draws the player on the screen.
     *
     * @param graphics2D The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.PINK);

        AffineTransform transform = AffineTransform.getTranslateInstance(myX, myY);

        // If you want to scale the image as well, you can do this
        double scaleX = (double) PLAYER_SIZE / myAnimation.getSprite().getWidth(null);
        double scaleY = (double) PLAYER_SIZE / myAnimation.getSprite().getHeight(null);
        transform.scale(scaleX, scaleY);

        // Draw the image with the transform applied
        graphics2D.drawImage(myAnimation.getSprite(), transform, null);
        //graphics2D.fillRect(myX + TILE_SIZE, myY + TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    /**
     * Applies movement logic to update the player's position based on input.
     */
    private void applyMovement() {
        double diagonalSpeedFactor = 1.2; // Speed normalizer

        int horizontalSpeed = computeSpeed(myInputListener.isLeftPressed(), myInputListener.isRightPressed());
        int verticalSpeed = computeSpeed(myInputListener.isUpPressed(), myInputListener.isDownPressed());

        wasMoving = isMoving;
        isMoving = horizontalSpeed != 0 || verticalSpeed != 0;

        if (horizontalSpeed != 0 && verticalSpeed != 0) {
            // Diagonal movement: adjust speed
            horizontalSpeed = (int) (horizontalSpeed / Math.sqrt(2) * diagonalSpeedFactor);
            verticalSpeed = (int) (verticalSpeed / Math.sqrt(2) * diagonalSpeedFactor);
        }

        applyBounds(horizontalSpeed, verticalSpeed);
        updateFacingDirection(horizontalSpeed, verticalSpeed);
        playWalkSounds();
    }

    /**
     * Computes the speed based on the input keys pressed.
     *
     * @param isNegativePressed Whether the negative direction key is pressed.
     * @param isPositivePressed Whether the positive direction key is pressed.
     * @return the computed speed.
     */
    private int computeSpeed(final boolean isNegativePressed, final boolean isPositivePressed) {
        return isNegativePressed ? -mySpeed : isPositivePressed ? mySpeed : 0;
    }

    /**
     * Applies boundary checks to ensure the player stays within the game boundaries.
     *
     * @param theHorizontalSpeed The horizontal speed of the player.
     * @param theVerticalSpeed The vertical speed of the player.
     */
    private void applyBounds(final int theHorizontalSpeed, final int theVerticalSpeed) {
        myX = Math.max(MIN_X, Math.min(myX + theHorizontalSpeed, MAX_X));
        myY = Math.max(MIN_Y, Math.min(myY + theVerticalSpeed, MAX_Y));
    }

    /**
     * Updates the player's facing direction based on movement speed.
     *
     * @param theHorizontalSpeed The horizontal speed of the player.
     * @param theVerticalSpeed The vertical speed of the player.
     */
    private void updateFacingDirection(final int theHorizontalSpeed, final int theVerticalSpeed) {
        if (theHorizontalSpeed < 0) {
            myFacingDirection = FacingDirection.LEFT;
        } else if (theHorizontalSpeed > 0) {
            myFacingDirection = FacingDirection.RIGHT;
        } else if (theVerticalSpeed < 0) {
            myFacingDirection = FacingDirection.UP;
        } else if (theVerticalSpeed > 0) {
            myFacingDirection = FacingDirection.DOWN;
        }
    }

    /**
     * Plays the walking sound effect if the player is moving.
     */
    private void playWalkSounds() {
        if (isMoving && System.currentTimeMillis() - lastStepTime > STEP_DELAY) {
            mySoundManager.playSoundEffect("step1");
            lastStepTime = System.currentTimeMillis();
        }
    }

    /**
     * Applies movement animations to the player based on their direction and state.
     */
    private void applyMovementAnimations() {
        if (isMoving) {
            switch (myFacingDirection) {
                case LEFT:
                    if (myAnimation != myWalkLeftAnimation || !wasMoving) {
                        myAnimation = myWalkLeftAnimation;
                        myAnimation.restart();
                    }
                    break;
                case RIGHT:
                    if (myAnimation != myWalkRightAnimation || !wasMoving) {
                        myAnimation = myWalkRightAnimation;
                        myAnimation.restart();
                    }
                    break;
                case UP:
                    if (myAnimation != myWalkUpAnimation || !wasMoving) {
                        myAnimation = myWalkUpAnimation;
                        myAnimation.restart();
                    }
                    break;
                case DOWN:
                    if (myAnimation != myWalkDownAnimation || !wasMoving) {
                        myAnimation = myWalkDownAnimation;
                        myAnimation.restart();
                    }
                    break;
            }
        } else {
            switch (myFacingDirection) {
                case LEFT:
                    if (myAnimation != myIdleLeftAnimation) {
                        myAnimation = myIdleLeftAnimation;
                        myAnimation.restart();
                    }
                    break;
                case RIGHT:
                    if (myAnimation != myIdleRightAnimation) {
                        myAnimation = myIdleRightAnimation;
                        myAnimation.restart();
                    }
                    break;
                case UP:
                    if (myAnimation != myIdleUpAnimation) {
                        myAnimation = myIdleUpAnimation;
                        myAnimation.restart();
                    }
                    break;
                case DOWN:
                    if (myAnimation != myIdleDownAnimation) {
                        myAnimation = myIdleDownAnimation;
                        myAnimation.restart();
                    }
                    break;
            }
        }

        myAnimation.update();
    }

    /**
     * Moves the player to the opposite side of the door when transitioning rooms.
     *
     * @param theDoorDirection The direction of the door.
     */
    public void moveToOppositeDoor(final DoorDirection theDoorDirection) {
        switch (theDoorDirection) {
            case DoorDirection.DOWN:
                this.myY = 0;
                break;
            case DoorDirection.UP:
                this.myY = 9 * TILE_SIZE;
                break;
            case DoorDirection.RIGHT:
                this.myX = (double) TILE_SIZE / 2;
                break;
            case DoorDirection.LEFT:
                this.myX = 14 * TILE_SIZE;
                break;
            default:
                throw new IllegalArgumentException("Invalid DoorDirection: " + theDoorDirection);
        }
    }

    /**
     * Sets default values for player attributes like position and speed
     */
    private void setDefaultValues() {
        myX = 6.5 * TILE_SIZE;
        myY = 4 * TILE_SIZE;
        mySpeed = 6;
    }

    /**
     * Sets the player's inventory.
     *
     * @param theInventory The inventory to set.
     */
    public void setInventory(final Inventory theInventory) {
        this.myInventory = theInventory;
    }

    /**
     * Gets the player's inventory.
     *
     * @return The player's inventory.
     */
    public Inventory getMyInventory() {
        return myInventory;
    }

    /**
     * Gets the player's x-coordinate.
     *
     * @return The x-coordinate of the player.
     */
    public double getX() {
        return myX;
    }

    /**
     * Gets the player's y-coordinate.
     *
     * @return The y-coordinate of the player.
     */
    public double getY() {
        return myY;
    }

    /**
     * Gets the player speed
     * @return the current speed of the player.
     */
    public double getSpeed() {
        return mySpeed;
    }

    /**
     * Gets the tile size used for the player.
     *
     * @return The tile size.
     */
    public int getTileSize() {
        return TILE_SIZE;
    }

    /**
     * Gets the player's name.
     *
     * @return The name of the player.
     */
    public String getName() {
        return myHeroClass.getName();
    }

    /**
     * Custom deserialization method to restore transient fields.
     *
     * @param in The ObjectInputStream used to read the object.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class cannot be found.
     */
    @Serial
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        //System.out.println("Deserialized Room object.");

        setDefaultValues();

        this.myInputListener = InputListener.getInstance();
        this.mySoundManager = SoundManager.getInstance();

        loadSpriteSheets();
        //System.out.println("Reloaded sprite sheets.");

        initializeAnimations();
        //System.out.println("Initialized animations.");
    }
}
