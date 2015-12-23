/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewar;

import de.smits_net.games.framework.image.StripedImage;

/**
 * Sprite for an explosion.
 *
 * @author Thomas Smits
 */
public class Explosion extends StripedImage {

    /**
     * Create a new sprite.
     */
    public Explosion() {
        super("assets/spacewar/explosion_1.png", 43);
    }
}