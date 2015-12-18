/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.images;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 *
 *
 * @author Thomas Smits
 */
public class Background {

    private double scroll;
    private double speed;

    protected BufferedImage background;

    public Background(double speed, int width, int height) {
        background = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.speed = speed;
    }

    public void move() {
        scroll += speed;

        if (-scroll > getWidth()) {
            scroll = 0;
        }

    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(background, (int)scroll, 0, observer);
        g.drawImage(background, background.getWidth() + (int)scroll, 0, observer);
    }

    public int getWidth() {
        return background.getWidth();
    }

    public int getHeight() {
        return background.getHeight();
    }
}
