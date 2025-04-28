/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A pack (collection) of images. This class can be used if more than one image
 * has to be transferred. It can also contain only one image.
 * <p>
 * The class has one "current" image, which can be retrieved using the
 * {@link ImagePack#getImage()} method. The user can cycle through the images with the
 * {@link ImagePack#cycle()} method.
 *
 * @author Thomas Smits
 */
public class ImagePack extends ImageBase implements Iterable<BufferedImage> {

    /** The images. */
    protected List<BufferedImage> imageList = new ArrayList<>();

    /** Image currently displayed, used for cycling through the images. */
    protected int currentImage = 0;

    /** If set to true, the cycling will go around forever, if set
     * to false. it stops with the last image */
    boolean wrapAround = true;

    /**
     * Constructor for internal use.
     */
    protected ImagePack() {
        // empty
    }

    /**
     * Creates a new set of images.
     *
     * @param urls URLs of the files to be loaded
     */
    public ImagePack(URL... urls) {
        if (urls.length == 0) {
            throw new IllegalArgumentException("At least one image required");
        }

        for (URL url : urls) {
            imageList.add(load(url));
        }
    }

    /**
     * Creates a new set of images.
     *
     * @param names names of images loaded from classpath
     */
    public ImagePack(String... names) {
        for (String name : names) {
            imageList.add(load(name));
        }
    }

    /**
     * Creates a new set of images.
     *
     * @param images to be stored
     */
    public ImagePack(BufferedImage... images) {
        Collections.addAll(imageList, images);
    }

    /**
     * Sets the wrap behavior.
     *
     * @param wrapAround if set to {@code true} (default), the images loop
     *                   forever.
     */
    public void setWrapAround(boolean wrapAround) {
        this.wrapAround = wrapAround;
    }

    /**
     * Adds an image to the stack.
     *
     * @param img the image.
     */
    public void addImage(Image img) {
        imageList.add(toBufferedImage(img));
    }

    /**
     * Cycle through the images.
     *
     * @return the current image
     */
    public Image cycle() {
        currentImage++;

        if (currentImage >= imageList.size()) {
            currentImage = wrapAround ? 0 : imageList.size() - 1;
        }

        return getImage();
    }

    /**
     * Gets the current image.
     *
     * @return the current image.
     */
    public BufferedImage getImage() {
        return imageList.get(currentImage);
    }

    /**
     * Gets the image at the given position.
     *
     * @param index position of the image
     * @return the image at the position.
     */
    public BufferedImage getImage(int index) {
        return imageList.get(index);
    }

    /**
     * Number of the images stored.
     *
     * @return the number of images.
     */
    public int size() {
        return imageList.size();
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<BufferedImage> iterator() {
        return imageList.iterator();
    }

    /**
     * Indicates, whether the images wrap around at the end.
     *
     * @return {@code true} if wrap around happens, otherwise {@code false}.
     */
    public boolean getWrapAround() {
        return wrapAround;
    }

    /**
     * Load a tiled image.
     *
     * @param img the image to be split up
     * @param boxWidth width of onw tile
     * @param boxHeight height of one tile
     * @return the images as a pack
     */
    public static ImagePack loadTiledImage(BufferedImage img, int boxWidth, int boxHeight) {

        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int tilesPerRow = imageWidth / boxWidth;
        int tilesPerColumn = imageHeight / boxHeight;

        List<BufferedImage> images = new ArrayList<>();

        int size = tilesPerColumn * tilesPerRow;
        int boxPerRow = img.getWidth() / boxWidth;

        for (int index = 0; index < size; index++) {
            int row = index / boxPerRow;
            int column = index % boxPerRow;

            int xpos = boxWidth * column;
            int ypos = boxWidth * row;

            images.add(img.getSubimage(xpos, ypos, boxWidth, boxHeight));
        }

        return new ImagePack(images.toArray(new BufferedImage[0]));
    }

    /**
     * Load a tiled image.
     *
     * @param path path to the image to be split up
     * @param boxWidth width of onw tile
     * @param boxHeight height of one tile
     * @return the images as a pack
     */
    public static ImagePack loadTiledImage(String path, int boxWidth, int boxHeight) {
        BufferedImage img = ImageBase.load(path);
        return loadTiledImage(img, boxWidth, boxHeight);
    }

    /**
     * Load a tiled image.
     *
     * @param url URL to the image to be split up
     * @param boxWidth width of onw tile
     * @param boxHeight height of one tile
     * @return the images as a pack
     */
    public static ImagePack loadTiledImage(URL url, int boxWidth, int boxHeight) {
        BufferedImage img = ImageBase.load(url);
        return loadTiledImage(img, boxWidth, boxHeight);
    }

    /**
     * Load a striped image.
     *
     * @param name path to the image to be split up
     * @param number number of sub-images
     * @return the images as a pack
     */
    public static ImagePack loadStripedImage(String name, int number) {
        BufferedImage img = ImageBase.load(name);
        return loadStripedImage(img, number);
    }

    /**
     * Load a striped image.
     *
     * @param url URL to the image to be split up
     * @param number number of sub-images
     * @return the images as a pack
     */
    public static ImagePack loadStripedImage(URL url, int number) {
        BufferedImage img = ImageBase.load(url);
        return loadStripedImage(img, number);
    }

    /**
     * Load a striped image.
     *
     * @param image the image to the image to be split up
     * @param number number of sub-images
     * @return the images as a pack
     */
    public static ImagePack loadStripedImage(BufferedImage image, int number) {
        return loadTiledImage(image, image.getWidth() / number, image.getHeight());
    }

    @Override
    public Dimension getDimension() {
        BufferedImage img = getImage();
        return new Dimension(img.getWidth(), img.getHeight());
    }

    @Override
    public void draw(Graphics g, Point position, ImageObserver observer) {
        g.drawImage(imageList.get(currentImage),
                position.x, position.y, observer);
    }

}
