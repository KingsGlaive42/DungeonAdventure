package model;

public class GameObject {
    public enum  FacingDirection {
        UP, DOWN, LEFT, RIGHT
    }
    public double myX, myY;
    public int mySpeed;

    public void setPosition(final double theX, final double theY) {
        this.myX = theX;
        this.myY = theY;
    }
}
