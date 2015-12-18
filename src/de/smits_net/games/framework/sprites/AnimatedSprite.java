/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprites;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.images.ImageStack;

/**
 * An animated sprite, i.e. a sprite that has several different
 *
 * @author Thomas Smits
 */
public abstract class AnimatedSprite extends Sprite {

    /** the image that is displayed */
    protected ImageStack images;

    protected int time;


    /** Hide the sprite after the given number of frames */
    protected int invisibleAfterFrames = -1;


    public void setInvisibleAfterFrames(int invisibleAfterFrames) {
        this.invisibleAfterFrames = invisibleAfterFrames;
    }


    public AnimatedSprite(BoardBase board, int x, int y, ImageStack images, int time) {
        this(board, x, y, BoundaryPolicy.STOP, images, time);
   }

    public AnimatedSprite(BoardBase board, int x, int y, BoundaryPolicy policy, ImageStack imgs,
            int time) {
        super(board, x, y, policy, imgs);

        this.images = imgs;
        this.time = time;
    }


    public void setImages(ImageStack imgs) {
        setImages(imgs, this.time);
    }

    public void setImages(ImageStack imgs, int time) {

        int offsetX = (images.getWidth() - imgs.getWidth()) / 2;
        int offsetY = (images.getHeight() - imgs.getHeight()) / 2;

        this.images = imgs;
        this.time = time;

        position.translate(offsetX, offsetY);

    }

    protected long lastRun = System.nanoTime();

    @Override
    public void draw(Graphics g, ImageObserver observer) {

        if (!isVisible()) {
            return;
        }

        long timePassed = (System.nanoTime() - lastRun) / 1000000L;

        if (timePassed > time) {
            images.cycle();
            lastRun = System.nanoTime();

            if (invisibleAfterFrames > 0) {
                invisibleAfterFrames--;
            }
            else if (invisibleAfterFrames == 0) {
                setVisible(false);
            }

        }

        images.draw(g, position, observer);
    }
}
