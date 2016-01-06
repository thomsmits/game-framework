/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.dodger;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;
import de.smits_net.games.framework.sprite.DirectionAnimatedSprite;

import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 * The Rabbit on the board.
 */
public class Rabbit extends DirectionAnimatedSprite {

    /** Speed of the rabbit. */
    private static final int RABBIT_SPEED = 5;

    /**
     * Create a new craft at the given position.
     *
     * @param board the board we are displayed on
     * @param startPoint start position
     */
    public Rabbit(Board board, Point startPoint) {
        super(board, startPoint, BoundaryPolicy.STOP,
                new AnimatedImage(100, "assets/dodger",
                        "bunny1_ready.png"),
                new AnimatedImage(100, "assets/dodger",
                        "bunny1_ready.png"),
                new AnimatedImage(100, "assets/dodger",
                        "bunny1_walk1_right.png",
                        "bunny1_walk2_right.png"),
                new AnimatedImage(100, "assets/dodger",
                        "bunny1_ready.png"),
                new AnimatedImage(100, "assets/dodger",
                        "bunny1_walk1_left.png",
                        "bunny1_walk2_left.png"),
                new AnimatedImage(100, "assets/dodger",
                        "bunny1_ready.png"),
                new AnimatedImage(100, "assets/dodger",
                        "bunny1_ready.png"),
                new AnimatedImage(100, "assets/dodger",
                        "bunny1_ready.png"),
                new AnimatedImage(100, "assets/dodger",
                        "bunny1_ready.png")
        );
    }

    /**
     * Rabbit was hit by an enemy.
     */
    public void hit() {
        AnimatedImage brokenRabbit = new AnimatedImage(
                100, "assets/dodger", "bunny1_hurt.png");
        setAllMovementAnimations(brokenRabbit);
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
        if (key == KeyEvent.VK_LEFT)  { velocity.x = -RABBIT_SPEED; }
        if (key == KeyEvent.VK_RIGHT) { velocity.x = RABBIT_SPEED; }
    }

    /**
     * Intercept key releasing.
     *
     * @param e the event.
     */
    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)  { velocity.x = 0; }
        if (key == KeyEvent.VK_RIGHT) { velocity.x = 0; }
    }
}
