/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.frogger;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.character.Character;
import de.smits_net.games.framework.image.AnimatedImage;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Frog extends Character {

    private static final int FROG_SPEED = 5;

    private static final int FROG_X = 130;
    private static final int FROG_Y = 415;
    private static final int FROG_JUMP = 29;

    /**
     * Create a new sprite.
     *
     * @param board      our board
     * @param startPoint start position
     */
    public Frog(Board board, Point startPoint) {
        super(board, new Point(FROG_X, FROG_Y), FROG_SPEED, BoundaryPolicy.STOP,
                new AnimatedImage(20, "assets/frogger", "frog_u1"),
                new AnimatedImage(20, "assets/frogger", "frog_l1", "frog_l2"),
                new AnimatedImage(20, "assets/frogger", "frog_r1", "frog_r2"),
                new AnimatedImage(20, "assets/frogger", "frog_u1", "frog_u2"),
                new AnimatedImage(20, "assets/frogger", "frog_d1", "frog_d2"),
                new AnimatedImage(1, "assets/frogger", "frog_l1"),
                new AnimatedImage(1, "assets/frogger", "frog_r1"),
                new AnimatedImage(1, "assets/frogger", "frog_u1"),
                new AnimatedImage(1, "assets/frogger", "frog_d1"));

        bounds = new Rectangle(0, 0, board.getWidth(), FROG_Y + animatedImage.getDimension().height);
    }


    boolean keyDown = false;

    /**
     * Intercept key pressing.
     *
     * @param e the event.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        if (keyDown) {
            return;
        }

        int key = e.getKeyCode();

        Point target = getPosition();

        if (key == KeyEvent.VK_LEFT)  { target.translate(-FROG_JUMP,   0); }
        if (key == KeyEvent.VK_RIGHT) { target.translate( FROG_JUMP,   0); }
        if (key == KeyEvent.VK_UP)    { target.translate(  0, -FROG_JUMP); }
        if (key == KeyEvent.VK_DOWN)  { target.translate(  0,  FROG_JUMP); }

        setTarget(target);

        keyDown = true;
    }

    /**
     * Intercept key releasing.
     *
     * @param e the event.
     */
    @Override
    public void keyReleased(KeyEvent e) {

        keyDown = false;

        int key = e.getKeyCode();
/*
        if (key == KeyEvent.VK_LEFT)  { deltaX = 0; }
        if (key == KeyEvent.VK_RIGHT) { deltaX = 0; }
        if (key == KeyEvent.VK_UP)    { deltaY = 0; }
        if (key == KeyEvent.VK_DOWN)  { deltaY = 0; }
        */
    }
}
