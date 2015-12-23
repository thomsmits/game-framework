/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewar;

import de.smits_net.games.framework.image.BackgroundImage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Background star field for the space shooter.
 *
 * @author Thomas Smits
 */
public class StarField extends BackgroundImage {

    private Random rnd = new Random();

    public StarField(double speed, int width, int height, Color color, double load) {
        super(speed, width, height);

        Graphics g = background.getGraphics();
        int numberOfStars = (int)((width * height) * load);

        g.setColor(color);

        for (int i = 0; i < numberOfStars; i++) {
            int x = rnd.nextInt(background.getWidth());
            int y = rnd.nextInt(height);

            g.drawRect(x, y, 1, 1);
        }
    }
}
