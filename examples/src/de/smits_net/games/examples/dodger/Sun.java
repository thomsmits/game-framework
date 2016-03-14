/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.dodger;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;
import de.smits_net.games.framework.sprite.AnimatedSprite;
import de.smits_net.games.framework.sprite.Direction;

import java.awt.Point;

/**
 * A sun falling from the sky.
 */
public class Sun extends AnimatedSprite {

    /**
     * Create a new sun.
     *
     * @param board the game board
     * @param startPoint point to start sprite
     * @param speed speed of the animation
     */
    public Sun(Board board, Point startPoint, int speed) {
        super(board, startPoint, BoundaryPolicy.NONE,
                new AnimatedImage(50, "assets/dodger",
                        "sun1.png",
                        "sun2.png"
                ));

        velocity.setVelocity(Direction.SOUTH, speed);
    }

}
