/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewars;

import de.smits_net.games.framework.images.StripedImage;

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
        super("assets/spacewars/explosion_1.png", 43);
    }
}