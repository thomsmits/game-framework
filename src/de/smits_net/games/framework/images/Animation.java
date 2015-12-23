/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.images;

import de.smits_net.games.framework.Constants;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;

/**
 * A set of images that form an animation. The difference
 * to the {@see ImagePack} is that the class automatically
 * tracks the time that passed since the last draw call and
 * changes the image automatically if the requested animation
 * time has passed.
 */
public class Animation extends ImageBase {

    /** Last change of the sprite */
    protected long lastRun = System.nanoTime();

    /** Time between two frames in milliseconds */
    protected int time;

    /** The images comprising the animation */
    protected ImagePack images;

    /**
     * Creates a new animation object.
     *
     * @param images the images comprising the animation
     * @param time the time one image is shown in milliseconds
     */
    public Animation(ImagePack images, int time) {
        this.images = images;
        this.time = time;
    }

    @Override
    public int getWidth() {
        return images.getWidth();
    }

    @Override
    public int getHeight() {
        return images.getHeight();
    }

    /**
     * Returns the images comprising the animation.
     *
     * @return the images
     */
    public ImagePack getImages() {
        return images;
    }

    /**
     * Returns the time before the image is changed.
     *
     * @return time in milliseconds
     */
    public int getTime() {
        return time;
    }

    @Override
    public void draw(Graphics g, Point position, ImageObserver observer) {

        long timePassed = (System.nanoTime() - lastRun) / Constants.NANOSECONDS_PER_MILLISECOND;

        if (timePassed > time) {
            images.cycle();
            lastRun = System.nanoTime();
        }

        images.draw(g, position, observer);
    }
}
