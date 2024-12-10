package model.DungeonManager;

import model.GameConfig;
import model.Player.Player;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Door within a room in the dungeon.
 * Each door has a direction and specific coordinates, used for player collision and rendering.
 */
public class Door implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Constants for door positioning and dimensions
    private static final int TILE_DIMENSION = 32;
    private static final int HALF_TILE = 16;
    private static final int QUARTER_TILE = 8;

    private static final int NORTH_DOOR_X_OFFSET = 8;
    private static final int SOUTH_DOOR_X_OFFSET = 8;
    private static final double SOUTH_DOOR_Y_OFFSET = 12.5;
    private static final int WEST_DOOR_Y_OFFSET = 6;
    private static final double EAST_DOOR_X_OFFSET = 16.75;
    private static final int EAST_DOOR_Y_OFFSET = 6;

    private static final Color DOOR_COLOR = new Color(0x421d00);

    // Door properties
    private final DoorDirection myDirection;
    private final double myX;
    private final double myY;
    private final Rectangle2D.Double myRect;

    /**
     * Constructs a door based on its direction.
     *
     * @param theDirection The direction the door is facing.
     * @throws IllegalArgumentException if theDirection is null or unsupported.
     */
    public Door(final DoorDirection theDirection) {
        if (theDirection == null) {
            throw new IllegalArgumentException("Direction must not be null");
        }
        myDirection = theDirection;
        switch (myDirection) {
            case UP:
                myX = NORTH_DOOR_X_OFFSET * TILE_DIMENSION; // Centered on top
                myY = 0;
                myRect = new Rectangle2D.Double(myX , myY, TILE_DIMENSION, TILE_DIMENSION);
                break;
            case DOWN:
                myX = SOUTH_DOOR_X_OFFSET * TILE_DIMENSION;
                myY = SOUTH_DOOR_Y_OFFSET * TILE_DIMENSION;
                myRect = new Rectangle2D.Double(myX, myY, TILE_DIMENSION, HALF_TILE);
                break;
            case LEFT:
                myX = 0;
                myY = WEST_DOOR_Y_OFFSET * TILE_DIMENSION;
                myRect = new Rectangle2D.Double(myX, myY, QUARTER_TILE, TILE_DIMENSION);
                break;
            case RIGHT:
                myX = EAST_DOOR_X_OFFSET * TILE_DIMENSION;
                myY = EAST_DOOR_Y_OFFSET * TILE_DIMENSION;
                myRect = new Rectangle2D.Double(myX, myY, QUARTER_TILE, TILE_DIMENSION);
                break;
            default:
                throw new IllegalArgumentException("Unsupported DoorDirection: " + theDirection);
        }
    }

    /**
     * Draws the door on the screen.
     *
     * @param theGraphics2D The Graphics2D context to draw the door.
     * @throws IllegalArgumentException if theGraphics2D is null.
     */
    public void draw(final Graphics2D theGraphics2D) {
        if (theGraphics2D == null) {
            throw new IllegalArgumentException("Graphics2D must not be null");
        }
        theGraphics2D.setColor(DOOR_COLOR);
        theGraphics2D.fill(myRect);

        if (GameConfig.isShowHitboxes()) {
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            theGraphics2D.setComposite(ac);
            theGraphics2D.setColor(Color.CYAN);
            theGraphics2D.fill(myRect);
            theGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    /**
     * Checks if the player is colliding with this door.
     *
     * @param thePlayer The player to check for collisions.
     * @return True if the player is colliding with the door, false otherwise.
     * @throws IllegalArgumentException if thePlayer is null.
     */
    public boolean isPlayerColliding(final Player thePlayer) {
        if (thePlayer == null) {
            throw new IllegalArgumentException("Player must not be null");
        }

        Rectangle2D.Double playerBounds = new Rectangle2D.Double(
                thePlayer.getX() + thePlayer.getTileSize(),
                thePlayer.getY() + thePlayer.getTileSize(),
                   thePlayer.getTileSize(),
                thePlayer.getTileSize() * 2
        );

        return playerBounds.intersects(myRect);
    }

    public double getX() {
        return myX;
    }

    public double getY() {
        return myY;
    }
}