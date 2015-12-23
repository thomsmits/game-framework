/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.image;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Images created from one large image that is organized in tiles.
 *
 * @author Thomas Smits
 */
public class TiledImage extends ImagePack {

    /** Dimension of the image */
    private Dimension dimension;

    /**
     * Create a new image.
     *
     * @param image the image with the tiles
     * @param boxWidth width of one tile
     * @param boxHeight height of one tile
     */
    public TiledImage(BufferedImage image, int boxWidth, int boxHeight) {
        super();
        List<BufferedImage> tiles = getTiles(image, boxWidth, boxHeight);
        tiles.forEach(this::addImage);

        this.dimension = new Dimension(boxWidth, boxHeight);
    }

    /**
     * Create a new image.
     *
     * @param path path to the image
     * @param boxWidth width of one tile
     * @param boxHeight height of one tile
     */
    public TiledImage(String path, int boxWidth, int boxHeight) {
        this(ImageBase.loadImage(path), boxWidth, boxHeight);
    }

    /**
     * Extract the tiles.
     *
     * @param img the image
     * @param boxWidth width of onw tile
     * @param boxHeight height of one tile
     * @return the images
     */
    private static List<BufferedImage> getTiles(BufferedImage img, int boxWidth, int boxHeight) {

        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int tilesPerRow = imageWidth / boxWidth;
        int tilesPerColumn = imageHeight / boxHeight;

        List<BufferedImage> result = new ArrayList<>();

        int size = tilesPerColumn * tilesPerRow;
        int boxPerRow = img.getWidth() / boxWidth;

        for (int index = 0; index < size; index++) {
            int row = index / boxPerRow;
            int column = index % boxPerRow;

            int xpos = boxWidth * column;
            int ypos = boxWidth * row;

            result.add(img.getSubimage(xpos, ypos, boxWidth, boxHeight));
        }

        return result;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }
}
