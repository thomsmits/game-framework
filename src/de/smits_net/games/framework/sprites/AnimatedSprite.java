/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprites;

import de.smits_net.games.framework.Constants;
import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.images.ImageStack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * An animated sprite, i.e. a sprite that has several different images which are
 * displayed one after the other with a given speed.
 *
 * @author Thomas Smits
 */
public abstract class AnimatedSprite extends Sprite {

    /** the image that is displayed */
    protected ImageStack images;

    /** Time to sleep between two animations */
    protected int time;

    /** Hide the sprite after the given number of frames */
    protected int invisibleAfterFrames = -1;

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param x x position
     * @param y y position
     * @param images the image
     * @param time time to sleep between two animations
     */
    public AnimatedSprite(Board board, int x, int y, ImageStack images, int time) {
        this(board, x, y, BoundaryPolicy.STOP, images, time);
    }

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param x x position
     * @param y y position
     * @param imgs the image(s)
     * @param time time to sleep between two animations
     */
    public AnimatedSprite(Board board, int x, int y, BoundaryPolicy policy, ImageStack imgs,
                          int time) {

        super(board, x, y, policy, imgs);

        this.images = imgs;
        this.time = time;
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
     * Set the images.
     *
     * @param imgs the images
     */
    public void setImages(ImageStack imgs) {
        setImages(imgs, this.time);
    }

    /**
     * Set the images and the animation speed.
     *
     * @param imgs the images
     * @param time the time
     */
    public void setImages(ImageStack imgs, int time) {

        int offsetX = (images.getWidth() - imgs.getWidth()) / 2;
        int offsetY = (images.getHeight() - imgs.getHeight()) / 2;

        this.images = imgs;
        this.time = time;

        position.translate(offsetX, offsetY);
    }

    /** Last change of the sprite */
    protected long lastRun = System.nanoTime();

    /**
     * @see Sprite#draw(Graphics, ImageObserver)
     */
    @Override
    public void draw(Graphics g, ImageObserver observer) {

        if (!isVisible()) {
            return;
        }

        long timePassed = (System.nanoTime() - lastRun) / Constants.NANOSECONDS_PER_MILLISECOND;

        if (timePassed > time) {
            images.cycle();
            lastRun = System.nanoTime();

            if (invisibleAfterFrames > 0) {
                invisibleAfterFrames--;
            }
            else if (invisibleAfterFrames == 0) {
                setVisible(false);
            }
        }

        images.draw(g, position, observer);

        if (Constants.DEBUG_SPRITE_OUTLINE) {
            g.setColor(isActive() ? Color.RED : Color.GREEN);
            g.drawPolygon(absoluteBorder());
        }
    }
}
