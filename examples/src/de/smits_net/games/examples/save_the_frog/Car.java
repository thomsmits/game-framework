/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.save_the_frog;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.ImagePack;
import de.smits_net.games.framework.sprite.Sprite;

import java.awt.Point;

/**
 * Car.
 */
public class Car extends Sprite {

    /** Start x position. */
    int startx;

    /**
     * Create a new car.
     *
     * @param board the board.
     * @param startPoint the start position.
     * @param name the name of the car.
     * @param speed the speed.
     */
    public Car(Board board, Point startPoint, String name, double speed) {
        super(board, startPoint, BoundaryPolicy.NONE,
                new ImagePack("assets/save_the_frog", name));
        startx = startPoint.x;
        velocity.x = speed;
    }

    @Override
    public void move() {
        super.move();

        if ((velocity.x < 0) && (position.x + image.getDimension().width < 0)) {
            position.x = 2 * board.getWidth();
        }
        else if ((velocity.x > 0) && (position.x > board.getWidth())) {
            position.x = -2 * image.getDimension().width;
        }
    }
}
