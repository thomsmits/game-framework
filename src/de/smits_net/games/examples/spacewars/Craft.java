/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewars;

import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.images.ImageStack;
import de.smits_net.games.framework.sprites.DirectionAnimatedSprite;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The spacecraft.
 *
 * @author Thomas Smits
 */
public class Craft extends DirectionAnimatedSprite {

    /** Speed of the space craft */
    private static final int CRAFT_SPEED = 2;

    /** Missiles fired */
    private List<Missile> missiles = new CopyOnWriteArrayList<>();

    /**
     * Create a new craft at the given position.
     *
     * @param board the board we are displayed on
     * @param x x position
     * @param y y position
     */
    public Craft(BoardBase board, int x, int y) {
        super(board, x, y, BoundaryPolicy.STOP,
                new ImageStack("assets/spacewars", "craft_1.png"),
                new ImageStack("assets/spacewars", "craft_1.png",
                        "craft_5.png"),
                new ImageStack("assets/spacewars", "craft_1.png",
                        "craft_2.png",
                        "craft_3.png",
                        "craft_4.png"),
                new ImageStack("assets/spacewars", "craft_1.png"),
                new ImageStack("assets/spacewars", "craft_1.png"));

        setBorder(loadPolygonFromFile("assets/spacewars", "craft.poly"));
    }

    /**
     * Return the missiles fired.
     *
     * @return the missiles
     */
    public List<Missile> getMissiles() {
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
        missiles.add(new Missile(board, position.x + dimension.width, position.y + dimension.height / 2));
    }

    /**
     * Let the spaceship explode.
     */
    public void explode() {
        Explosion ex = new Explosion();
        this.noMovement = ex;
        this.left = ex;
        this.right = ex;
        this.up = ex;
        this.down = ex;
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
        if (key == KeyEvent.VK_LEFT)  { deltaX = -CRAFT_SPEED; }
        if (key == KeyEvent.VK_RIGHT) { deltaX = CRAFT_SPEED; }
        if (key == KeyEvent.VK_UP)    { deltaY = -CRAFT_SPEED; }
        if (key == KeyEvent.VK_DOWN)  { deltaY = CRAFT_SPEED; }
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
        if (key == KeyEvent.VK_UP)    { deltaY = 0; }
        if (key == KeyEvent.VK_DOWN)  { deltaY = 0; }
    }
}
