/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprites;

import de.smits_net.games.framework.Constants;
import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.images.ImageStack;

import java.awt.Color;
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
    protected ImageStack left;

    /** the image that is displayed when moving right */
    protected ImageStack right;

    /** the image that is displayed when moving up */
    protected ImageStack up;

    /** the image that is displayed when moving down */
    protected ImageStack down;

    /** the image that is displayed when not moving at all */
    protected ImageStack noMovement;

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

        ImageStack directionImages;

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

        if (Constants.DEBUG_SPRITE_OUTLINE) {
            g.setColor(isActive() ? Color.RED : Color.GREEN);
            g.drawPolygon(absoluteBorder());
        }
    }
}
