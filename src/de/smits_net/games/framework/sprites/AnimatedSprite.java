/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprites;

import de.smits_net.games.framework.Constants;
import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.images.Animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;

/**
 * An animated sprite, i.e. a sprite that has several different images which are
 * displayed one after the other with a given speed.
 *
 * @author Thomas Smits
 */
public abstract class AnimatedSprite extends Sprite {

    /** the image that is displayed */
    protected Animation animation;

    /** Hide the sprite after the given number of frames */
    protected int invisibleAfterFrames = -1;

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param x x position
     * @param y y position
     * @param animation the animation
     */
    public AnimatedSprite(Board board, int x, int y, Animation animation) {
        this(board, x, y, BoundaryPolicy.STOP, animation);
    }

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param x x position
     * @param y y position
     * @param animation the animation
     */
    public AnimatedSprite(Board board, int x, int y, BoundaryPolicy policy, Animation animation) {
        super(board, x, y, policy, animation.getImages());
        this.animation = animation;
    }

    /**
     * Sets the number of frames the sprite is invisible after.
     *
     * @param invisibleAfterFrames number of frames
     */
    public void setInvisibleAfterFrames(int invisibleAfterFrames) {
        this.invisibleAfterFrames = invisibleAfterFrames;
    }

    /**
     * Set the animation.
     *
     * @param animation the new animation
     */
    public void setImages(Animation animation) {

        int offsetX = (animation.getWidth() - animation.getWidth()) / 2;
        int offsetY = (animation.getHeight() - animation.getHeight()) / 2;

        this.animation = animation;

        positionX = positionX + offsetX;
        positionY = positionY + offsetY;
    }

    /**
     * @see Sprite#draw(Graphics, ImageObserver)
     */
    @Override
    public void draw(Graphics g, ImageObserver observer) {

        if (!isVisible()) {
            return;
        }

        if (invisibleAfterFrames > 0) {
            invisibleAfterFrames--;
        }
        else if (invisibleAfterFrames == 0) {
            setVisible(false);
        }

        animation.draw(g, new Point((int)positionX, (int)positionY), observer);

        if (Constants.DEBUG_SPRITE_OUTLINE) {
            g.setColor(isActive() ? Color.RED : Color.GREEN);
            g.drawPolygon(absoluteBorder());
        }
    }
}
