/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.character;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;
import de.smits_net.games.framework.sprite.DirectionAnimatedSprite;

import java.awt.Point;

/**
 * Base class for moveable characters in the game. Characters are similar
 * to sprites with the difference that they have a target location and move
 * to this location with a given speed and then stop there.
 */
public class Character extends DirectionAnimatedSprite {

    /** Position where the character should move to */
    protected Point target;

    /** The speed the character moves with */
    protected int speed;

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param x x position
     * @param y y position
     * @param speed the speed of the object
     * @param policy policy used when sprite reaches
     * @param noDirection animation for no direction
     * @param west animation for movement to the west
     * @param east animation for movement to the east
     * @param north animation for movement north
     * @param south animation for movement south
     * @param noMovementWest animation for non-moving sprite, facing to the west
     * @param noMovementEast animation for non-moving sprite, facing to the east
     * @param noMovementNorth animation for non-moving sprite, facing to the north
     * @param noMovementSouth animation for non-moving sprite, facing to the south
     */
    public Character(Board board, int x, int y, int speed,
                                    BoundaryPolicy policy,
                                   AnimatedImage noDirection,
                                   AnimatedImage west,
                                   AnimatedImage east,
                                   AnimatedImage north,
                                   AnimatedImage south,
                                   AnimatedImage noMovementWest,
                                   AnimatedImage noMovementEast,
                                   AnimatedImage noMovementNorth,
                                   AnimatedImage noMovementSouth) {

        super(board, x, y, policy, noDirection, north, null, east, null, south, null, west, null,
                noMovementNorth, null, noMovementEast, null, noMovementSouth, noMovementWest, null, null);

        // start with target = position
        target = new Point(x, y);

        this.speed = speed;
    }

    /**
     * Sets the target position of the character.
     *
     * @param target target position
     */
    public void setTarget(Point target) {

        Point newTarget = (Point)(target.clone());

        if (newTarget.x < lowerBounds.x) {
            newTarget.x = lowerBounds.x;
        }

        if (newTarget.y < lowerBounds.y) {
            newTarget.y = lowerBounds.y;
        }

        if (newTarget.x > upperBounds.x) {
            newTarget.x = upperBounds.x;
        }

        if (newTarget.y > upperBounds.y) {
            newTarget.y = upperBounds.y;
        }

        System.out.printf("Target: %s%n", newTarget);
        this.target = newTarget;
    }

    @Override
    public void move() {

        double distanceX = target.x - positionX;
        double distanceY = target.y - positionY;

        if (Math.abs(distanceX) < 0.1 && Math.abs(distanceY) < 0.1 ) {
            return;
        }

        double alpha = Math.atan(distanceY / distanceX);

        if (Math.abs(alpha) < 0.01) {
            deltaX = speed * Math.signum(distanceX);
            deltaY = 0.0;
        }
        else {
            deltaX = Math.cos(alpha) * speed;
            deltaY = Math.sin(alpha) * speed;
        }

        double lengthSpeedVector = deltaX*deltaX + deltaY*deltaY;
        double lengthDistance = distanceX*distanceX + distanceY*distanceY;

        if (lengthDistance < lengthSpeedVector) {
            deltaX = 0;
            deltaY = 0;
            positionX = target.x;
            positionY = target.y;
        }
        else {
            positionX += deltaX;
            positionY += deltaY;
        }

        System.out.printf("direction=%s, alpha=%f, distanceX=%f, distanceY=%f, deltaX=%f, deltaY=%f%n", currentDirection, alpha, distanceX, distanceY, deltaX, deltaY);

        ensureBoundaryPolicy();

    }
}
