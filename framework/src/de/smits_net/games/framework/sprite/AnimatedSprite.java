/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprite;

import de.smits_net.games.framework.Constants;
import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;

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

    /** the image that is displayed. */
    protected AnimatedImage animatedImage;

    /** Hide the sprite after the given number of frames. */
    protected int invisibleAfterFrames = -1;

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param startPoint start point of the sprite
     * @param animatedImage the animatedImage
     */
    public AnimatedSprite(Board board, Point startPoint,
                          AnimatedImage animatedImage) {
        this(board, startPoint, BoundaryPolicy.STOP, animatedImage);
    }

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param startPoint start point of the sprite
     * @param policy the boundary policy for this sprite
     * @param animatedImage the animatedImage
     */
    public AnimatedSprite(Board board, Point startPoint, BoundaryPolicy policy,
                          AnimatedImage animatedImage) {
        super(board, startPoint, policy, animatedImage.getImages());
        this.animatedImage = animatedImage;
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
     * Set the animated image.
     *
     * @param newImages the new animatedImage
     */
    public void setImages(AnimatedImage newImages) {

        int offsetX = (this.animatedImage.getDimension().width
                - newImages.getDimension().width) / 2;
        int offsetY = (this.animatedImage.getDimension().height
                - newImages.getDimension().height) / 2;

        this.animatedImage = newImages;

        position.x = position.x + offsetX;
        position.y = position.y + offsetY;
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

        animatedImage.draw(g, getPosition(), observer);

        if (Constants.DEBUG_SPRITE_OUTLINE) {
            g.setColor(isActive() ? Color.RED : Color.GREEN);
            g.drawPolygon(absoluteBorder());
        }
    }
}
