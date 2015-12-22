/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.images;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for images used in the game.
 *
 * @author Thomas Smits
 */
public abstract class ImageBase {

    /** Cache for loaded images */
    protected static Map<String, BufferedImage> imageCache = new HashMap<>();

    /** The current graphics configuration of the screen we are using */
    private static GraphicsConfiguration gc;

    static {
        // get the graphics environment
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
    }

    /**
     * Draw the image at the given position.
     *
     * @param g graphics context
     * @param position position to draw image at
     * @param observer image observer
     */
    public abstract void draw(Graphics g, Point position, ImageObserver observer);

    /**
     * Load the image.
     *
     * @param file path to the image
     * @return The loaded image
     */
    public static BufferedImage loadImage(File file) {

        BufferedImage img = imageCache.get(file.getAbsolutePath());

        if (img == null) {
            try {
                img = ImageIO.read(file);
                int transparency = img.getColorModel().getTransparency();

                // create an image especially suitable for the graphics
                // environment
                BufferedImage newImage = gc.createCompatibleImage(
                        img.getWidth(), img.getHeight(), transparency);

                Graphics2D g = newImage.createGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
            }
            catch (IOException e) {
                throw new IllegalArgumentException("File '" + file.getPath() + "' not found.");
            }

            imageCache.put(file.getAbsolutePath(), img);
        }

        return img;
    }

    /**
     * Load the image.
     *
     * @param imagePath path to the image
     * @return The loaded image
     */
    public static BufferedImage loadImage(String imagePath) {
        return loadImage(new File(imagePath));
    }

    /**
     * Convert an image to a buffered image.
     *
     * @param image the image to be converted
     * @return the image as buffered image
     */
    public static BufferedImage toBufferedImage(Image image) {

        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }

    /**
     * Width of the image.
     *
     * @return the width (in pixel)
     */
    public abstract int getWidth();

    /**
     * Height of the image.
     *
     * @return the height (in pixel)
     */
    public abstract int getHeight();
}
