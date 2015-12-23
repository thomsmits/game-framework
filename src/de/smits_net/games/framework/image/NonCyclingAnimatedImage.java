/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.image;

/**
 * Animation that does not cycle but ends with the last picture.
 */
public class NonCyclingAnimatedImage extends AnimatedImage {

    /**
     * Creates a new animatedImage object.
     *
     * @param time the time one image is shown in milliseconds
     * @param images the images comprising the animatedImage
     */
    public NonCyclingAnimatedImage(int time, ImagePack images) {
        super(time, images);
        images.setWrapAround(false);
    }

    /**
     * Convenience constructor that creates the image pack for the
     * caller to make usage easier.
     *
     * @param time the time one image is shown in milliseconds
     * @param path path to the image
     * @param fileNames names of the files to be loaded
     */
    public NonCyclingAnimatedImage(int time, String path, String... fileNames) {
        super(time, path, fileNames);
        images.setWrapAround(false);
    }
}
