/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprite;

import static de.smits_net.games.framework.sprite.Direction.EAST;
import static de.smits_net.games.framework.sprite.Direction.NONE;
import static de.smits_net.games.framework.sprite.Direction.NORTH;
import static de.smits_net.games.framework.sprite.Direction.NORTHEAST;
import static de.smits_net.games.framework.sprite.Direction.SOUTH;
import static de.smits_net.games.framework.sprite.Direction.SOUTHEAST;
import static de.smits_net.games.framework.sprite.Direction.SOUTHWEST;
import static de.smits_net.games.framework.sprite.Direction.WEST;

/**
 * Class to encapsulate the velocity of a sprite.
 */
public class Velocity {

    /** velocity in x direction */
    public double x;

    /** velocity in y direction */
    public double y;

    /**
     * Creates a new object with the given velocity components.
     *
     * @param x component in x direction
     * @param y component in y direction
     */
    public Velocity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new object with an velocity of 0.
     */
    public Velocity() {
        this(0, 0);
    }

    /**
     * Sets the velocity in the given direction.
     *
     * @param direction the direction
     * @param speed the absolute value of the velocity
     */
    public void setVelocity(Direction direction, int speed) {
        x = direction.getX() * speed;
        y = direction.getY() * speed;
    }

    /**
     * Determines the direction of the current velocity vector.
     *
     * @return the direction.
     */
    public Direction direction() {

        Direction result;

        // to avoid rounding errors in the double values, we
        // are converting them to integers
        long dX = Math.round(x * 100);
        long dY = Math.round(y * 100);

        if (dX > 0) {

            if (dY > 0) {
                result = SOUTHEAST;
            }
            else if (dY < 0) {
                result = NORTHEAST;
            }
            else { // dY == 0
                result = EAST;
            }
        }
        else if (dX < 0) {

            if (dY > 0) {
                result = SOUTHWEST;
            }
            else if (dY < 0) {
                result = SOUTHEAST;
            }
            else { // dY == 0
                result = WEST;
            }
        }
        else { // dX == 0

            if (dY > 0) {
                result = SOUTH;
            }
            else if (dY < 0) {
                result = NORTH;
            }
            else { // dY == 0
                result = NONE;
            }
        }

        return result;
    }
}
