/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewar;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.images.Animation;
import de.smits_net.games.framework.sprites.AnimatedSprite;

/**
 * Sprite of an alien.
 *
 * @author Thomas Smits
 */
public class Alien extends AnimatedSprite {

    /** speed of the alien in x direction */
    private static final int ALIEN_SPEED = -1;

    /**
     * Create a new alien.
     *
     * @param board the game board
     * @param x the x position
     * @param y the y position
     */
    public Alien(Board board, int x, int y) {
        super(board, x, y, BoundaryPolicy.NONE,
                new Animation(50, 5, "assets/spacewar/spike_fist_move_strip5.png"));
        setDeltaX(ALIEN_SPEED);
    }

    /**
     * Let the alien explode.
     */
    public void explode() {
        setActive(false);
        setImages(new Animation(20, new Explosion()));
        setInvisibleAfterFrames(30);
    }

    /**
     * A mouse click inside the alien will cause an explosion.
     */
    @Override
    public void mousePressed() {
        explode();
    }
}
