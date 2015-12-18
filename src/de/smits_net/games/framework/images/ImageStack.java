/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.images;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A stack of images. This class can be used if more than one image has to be
 * transferred.
 *
 * The class has one "current" image, which can be received using the
 * getImage() method. The user can cycle through the images with the
 * cycle() method.
 *
 * @author Thomas Smits
 */
public class ImageStack extends ImageBase implements Iterable<BufferedImage> {

    /** THe images */
    protected List<BufferedImage> imageList = new ArrayList<>();

    /** Image currently displayed, used for cycling through the images */
    protected int currentImage = 0;

    /**
     * Constructor for internal use.
     */
    protected ImageStack() {
        // empty
    }

    /**
     * Creates a new set of images.
     *
     * @param path path to the image
     * @param fileNames names of the files to be loaded
     */
    public ImageStack(String path, String... fileNames) {

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
            currentImage = 0;
        }

        return getImage();
    }

    /**
     * Gets the current image.
     *
     * @return the current image.
     */
    public Image getImage() {
        return imageList.get(currentImage);
    }

    /**
     * Gets the image at the given position.
     *
     * @param index position of the image
     * @return the image at the position.
     */
    public Image getImage(int index) {
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
     * @see de.smits_net.games.framework.images.ImageBase#getWidth()
     */
    @Override
    public int getWidth() {
        return imageList.get(0).getWidth();
    }

    /**
     * @see de.smits_net.games.framework.images.ImageBase#getHeight()
     */
    @Override
    public int getHeight() {
        return imageList.get(0).getHeight();
    }

    /**
     * @see de.smits_net.games.framework.images.ImageBase#draw(java.awt.Graphics, java.awt.Point, java.awt.image.ImageObserver)
     */
    @Override
    public void draw(Graphics g, Point position, ImageObserver observer) {
        g.drawImage(imageList.get(currentImage), position.x, position.y, observer);
    }
}
