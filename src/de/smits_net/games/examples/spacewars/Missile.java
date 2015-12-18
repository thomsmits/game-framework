/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewars;

import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.images.ImageStack;
import de.smits_net.games.framework.sprites.Sprite;

/**
 * Sprite of a missile fired by the space craft.
 *
 * @author Thomas Smits
 */
public class Missile extends Sprite {

    /** Speed of the missile.  */
    private final int MISSILE_SPEED = 4;

    /**
     * Create a new missile.
     *
     * @param board the board
     * @param x x position
     * @param y y position
     */
    public Missile(BoardBase board, int x, int y) {
        super(board, x, y, BoundaryPolicy.INVISIBLE,
                new ImageStack("assets/spacewars", "rocket.png"));

        setDeltaX(MISSILE_SPEED);
    }
}
