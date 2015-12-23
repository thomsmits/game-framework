/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprite;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;
import de.smits_net.games.framework.image.ImagePack;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * A sprite with different animations depending on the direction
 * of movement.
 *
 * @author Thomas Smits
 */
public class DirectionAnimatedSprite extends AnimatedSprite {

    /** the animatedImage that is displayed when moving left */
    protected AnimatedImage left;

    /** the animatedImage that is displayed when moving right */
    protected AnimatedImage right;

    /** the animatedImage that is displayed when moving up */
    protected AnimatedImage up;

    /** the animatedImage that is displayed when moving down */
    protected AnimatedImage down;

    /** the animatedImage that is displayed when not moving at all */
    protected AnimatedImage noMovement;

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param x x position
     * @param y y position
     * @param policy policy used when sprite reaches
     * @param left animatedImage for movement to the left
     * @param right animatedImage for movement to the right
     * @param up animatedImage for movement up
     * @param down animatedImage for movement down
     * @param noMovement animatedImage for no movement
     */
    public DirectionAnimatedSprite(Board board, int x, int y, BoundaryPolicy policy,
                                   AnimatedImage noMovement,
                                   AnimatedImage left,
                                   AnimatedImage right,
                                   AnimatedImage up,
                                   AnimatedImage down) {

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
            animatedImage = right;
        }
        else if (deltaX < 0) {
            animatedImage = left;
        }
        else if (deltaY > 0) {
            animatedImage = down;
        }
        else if (deltaY < 0) {
            animatedImage = up;
        }
        else {
            animatedImage = noMovement;
        }

        super.draw(g, observer);
    }
}
