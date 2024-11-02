package model.Player;

import controller.InputListener;
import model.AnimationSystem.Animation;
import model.AnimationSystem.Sprite;
import model.DungeonCharacters.DungeonCharacter;
import model.DungeonCharacters.Priestess;
import model.DungeonCharacters.Thief;
import model.DungeonCharacters.Warrior;
import model.GameObject;
import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    private final GamePanel myGamePanel;
    private final InputListener myInputListener;
    private final int TILE_SIZE;
    private final int PLAYER_SCALE = 3;
    private final int PLAYER_SIZE;

    private final Sprite myWalkingSpritesheet = new Sprite();
    private final Sprite myIdleSpritesheet = new Sprite();

    private DungeonCharacter myHeroClass;

    // Images for each animation
    private BufferedImage[] myWalkingDownSprites;
    private BufferedImage[] myWalkingUpSprites;
    private BufferedImage[] myWalkingLeftSprites;
    private BufferedImage[] myWalkingRightSprites;

    private BufferedImage[] myIdleDownSprites;
    private BufferedImage[] myIdleUpSprites;
    private BufferedImage[] myIdleLeftSprites;
    private BufferedImage[] myIdleRightSprites;

    // These are animation states
    private Animation myWalkUpAnimation;
    private Animation myWalkDownAnimation;
    private Animation myWalkLeftAnimation;
    private Animation myWalkRightAnimation;

    private Animation myIdleUpAnimation;
    private Animation myIdleDownAnimation;
    private Animation myIdleLeftAnimation;
    private Animation myIdleRightAnimation;

    // This is the actual animation
    private Animation myAnimation;

    private boolean isMoving = false;
    private boolean wasMoving = false;
    private GameObject.FacingDirection myFacingDirection = GameObject.FacingDirection.DOWN;

    public Player(final GamePanel theGamePanel, final InputListener theInputListener, final String theCharacterClass, final String thePlayerName) {
        this.myGamePanel = theGamePanel;
        this.myInputListener = theInputListener;
        this.TILE_SIZE = myGamePanel.getTileSize();
        this.PLAYER_SIZE = TILE_SIZE * PLAYER_SCALE;

        setHeroClass(theCharacterClass, thePlayerName);

        loadSpriteSheets();
        initializeAnimations();

        setDefaultValues();
    }

    private void setHeroClass(final String theCharacterClass, final String thePlayerName) {
        switch (theCharacterClass.toLowerCase()) {
            case "warrior":
                this.myHeroClass = new Warrior(thePlayerName);
                break;
            case "thief":
                this.myHeroClass = new Thief(thePlayerName);
                break;
            case "priestess":
                this.myHeroClass = new Priestess(thePlayerName);
                break;
            default:
                throw new IllegalArgumentException("Invalid character class selected.");
        }
    }

    private void loadSpriteSheets() {
        myWalkingSpritesheet.loadSprite("src/resources/assets/player/player_walking.png");
        myIdleSpritesheet.loadSprite("src/resources/assets/player/player_idle.png");
    }

    private void initializeAnimations() {
        myWalkingDownSprites = new BufferedImage[]{
                myWalkingSpritesheet.getSprite(0, 0),
                myWalkingSpritesheet.getSprite(1, 0),
                myWalkingSpritesheet.getSprite(2, 0),
                myWalkingSpritesheet.getSprite(3, 0),
                myWalkingSpritesheet.getSprite(4, 0),
                myWalkingSpritesheet.getSprite(5, 0),
        };

        myWalkingUpSprites = new BufferedImage[]{
                myWalkingSpritesheet.getSprite(0, 1),
                myWalkingSpritesheet.getSprite(1, 1),
                myWalkingSpritesheet.getSprite(2, 1),
                myWalkingSpritesheet.getSprite(3, 1),
                myWalkingSpritesheet.getSprite(4, 1),
                myWalkingSpritesheet.getSprite(5, 1)
        };
        myWalkingLeftSprites = new BufferedImage[]{
                myWalkingSpritesheet.getSprite(0, 2),
                myWalkingSpritesheet.getSprite(1, 2),
                myWalkingSpritesheet.getSprite(2, 2),
                myWalkingSpritesheet.getSprite(3, 2),
                myWalkingSpritesheet.getSprite(4, 2),
                myWalkingSpritesheet.getSprite(5, 2)
        };
        myWalkingRightSprites = new BufferedImage[]{
                myWalkingSpritesheet.getSprite(0, 3),
                myWalkingSpritesheet.getSprite(1, 3),
                myWalkingSpritesheet.getSprite(2, 3),
                myWalkingSpritesheet.getSprite(3, 3),
                myWalkingSpritesheet.getSprite(4, 3),
                myWalkingSpritesheet.getSprite(5, 3)
        };

        myIdleDownSprites = new BufferedImage[]{
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
        myIdleUpSprites = new BufferedImage[]{
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
        myIdleLeftSprites = new BufferedImage[]{
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
        myIdleRightSprites = new BufferedImage[]{
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

    private void setDefaultValues() {
        myX = 100;
        myY = 100;
        mySpeed = 4;
    }

    public void update() {
        applyMovement();
        applyMovementAnimations();
    }

    private void applyMovement() {
        double diagonalSpeedFactor = 1.2; // Speed normalizer

        int horizontalSpeed = myInputListener.isLeftPressed() ? -mySpeed :
                myInputListener.isRightPressed() ? mySpeed : 0;
        int verticalSpeed = myInputListener.isUpPressed() ? -mySpeed :
                myInputListener.isDownPressed() ? mySpeed : 0;

        wasMoving = isMoving;
        isMoving = horizontalSpeed != 0 || verticalSpeed != 0;

        if (horizontalSpeed != 0 && verticalSpeed != 0) {
            // Diagonal movement: adjust speed
            horizontalSpeed = (int) (horizontalSpeed / Math.sqrt(2) * diagonalSpeedFactor);
            verticalSpeed = (int) (verticalSpeed / Math.sqrt(2) * diagonalSpeedFactor);
        }

        myX += horizontalSpeed;
        myY += verticalSpeed;

        // Update facing direction based on movement
        if (horizontalSpeed < 0) {
            myFacingDirection = FacingDirection.LEFT;
        } else if (horizontalSpeed > 0) {
            myFacingDirection = FacingDirection.RIGHT;
        } else if (verticalSpeed < 0) {
            myFacingDirection = FacingDirection.UP;
        } else if (verticalSpeed > 0) {
            myFacingDirection = FacingDirection.DOWN;
        }
    }

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

    public void moveToOppositeDoor(final String theDoorDirection) {
        switch (theDoorDirection) {
            case "UP":
                this.myX = 7 * TILE_SIZE;
                this.myY = 6 * TILE_SIZE;
                break;
            case "DOWN":
                this.myX = 7 * TILE_SIZE;
                this.myY = 6 * TILE_SIZE;
                break;
            case "LEFT":
                this.myX = 7 * TILE_SIZE;
                this.myY = 6 * TILE_SIZE;
                break;
            case "RIGHT":
                this.myX = 7 * TILE_SIZE;
                this.myY = 6 * TILE_SIZE;
                break;
        }
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    public int getSize() {
        return PLAYER_SIZE;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.PINK);
        graphics2D.drawImage(myAnimation.getSprite(), myX, myY, PLAYER_SIZE, PLAYER_SIZE, null);
        graphics2D.fillRect(myX + TILE_SIZE, myY + TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}
