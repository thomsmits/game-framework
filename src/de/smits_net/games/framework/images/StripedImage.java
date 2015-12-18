/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.images;

import java.awt.image.BufferedImage;

/**
 * An image with all sub images in one row.
 */
public class StripedImage extends TiledImage {

    /**
     * Creates a new striped image with the given number of elements.
     *
     * @param image the image
     * @param number the number of elements
     */
    public StripedImage(BufferedImage image, int number) {
        super(image, image.getWidth() / number, image.getHeight());
    }

    /**
     * Create a new image.
     *
     * @param path path to the image
     * @param number the number of elements
     */
    public StripedImage(String path, int number) {
        this(ImageBase.loadImage(path), number);
    }
}
