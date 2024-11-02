package model.DungeonManager;

import model.Player.Player;

import java.awt.*;

public class Door {
    private final String myDirection;
    private int myX, myY;
    private static final int DOOR_WIDTH = 32;
    private static final int DOOR_HEIGHT = 32;

    public Door(final String theDirection) {
        myDirection = theDirection;
        switch (myDirection) {
            case "UP":
                myX = 7 * DOOR_WIDTH; // Centered on top
                myY = 0;
                break;
            case "DOWN":
                myX = 7 * DOOR_WIDTH;
                myY = 11 * DOOR_HEIGHT;
                break;
            case "LEFT":
                myX = 0;
                myY = 6 * DOOR_HEIGHT;
                break;
            case "RIGHT":
                myX = 15 * DOOR_WIDTH;
                myY = 6 * DOOR_HEIGHT;
                break;
        }
    }

    public void draw(final Graphics2D theGraphics2D) {
        theGraphics2D.setColor(Color.BLUE);
        theGraphics2D.fillRect(myX, myY, DOOR_WIDTH, DOOR_HEIGHT);
    }

    public boolean isPlayerColliding(final Player thePlayer) {
        Rectangle playerBounds = new Rectangle(thePlayer.getX() + thePlayer.getTileSize(), thePlayer.getY() + thePlayer.getTileSize(), thePlayer.getTileSize(), thePlayer.getTileSize());
        Rectangle doorBounds = new Rectangle(myX, myY, DOOR_WIDTH, DOOR_HEIGHT);
        return playerBounds.intersects(doorBounds);
    }

    public String getDirection() {
        return myDirection;
    }
}
