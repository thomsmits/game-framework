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
public class Craft extends DirectionAnimatedSprite {

    /** Speed of the space craft */
    private static final int CRAFT_SPEED = 2;

    /** Speed of the animatedImage */
    private static final int ANIMATION_SPEED = 10;

    /** Missiles fired */
    private SpriteCollection<Missile> missiles = new SpriteCollection<>();

    /**
     * Create a new craft at the given position.
     *
     * @param board the board we are displayed on
     * @param startPoint start position
     */
    public Craft(Board board, Point startPoint) {
        super(board, startPoint, BoundaryPolicy.STOP,
                new AnimatedImage(ANIMATION_SPEED, "assets/spacewar", "craft_1.png"),
                new AnimatedImage(ANIMATION_SPEED, "assets/spacewar", "craft_1.png"),
                new AnimatedImage(ANIMATION_SPEED, "assets/spacewar", "craft_1.png",
                        "craft_2.png",
                        "craft_3.png",
                        "craft_4.png"),
                new AnimatedImage(ANIMATION_SPEED, "assets/spacewar", "craft_1.png"),
                new AnimatedImage(ANIMATION_SPEED, "assets/spacewar", "craft_1.png",
                        "craft_5.png")
        );

        setBorder(loadPolygonFromFile("assets/spacewar", "craft.poly"));
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
        missiles.add(new Missile(board, new Point((int)position.x + dimension.width, (int)position.y + dimension.height / 2)));
    }

    /**
     * Let the spaceship explode.
     */
    public void explode() {
        AnimatedImage ex = new AnimatedImage(ANIMATION_SPEED, new Explosion());
        setAllMovementAnimations(ex);
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

        if (key == KeyEvent.VK_SPACE) { fire(); }
        if (key == KeyEvent.VK_LEFT)  { velocity.x = -CRAFT_SPEED; }
        if (key == KeyEvent.VK_RIGHT) { velocity.x = CRAFT_SPEED; }
        if (key == KeyEvent.VK_UP)    { velocity.y = -CRAFT_SPEED; }
        if (key == KeyEvent.VK_DOWN)  { velocity.y = CRAFT_SPEED; }
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
        if (key == KeyEvent.VK_UP)    { velocity.y = 0; }
        if (key == KeyEvent.VK_DOWN)  { velocity.y = 0; }
    }
}
