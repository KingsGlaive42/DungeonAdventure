package model;

/**
 * Represents a generic game object with a position and speed.
 */
public class GameObject {
    /**
     * Enumeration representing the possible facing directions of the game object.
     */
    public enum FacingDirection {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * The X-coordinate of the game object.
     */
    public double myX;

    /**
     * The Y-coordinate of the game object.
     */
    public double myY;

    /**
     * The speed of the game object.
     */
    public int mySpeed;

    /**
     * Sets the position of the game object.
     *
     * @param theX the new X-coordinate of the game object.
     * @param theY the new Y-coordinate of the game object.
     */
    public void setPosition(final double theX, final double theY) {
        this.myX = theX;
        this.myY = theY;
    }
}
