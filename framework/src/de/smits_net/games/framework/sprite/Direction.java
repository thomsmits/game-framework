/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprite;

/**
 * Enumeration to indicate the direction of an object.
 *
 * @author Thomas Smits
 */
public enum Direction {

    /** No direction. */
    NONE     ( 0.0000,  0.0000),

    /** Direction North. */
    NORTH    ( 0.0000, -1.0000),

    /** Direction North East. */
    NORTHEAST( 0.7071, -0.7071),

    /** Direction North West. */
    NORTHWEST(-0.7071, -0.7071),

    /** Direction East. */
    EAST     ( 1.0000,  0.0000),

    /** Direction West. */
    WEST     (-1.0000,  0.0000),

    /** Direction South. */
    SOUTH    ( 0.0000,  1.0000),

    /** Direction South West. */
    SOUTHWEST(-0.7071,  0.7071),

    /** Direction South East. */
    SOUTHEAST( 0.7071,  0.7071);

    /** Component in x direction. */
    private double x;

    /** Component in y direction. */
    private double y;

    /**
     * Internal constructor.
     *
     * @param x vector x component
     * @param y vector y component
     */
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
