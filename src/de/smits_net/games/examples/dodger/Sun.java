/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.dodger;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.images.ImagePack;
import de.smits_net.games.framework.sprites.AnimatedSprite;

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
                new ImagePack(
                        "assets/dodger",
                        "sun1.png",
                        "sun2.png"
                ), 50);

        setDeltaY(speed);
    }

}
