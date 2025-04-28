/* (c) 2016 Thomas Smits */
package de.smits_net.games.framework.image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * A simple image, without any animation.
 */
public class SimpleImage extends ImageBase {

    /** The Image encapsulated in this object. */
    protected final BufferedImage image;

    /**
     * Create a new simple image.
     *
     * @param image the image.
     */
    public SimpleImage(BufferedImage image) {
        this.image = image;
    }


    public SimpleImage(String name) {
        this(ImageBase.load(name));
    }

    /**
     * Returns this image as an AWT buffered image.
     *
     * @return the buffered image object.
     */
    public BufferedImage getBufferedImage() {
        return image;
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    @Override
    public void draw(Graphics g, Point position, ImageObserver observer) {
        g.drawImage(image, position.x, position.y, observer);
    }
}
