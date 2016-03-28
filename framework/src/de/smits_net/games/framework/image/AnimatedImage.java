/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.image;

import de.smits_net.games.framework.Constants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * A set of images that form an animated image. The difference
 * to the {@link ImagePack} is that the class automatically
 * tracks the time that passed since the last draw call and
 * changes the image automatically if the requested animatedImage
 * time has passed.
 *
 * @author Thomas Smits
 */
public class AnimatedImage extends ImageBase {

    /** Last change of the sprite. */
    protected long lastRun = System.nanoTime();

    /** Time between two frames in milliseconds. */
    protected int time;

    /** The images comprising the animatedImage. */
    protected ImagePack images;

    /**
     * Creates a new object. The animation will cycle infinitely through
     * the provided images.
     *
     * @param time the time one image is shown in milliseconds
     * @param images the images comprising the animatedImage
     */
    public AnimatedImage(int time, ImagePack images) {
        this(time, true, images);
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
        this.images = images;
        this.time = time;
        images.setWrapAround(wrapAround);
    }

    /**
     * Convenience constructor that creates the image pack for the
     * caller to make usage easier.
     *
     * @param time the time one image is shown in milliseconds
     * @param path path to the image
     * @param fileNames names of the files to be loaded
     */
    public AnimatedImage(int time, String path, String... fileNames) {
        this(time, new ImagePack(path, fileNames));
    }

    /**
     * Convenience constructor that creates the image pack for the
     * caller to make usage easier.
     *
     * @param time the time one image is shown in milliseconds
     * @param wrapAround if set to {@code true} the animation cycles infinitely
     *                   through the images. If set to {@code false} it stops at
     *                   the last image.
     * @param path path to the image
     * @param fileNames names of the files to be loaded
     */
    public AnimatedImage(int time, boolean wrapAround,
                         String path, String... fileNames) {
        this(time, wrapAround, new ImagePack(path, fileNames));
    }

    /**
     * Convenience constructor that creates the striped image for the
     * caller to make usage easier.
     *
     * @param time the time one image is shown in milliseconds
     * @param number the number of elements
     * @param imageFilePath the striped image
     */
    public AnimatedImage(int time, int number, String imageFilePath) {
        this(time, new StripedImage(imageFilePath, number));
    }

    @Override
    public Dimension getDimension() {
        return images.getDimension();
    }

    /**
     * Returns the images comprising the animatedImage.
     *
     * @return the images
     */
    public ImagePack getImages() {
        return images;
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
            buffer[k] = images.getImage(i);
        }

        return new AnimatedImage(time, new ImagePack(buffer));
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
            images.cycle();
            lastRun = System.nanoTime();
        }

        images.draw(g, position, observer);
    }
}
