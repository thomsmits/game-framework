/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprites;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.images.ImageStack;

/**
 *
 *
 * @author Thomas Smits
 */
public class DirectionAnimatedSprite extends AnimatedSprite {


    /** the image that is displayed */
    protected ImageStack left;

    protected ImageStack right;

    protected ImageStack up;

    protected ImageStack down;

    protected ImageStack noMovement;


    /**
     * @param board
     * @param x
     * @param y
     * @param policy
     * @param left
     * @param right
     * @param up
     * @param down
     * @param noMovement
     */
    public DirectionAnimatedSprite(BoardBase board, int x, int y, BoundaryPolicy policy,
            ImageStack noMovement,
            ImageStack left,
            ImageStack right,
            ImageStack up,
            ImageStack down) {

        super(board, x, y, policy, noMovement, 0);
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.noMovement = noMovement;
    }

    @Override
    public void draw(Graphics g, ImageObserver observer) {

        if (invisibleAfterFrames > 0) {
            invisibleAfterFrames--;
        }
        else if (invisibleAfterFrames == 0) {
            setVisible(false);
        }

        if (isVisible()) {
            ImageStack directionImages = null;
            if (deltaX > 0) {
                directionImages = right;
            }
            else if (deltaX < 0) {
                directionImages = left;
            }
            else {
                directionImages = noMovement;
            }

            directionImages.draw(g, position, observer);
            directionImages.cycle();

        }
    }
}
