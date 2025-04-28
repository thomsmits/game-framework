/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.save_the_frog;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.character.Character;
import de.smits_net.games.framework.image.AnimatedImage;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * The main character in the game: a little green frog.
 */
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
                new AnimatedImage(20, true,
                        "/de/smits_net/games/examples/save_the_frog/frog_u1.png"),
                new AnimatedImage(20, true,
                        "/de/smits_net/games/examples/save_the_frog/frog_l1.png",
                        "/de/smits_net/games/examples/save_the_frog/frog_l2.png"),
                new AnimatedImage(20,true,
                        "/de/smits_net/games/examples/save_the_frog/frog_r1.png",
                        "/de/smits_net/games/examples/save_the_frog/frog_r2.png"),
                new AnimatedImage(20, true,
                        "/de/smits_net/games/examples/save_the_frog/frog_u1.png",
                        "/de/smits_net/games/examples/save_the_frog/frog_u2.png"),
                new AnimatedImage(20, true,
                        "/de/smits_net/games/examples/save_the_frog/frog_d1.png",
                        "/de/smits_net/games/examples/save_the_frog/frog_d2.png"),
                new AnimatedImage(1, true, "/de/smits_net/games/examples/save_the_frog/frog_l1.png"),
                new AnimatedImage(1, true, "/de/smits_net/games/examples/save_the_frog/frog_r1.png"),
                new AnimatedImage(1, true, "/de/smits_net/games/examples/save_the_frog/frog_u1.png"),
                new AnimatedImage(1, true, "/de/smits_net/games/examples/save_the_frog/frog_d1.png"));

        bounds = new Rectangle(0, 0, board.getWidth(), FROG_Y + animatedImage.getDimension().height);
    }

    /**
     * Let the frog die.
     */
    public void die() {
        setInvisibleAfterFrames(300);

        AnimatedImage deadFrog = new AnimatedImage(200, false,
                "/de/smits_net/games/examples/save_the_frog/dead_1.png",
                "/de/smits_net/games/examples/save_the_frog/dead_2.png",
                "/de/smits_net/games/examples/save_the_frog/dead_3.png",
                "/de/smits_net/games/examples/save_the_frog/dead_4.png");

        setAllMovementAnimations(deadFrog);
        setAllNoMovementAnimations(deadFrog);
        setActive(false);
    }

    boolean keyDown = false;

    /**
     * Intercept key pressing.
     *
     * @param e the event.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        if (!isActive()) {
            // don't move dead frogs
            return;
        }

        if (keyDown) {
            // key needs to be released
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
