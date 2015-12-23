/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprite;

/**
 * Enumeration to indicate the direction of an object.
 */
public enum Direction {

    NONE     ( 0.0000,  0.0000),
    NORTH    ( 0.0000, -1.0000),
    NORTHEAST( 0.7071, -0.7071),
    NORTHWEST(-0.7071, -0.7071),
    EAST     ( 1.0000,  0.0000),
    WEST     (-1.0000,  0.0000),
    SOUTH    ( 0.0000,  1.0000),
    SOUTHWEST(-0.7071,  0.7071),
    SOUTHEAST( 0.7071,  0.7071);

    /** Component in x direction */
    private double x;

    /** Component in y direction */
    private double y;

    Direction(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x component of the direction vector.
     *
     * @return x component
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y component of the direction vector.
     *
     * @return y component
     */
    public double getY() {
        return y;
    }
}
