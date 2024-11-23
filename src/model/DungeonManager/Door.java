package model.DungeonManager;

import model.Player.Player;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;

class Door {
    private final DoorDirection myDirection;
    private double myX, myY;
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

    private Rectangle2D.Double myRect;

    Door(final DoorDirection theDirection) {
        myDirection = theDirection;
        switch (myDirection) {
            case DoorDirection.UP:
                myX = NORTH_DOOR_X_OFFSET * TILE_DIMENSION; // Centered on top
                myY = 0;
                myRect = new Rectangle2D.Double(myX , myY, TILE_DIMENSION, TILE_DIMENSION);
                break;
            case DoorDirection.DOWN:
                myX = SOUTH_DOOR_X_OFFSET * TILE_DIMENSION;
                myY = SOUTH_DOOR_Y_OFFSET * TILE_DIMENSION;
                myRect = new Rectangle2D.Double(myX, myY, TILE_DIMENSION, HALF_TILE);
                break;
            case DoorDirection.LEFT:
                myX = 0;
                myY = WEST_DOOR_Y_OFFSET * TILE_DIMENSION;
                myRect = new Rectangle2D.Double(myX, myY, QUARTER_TILE, TILE_DIMENSION);
                break;
            case DoorDirection.RIGHT:
                myX = EAST_DOOR_X_OFFSET * TILE_DIMENSION;
                myY = EAST_DOOR_Y_OFFSET * TILE_DIMENSION;
                myRect = new Rectangle2D.Double(myX, myY, QUARTER_TILE, TILE_DIMENSION);
                break;
        }
    }

    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(DOOR_COLOR);
        theGraphics2D.fill(myRect);
    }

    boolean isPlayerColliding(final Player thePlayer) {
        Rectangle2D.Double playerBounds = new Rectangle2D.Double(thePlayer.getX() + thePlayer.getTileSize(), thePlayer.getY() + thePlayer.getTileSize(), thePlayer.getTileSize(), thePlayer.getTileSize() * 2);

        return playerBounds.intersects(myRect);
    }

    public double getX() {
        return myX;
    }

    public double getY() {
        return myY;
    }

    public Rectangle2D.Double getRect() {
        return myRect;
    }
}
