/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprite;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 * Enhanced collection class for sprites.
 *
 * @param <T> type of the sprite managed by this class.
 */
public class SpriteCollection<T extends Sprite> extends ArrayList<T> {

    /**
     * Draw all sprites in the collection.
     *
     * @param g the graphics context
     * @param observer the image observer
     */
    public void draw(Graphics g, ImageObserver observer) {
        forEach(e -> e.draw(g, observer));
    }

    /**
     * Remove all invisible elements form the collection.
     */
    public void removeIfInvisble() {
        removeIf(e -> !e.isVisible());
    }

    /**
     * Moves all sprites in the collection.
     */
    public void move() {
        forEach(Sprite::move);
    }

    /**
     * Checks whether any of the sprites in this collection intersects
     * with the given sprite.
     *
     * @param sp sprite to check
     * @return {@code true} if intersects, otherwise {@code false}
     */
    public boolean intersects(Sprite sp) {

        for (T t : this) {
            if (t.isActive() && sp.isActive() && t.intersects(sp)) {
                return true;
            }
        }

        return false;
    }
}
