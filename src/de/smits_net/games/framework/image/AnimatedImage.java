/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.image;

import de.smits_net.games.framework.Constants;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;

/**
 * A set of images that form an animatedImage. The difference
 * to the {@see ImagePack} is that the class automatically
 * tracks the time that passed since the last draw call and
 * changes the image automatically if the requested animatedImage
 * time has passed.
 */
public class AnimatedImage extends ImageBase {

    /** Last change of the sprite */
    protected long lastRun = System.nanoTime();

    /** Time between two frames in milliseconds */
    protected int time;

    /** The images comprising the animatedImage */
    protected ImagePack images;

    /**
     * Creates a new animatedImage object.
     *
     * @param time the time one image is shown in milliseconds
     * @param images the images comprising the animatedImage
     */
    public AnimatedImage(int time, ImagePack images) {
        this.images = images;
        this.time = time;
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
    public int getWidth() {
        return images.getWidth();
    }

    @Override
    public int getHeight() {
        return images.getHeight();
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
