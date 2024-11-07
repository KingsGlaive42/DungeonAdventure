package model.DungeonManager;

import model.Player.Player;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;

class Door {
    private final DoorDirection myDirection;
    private double myX, myY;
    private static final int DOOR_WIDTH = 32;
    private static final int DOOR_HEIGHT = 32;

    Door(final DoorDirection theDirection) {
        myDirection = theDirection;
        switch (myDirection) {
            case DoorDirection.UP:
                myX = 7.5 * DOOR_WIDTH; // Centered on top
                myY = 0;
                break;
            case DoorDirection.DOWN:
                myX = 7.5 * DOOR_WIDTH;
                myY = 11 * DOOR_HEIGHT;
                break;
            case DoorDirection.LEFT:
                myX = 0;
                myY = 5.5 * DOOR_HEIGHT;
                break;
            case DoorDirection.RIGHT:
                myX = 15 * DOOR_WIDTH;
                myY = 5.5 * DOOR_HEIGHT;
                break;
        }
    }

    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.BLUE);
        theGraphics2D.fill(new Rectangle2D.Double(myX, myY, DOOR_WIDTH, DOOR_HEIGHT));
    }

    boolean isPlayerColliding(final Player thePlayer) {
        Rectangle2D.Double playerBounds = new Rectangle2D.Double(thePlayer.getX() + thePlayer.getTileSize(), thePlayer.getY() + thePlayer.getTileSize(), thePlayer.getTileSize(), thePlayer.getTileSize());
        Rectangle2D.Double doorBounds = new Rectangle2D.Double(myX, myY, DOOR_WIDTH, DOOR_HEIGHT);
        return playerBounds.intersects(doorBounds);
    }
}
