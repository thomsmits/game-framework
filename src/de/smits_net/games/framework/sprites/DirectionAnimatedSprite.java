/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprites;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.images.Animation;
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

    /** the animation that is displayed when moving left */
    protected Animation left;

    /** the animation that is displayed when moving right */
    protected Animation right;

    /** the animation that is displayed when moving up */
    protected Animation up;

    /** the animation that is displayed when moving down */
    protected Animation down;

    /** the animation that is displayed when not moving at all */
    protected Animation noMovement;

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
                                   Animation noMovement,
                                   Animation left,
                                   Animation right,
                                   Animation up,
                                   Animation down) {

        super(board, x, y, policy, noMovement);
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.noMovement = noMovement;
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
            animation = right;
        }
        else if (deltaX < 0) {
            animation = left;
        }
        else if (deltaY > 0) {
            animation = down;
        }
        else if (deltaY < 0) {
            animation = up;
        }
        else {
            animation = noMovement;
        }

        super.draw(g, observer);
    }
}
