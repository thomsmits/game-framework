/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprites;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.images.ImagePack;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * A sprite with different animations depending on the direction
 * of movement.
 *
 * @author Thomas Smits
 */
public class DirectionAnimatedSprite extends AnimatedSprite {

    /** the image that is displayed when moving left */
    protected ImagePack left;

    /** the image that is displayed when moving right */
    protected ImagePack right;

    /** the image that is displayed when moving up */
    protected ImagePack up;

    /** the image that is displayed when moving down */
    protected ImagePack down;

    /** the image that is displayed when not moving at all */
    protected ImagePack noMovement;

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param x x position
     * @param y y position
     * @param policy policy used when sprite reaches
     * @param left animation for movement to the left
     * @param right animation for movement to the right
     * @param up animation for movement up
     * @param down animation for movement down
     * @param noMovement animation for no movement
     */
    public DirectionAnimatedSprite(Board board, int x, int y, BoundaryPolicy policy,
                                   ImagePack noMovement,
                                   ImagePack left,
                                   ImagePack right,
                                   ImagePack up,
                                   ImagePack down,
                                   int time) {

        super(board, x, y, policy, noMovement, 0);
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.noMovement = noMovement;
        this.time = time;
    }

    /**
     * @see Sprite#draw(Graphics, ImageObserver)
     */
    @Override
    public void draw(Graphics g, ImageObserver observer) {

        if (invisibleAfterFrames > 0) {
            invisibleAfterFrames--;
        }
        else if (invisibleAfterFrames == 0) {
            setVisible(false);
        }

        if (!isVisible()) {
            return;
        }

        ImagePack directionImages;

        if (deltaX > 0) {
            images = right;
        }
        else if (deltaX < 0) {
            images = left;
        }
        else if (deltaY > 0) {
            images = down;
        }
        else if (deltaY < 0) {
            images = up;
        }
        else {
            images = noMovement;
        }

        super.draw(g, observer);
    }
}
