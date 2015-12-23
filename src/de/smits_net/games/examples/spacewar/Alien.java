/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewar;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;
import de.smits_net.games.framework.sprite.AnimatedSprite;

import java.awt.Point;

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
     * @param startPoint start position
     */
    public Alien(Board board, Point startPoint) {
        super(board, startPoint, BoundaryPolicy.NONE,
                new AnimatedImage(50, 5, "assets/spacewar/spike_fist_move_strip5.png"));
        setDeltaX(ALIEN_SPEED);
    }

    /**
     * Let the alien explode.
     */
    public void explode() {
        setActive(false);
        setImages(new AnimatedImage(20, new Explosion()));
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
