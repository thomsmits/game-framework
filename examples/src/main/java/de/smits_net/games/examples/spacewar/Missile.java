/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewar;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.ImagePack;
import de.smits_net.games.framework.sprite.Sprite;
import de.smits_net.games.framework.sprite.Velocity;

import java.awt.Point;

/**
 * Sprite of a missile fired by the spacecraft.
 *
 * @author Thomas Smits
 */
public class Missile extends Sprite {

    /** Speed of the missile.  */
    private static final int MISSILE_SPEED = 4;

    /**
     * Create a new missile.
     *
     * @param board the board
     * @param startPoint point where sprite starts
     */
    public Missile(Board board, Point startPoint) {
        super(board, startPoint, BoundaryPolicy.INVISIBLE, Resources.IMAGE_MISSILE);
        setVelocity(new Velocity(MISSILE_SPEED, 0));
    }
}
