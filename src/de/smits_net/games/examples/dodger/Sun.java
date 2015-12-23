/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.dodger;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;
import de.smits_net.games.framework.sprite.AnimatedSprite;

public class Sun extends AnimatedSprite {

    /**
     * Create a new alien.
     *
     * @param board the game board
     * @param x the x position
     * @param y the y position
     */
    public Sun(Board board, int x, int y, int speed) {
        super(board, x, y, BoundaryPolicy.NONE,
                new AnimatedImage(50, "assets/dodger",
                        "sun1.png",
                        "sun2.png"
                ));

        setDeltaY(speed);
    }

}
