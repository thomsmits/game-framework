/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.image;

import de.smits_net.games.framework.Constants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;

/**
 * A set of images that form an animated image. The difference
 * to the {@link ImagePack} is that the class automatically
 * tracks the time that'd passed since the last draw call and
 * changes the image automatically if the requested animatedImage
 * time has passed.
 *
 * @author Thomas Smits
 */
public class AnimatedImage extends ImagePack {

    /** Last change of the sprite. */
    protected long lastRun = System.nanoTime();

    /** Time between two frames in milliseconds. */
    protected volatile int time;

    /**
     * Creates a new object. The animation will cycle infinitely through
     * the provided images.
     *
     * @param time the time one image is shown in milliseconds
     * @param wrapAround if set to {@code true} the animation cycles infinitely
     *                   through the images. If set to {@code false} it stops at
     *                   the last image.
     * @param names the names of the images comprising the animatedImage
     */
    public AnimatedImage(int time, boolean wrapAround, String ...names) {
        this(time, wrapAround, new ImagePack(names));
    }

    /**
     * Creates a new animatedImage object.
     *
     * @param time the time one image is shown in milliseconds
     * @param wrapAround if set to {@code true} the animation cycles infinitely
     *                   through the images. If set to {@code false} it stops at
     *                   the last image.
     * @param images the images comprising the animatedImage
     */
    public AnimatedImage(int time, boolean wrapAround, ImagePack images) {
        this.time = time;
        images.setWrapAround(wrapAround);
        this.imageList = images.imageList;
    }

    /**
     * Return the images comprising the animatedImage.
     *
     * @return the images
     */
    public ImagePack getImages() {
        return this;
    }

    /**
     * Returns an animation that contains the images from this animation
     * starting with picture {@code start} (inclusive) up to picture
     * {@code end} (exclusive).
     *
     * @param start index of the first picture of the animation
     * @param end index of the last picture (exclusive) of the animation
     * @return a new animation with the selected images
     */
    public AnimatedImage getSubAnimation(int start, int end) {

        BufferedImage[] buffer = new BufferedImage[end - start];

        for (int i = start, k = 0; i < end; i++, k++) {
            buffer[k] = getImage(i);
        }

        return new AnimatedImage(time, true, new ImagePack(buffer));
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

        long timePassed = (System.nanoTime() - lastRun)
                / Constants.NANOSECONDS_PER_MILLISECOND;

        if (timePassed > time) {
            cycle();
            lastRun = System.nanoTime();
        }

        super.draw(g, position, observer);
    }
}
