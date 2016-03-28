/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * Auto scrolling background image for a game.
 *
 * @author Thomas Smits
 */
public class BackgroundImage {

    /** Current scroll position. */
    private double scroll;

    /** Scroll speed. */
    private double speed;

    /** The image. */
    protected BufferedImage background;

    /**
     * Creates a new background image.
     *
     * @param speed speed of the scroll operation
     * @param width width of the image
     * @param height height of the image
     */
    public BackgroundImage(double speed, int width, int height) {
        background = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        this.speed = speed;
    }

    /**
     * Creates a new background image.
     *
     * @param speed speed of the scroll operation
     * @param image the image to be used as the background
     */
    public BackgroundImage(double speed, BufferedImage image) {
        background = image;
        this.speed = speed;
    }

    /**
     * Move the image with the given speed.
     */
    public void move() {
        scroll += speed;

        if (-scroll > getWidth()) {
            scroll = 0;
        }
    }

    /**
     * Get the width of the image.
     *
     * @return the width in pixel.
     */
    public int getWidth() {
        return background.getWidth();
    }

    /**
     * Get the height of the image.
     *
     * @return the height in pixel.
     */
    public int getHeight() {
        return background.getHeight();
    }

    /**
     * Draw the background picture.
     *
     * @param g the graphics context to draw into
     * @param observer the image observer
     */
    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(background, (int) scroll, 0, observer);
        g.drawImage(background, background.getWidth() + (int) scroll,
                0, observer);
    }
}
