/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.frogger;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.ImagePack;
import de.smits_net.games.framework.sprite.Sprite;

public class Car extends Sprite {

    int startx;

    public Car(Board board, int x, int y, String name, double speed) {
        super(board, x, y, BoundaryPolicy.NONE, new ImagePack("assets/frogger", name));
        startx = x;
        setDeltaX(speed);
    }

    @Override
    public void move() {
        super.move();

        if ((deltaX < 0) && (positionX + image.getDimension().width < 0)) {
            positionX = 2*board.getWidth();
        }
        else if ((deltaX > 0) && (positionX > board.getWidth())) {
            positionX = -2*image.getDimension().width;
        }
    }
}
