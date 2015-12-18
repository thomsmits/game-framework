/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewars;

import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.images.StripedImage;
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
    public Alien(BoardBase board, int x, int y) {
        super(board, x, y, BoundaryPolicy.NONE,
                new StripedImage(
                "assets/spacewars/spike_fist_move_strip5.png", 5), 50);
        setDeltaX(ALIEN_SPEED);
    }

    /**
     * Let the alien explode.
     */
    public void explode() {
        setActive(false);
        setImages(new Explosion(), 20);
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
