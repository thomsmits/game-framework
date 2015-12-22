/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.dodger;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.images.ImageStack;
import de.smits_net.games.framework.sprites.DirectionAnimatedSprite;

import java.awt.event.KeyEvent;

public class Rabbit extends DirectionAnimatedSprite {

    private static final int CRAFT_SPEED = 5;

    /**
     * Create a new craft at the given position.
     *
     * @param board tinthe board we are displayed on
     * @param x x position
     * @param y y position
     */
    public Rabbit(Board board, int x, int y) {
        super(board, x, y, BoundaryPolicy.STOP,
                new ImageStack("assets/dodger", "bunny1_ready.png"),
                new ImageStack("assets/dodger", "bunny1_walk1_left.png",
                        "bunny1_walk2_left.png"),
                new ImageStack("assets/dodger", "bunny1_walk1_right.png",
                        "bunny1_walk2_right.png"),
                new ImageStack("assets/dodger", "bunny1_ready.png"),
                new ImageStack("assets/dodger", "bunny1_ready.png"),
                100);
    }

    public void hit() {
        ImageStack brokenRabbit = new ImageStack("assets/dodger", "bunny1_hurt.png");
        this.noMovement = brokenRabbit;
        this.left = brokenRabbit;
        this.right = brokenRabbit;
        this.up = brokenRabbit;
        this.down = brokenRabbit;
        setInvisibleAfterFrames(30);
        setActive(false);
    }
    /**
     * Intercept key pressing.
     *
     * @param e the event.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        //if (key == KeyEvent.VK_SPACE) { fire(); }
        if (key == KeyEvent.VK_LEFT)  { deltaX = -CRAFT_SPEED; }
        if (key == KeyEvent.VK_RIGHT) { deltaX = CRAFT_SPEED; }
    }

    /**
     * Intercept key releasing.
     *
     * @param e the event.
     */
    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)  { deltaX = 0; }
        if (key == KeyEvent.VK_RIGHT) { deltaX = 0; }
    }
}
