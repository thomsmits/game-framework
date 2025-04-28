/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewar;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;
import de.smits_net.games.framework.image.ImagePack;
import de.smits_net.games.framework.sprite.AnimatedSprite;
import de.smits_net.games.framework.sprite.Direction;

import java.awt.*;

/**
 * Sprite of an alien.
 *
 * @author Thomas Smits
 */
public class Alien extends AnimatedSprite {

    /** speed of the alien in x direction. */
    private static final int ALIEN_SPEED = 1;


    /**
     * Create a new alien.
     *
     * @param board the game board
     * @param startPoint start position
     */
    public Alien(Board board, Point startPoint) {
        super(board, startPoint, BoundaryPolicy.NONE,
                new AnimatedImage(50, true,
                        ImagePack.loadStripedImage(
                                "/de/smits_net/games/examples/spacewar/spike_fist_move_strip5.png", 5)));
        velocity.setVelocity(Direction.WEST, ALIEN_SPEED);
    }

    /**
     * Let the alien explode.
     */
    public void explode() {
        final ImagePack explosion = ImagePack.loadStripedImage(
                        "/de/smits_net/games/examples/spacewar/explosion_1.png", 43);
        setActive(false);
        setImages(new AnimatedImage(20, true, explosion));
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
