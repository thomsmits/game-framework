/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.dodger;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;
import de.smits_net.games.framework.sprite.AnimatedSprite;

import java.awt.Point;

public class Sun extends AnimatedSprite {

    /**
     * Create a new sun.
     *
     * @param board the game board
     * @param startPoint point to start sprite
     */
    public Sun(Board board, Point startPoint, int speed) {
        super(board, startPoint, BoundaryPolicy.NONE,
                new AnimatedImage(50, "assets/dodger",
                        "sun1.png",
                        "sun2.png"
                ));

        setDeltaY(speed);
    }

}
