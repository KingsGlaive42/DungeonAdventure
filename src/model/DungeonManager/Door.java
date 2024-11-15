package model.DungeonManager;

import model.Player.Player;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;

class Door {
    private final DoorDirection myDirection;
    private double myX, myY;
    private static final int TILE_DIMENSION = 32;

    Door(final DoorDirection theDirection) {
        myDirection = theDirection;
        switch (myDirection) {
            case DoorDirection.UP:
                myX = 8 * TILE_DIMENSION; // Centered on top
                myY = 0;
                break;
            case DoorDirection.DOWN:
                myX = 8 * TILE_DIMENSION;
                myY = 12 * TILE_DIMENSION;
                break;
            case DoorDirection.LEFT:
                myX = 0;
                myY = 6 * TILE_DIMENSION;
                break;
            case DoorDirection.RIGHT:
                myX = 16 * TILE_DIMENSION;
                myY = 6 * TILE_DIMENSION;
                break;
        }
    }

    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(new Color(0x421d00));
        switch (myDirection) {
            case DoorDirection.UP:
                theGraphics2D.fill(new Rectangle2D.Double(myX , myY, TILE_DIMENSION, TILE_DIMENSION));
                break;
            case DoorDirection.DOWN:
                theGraphics2D.fill(new Rectangle2D.Double(myX, myY + 16, TILE_DIMENSION, (double) TILE_DIMENSION / 2));
                break;
            case DoorDirection.LEFT:
                theGraphics2D.fill(new Rectangle2D.Double(myX, myY, (double) TILE_DIMENSION / 4, TILE_DIMENSION));
                break;
            case DoorDirection.RIGHT:
                theGraphics2D.fill(new Rectangle2D.Double(myX + 24, myY, (double) TILE_DIMENSION / 4, TILE_DIMENSION));
                break;
        }

    }

    boolean isPlayerColliding(final Player thePlayer) {
        Rectangle2D.Double playerBounds = new Rectangle2D.Double(thePlayer.getX() + thePlayer.getTileSize(), thePlayer.getY() + thePlayer.getTileSize(), thePlayer.getTileSize(), thePlayer.getTileSize() * 2);
        Rectangle2D.Double doorBounds;

        switch (myDirection) {
            case DoorDirection.UP:
                doorBounds = new Rectangle2D.Double(myX , myY, TILE_DIMENSION, (double) TILE_DIMENSION);
                break;
            case DoorDirection.DOWN:
                doorBounds = new Rectangle2D.Double(myX, myY + 24, TILE_DIMENSION, (double) TILE_DIMENSION / 2);
                break;
            case DoorDirection.LEFT:
                doorBounds = new Rectangle2D.Double(myX, myY, (double) TILE_DIMENSION / 4, TILE_DIMENSION);
                break;
            case DoorDirection.RIGHT:
                doorBounds = new Rectangle2D.Double(myX + 24, myY, (double) TILE_DIMENSION / 4, TILE_DIMENSION);
                break;
            default:
                doorBounds = new Rectangle2D.Double(myX, myY, TILE_DIMENSION, TILE_DIMENSION);
        }

        return playerBounds.intersects(doorBounds);
    }
}
