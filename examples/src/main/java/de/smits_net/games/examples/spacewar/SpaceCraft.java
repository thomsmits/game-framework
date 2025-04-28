/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewar;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;
import de.smits_net.games.framework.sprite.DirectionAnimatedSprite;
import de.smits_net.games.framework.sprite.SpriteCollection;

import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 * The spacecraft.
 *
 * @author Thomas Smits
 */
public class SpaceCraft extends DirectionAnimatedSprite {

    /** Speed of the spacecraft. */
    private static final int CRAFT_SPEED = 2;

    /** Speed of the animatedImage. */
    private static final int ANIMATION_SPEED = 10;

    /** Image for the craft without thrusters.*/
    private static final AnimatedImage CRAFT_NOT_MOVING =
            new AnimatedImage(ANIMATION_SPEED, true,
                    Resources.IMAGE_SPACECRAFT_SOLO);

    /** Image for the craft accelerating forward. */
    private static final AnimatedImage CRAFT_ACCELERATING_FORWARD =
            new AnimatedImage(ANIMATION_SPEED, true,
                    Resources.IMAGE_SPACECRAFT_FORWARD);

    /** Image for the craft accelerating backward. */
    private static final AnimatedImage CRAFT_ACCELERATING_BACKWARD =
            new AnimatedImage(ANIMATION_SPEED, true,
                    Resources.IMAGE_SPACECRAFT_BACKWARD);

    /** Missiles fired. */
    private final SpriteCollection<Missile> missiles = new SpriteCollection<>();

    /**
     * Create a new craft at the given position.
     *
     * @param board the board we are displayed on
     * @param startPoint start position
     */
    public SpaceCraft(Board board, Point startPoint) {
        super(board, startPoint, BoundaryPolicy.STOP,
                CRAFT_NOT_MOVING,
                CRAFT_NOT_MOVING,
                CRAFT_ACCELERATING_FORWARD,
                CRAFT_NOT_MOVING,
                CRAFT_ACCELERATING_BACKWARD
        );

        setBorder(Resources.POLYGON_SPACECRAFT);
    }

    /**
     * Return the missiles fired.
     *
     * @return the missiles
     */
    public SpriteCollection<Missile> getMissiles() {
        return missiles;
    }

    /**
     * Click will fire a missile.
     */
    @Override
    public void mouseClicked() {
        fire();
    }

    /**
     * Fire a missile.
     */
    public void fire() {
        missiles.add(new Missile(board,
                new Point((int) position.x + dimension.width,
                        (int) position.y + dimension.height / 2)));
    }

    /**
     * Let the spaceship explode.
     */
    public void explode() {
        AnimatedImage ex = new AnimatedImage(
                ANIMATION_SPEED, false,
                Resources.IMAGE_EXPLOSION);
        setAllMovementAnimations(ex);
        setInvisibleAfterFrames(30);
        setActive(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Accelerate the spaceship depending on the
        // pressed key
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) { fire(); }
        if (key == KeyEvent.VK_LEFT)  { velocity.x = -CRAFT_SPEED; }
        if (key == KeyEvent.VK_RIGHT) { velocity.x = CRAFT_SPEED; }
        if (key == KeyEvent.VK_UP)    { velocity.y = -CRAFT_SPEED; }
        if (key == KeyEvent.VK_DOWN)  { velocity.y = CRAFT_SPEED; }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // If key is released, stop spaceship motor
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)  { velocity.x = 0; }
        if (key == KeyEvent.VK_RIGHT) { velocity.x = 0; }
        if (key == KeyEvent.VK_UP)    { velocity.y = 0; }
        if (key == KeyEvent.VK_DOWN)  { velocity.y = 0; }
    }
}
