/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A pack (collection) of images. This class can be used if more than one image
 * has to be transferred. It can also contain only one image.
 *
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

    /** If set to true the cycling will go around forever, if set
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
     * @param path path to the image
     * @param fileNames names of the files to be loaded
     */
    public ImagePack(String path, String... fileNames) {

        File base = new File(path);

        if (fileNames.length == 0) {
            throw new IllegalArgumentException("At least one image required");
        }

        for (String imageFile : fileNames) {
            File imagePath = new File(base, imageFile);
            imageList.add(loadImage(imagePath.getAbsolutePath()));
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
    void setWrapAround(boolean wrapAround) {
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
