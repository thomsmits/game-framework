/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.image;

import javax.imageio.ImageIO;
import java.awt.Dimension;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for images used in the game.
 *
 * @author Thomas Smits
 */
public abstract class ImageBase {

    /** Cache for loaded images. */
    protected static Map<String, BufferedImage> imageCache = new HashMap<>();

    /** The current graphics configuration of the screen we are using. */
    private static final GraphicsConfiguration gc;

    static {
        // get the graphics environment
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
    }

    /**
     * Draw the image at the given position.
     *
     * @param g graphics context
     * @param position position to draw image at
     * @param observer image observer
     */
    public abstract void draw(Graphics g,
                              Point position,
                              ImageObserver observer);

    /**
     * Load the image.
     *
     * @param url pointing to the file
     * @return The loaded image
     */
    public static BufferedImage load(URL url) {
        try {
            return load(new File(url.toURI()));
        }
        catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Illegal URI " + url.toString(), ex);
        }
    }

    /**
     * Load the image.
     *
     * @param file path to the image
     * @return The loaded image
     */
    public static BufferedImage load(File file) {

        BufferedImage img = imageCache.get(file.getAbsolutePath());

        File fileToLad = file;

        if (img == null) {
            try {

                if (!fileToLad.exists()) {
                    fileToLad = new File(file.getPath() + ".png");
                }

                if (!fileToLad.exists()) {
                    fileToLad = new File(file.getPath() + ".gif");
                }

                if (!fileToLad.exists()) {
                    fileToLad = new File(file.getPath() + ".jpg");
                }

                img = ImageIO.read(fileToLad);
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
                throw new IllegalArgumentException(
                        "File '" + file.getPath() + "' not found.", e);
            }

            imageCache.put(file.getAbsolutePath(), img);
        }

        return img;
    }

    /**
     * Load the image from the class path.
     *
     * @param name path to the image
     * @return The loaded image
     */
    public static BufferedImage load(String name) {
        URL url = ImageBase.class.getResource(name);
        if (url == null) {
            throw new RuntimeException(name + " not found");
        }
        return load(ImageBase.class.getResource(name));
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
     * Dimension of the image.
     *
     * @return the dimension (in pixel)
     */
    public abstract Dimension getDimension();
}
