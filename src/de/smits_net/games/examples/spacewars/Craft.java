/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewars;

import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.images.ImageStack;
import de.smits_net.games.framework.sprites.DirectionAnimatedSprite;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Craft extends DirectionAnimatedSprite {

    private static final int CRAFT_SPEED = 2;

    /** Missiles fired */
    private List<Missile> missiles = new CopyOnWriteArrayList<>();


    @Override
    public void mouseClicked() {
        fire();
    }

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


    public List<Missile> getMissiles() {
        return missiles;
    }

    public void fire() {
        missiles.add(new Missile(board, position.x + dimension.width, position.y + dimension.height / 2));
    }

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

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) { fire(); }
        if (key == KeyEvent.VK_LEFT)  { deltaX = -CRAFT_SPEED; }
        if (key == KeyEvent.VK_RIGHT) { deltaX = CRAFT_SPEED; }
        if (key == KeyEvent.VK_UP)    { deltaY = -CRAFT_SPEED; }
        if (key == KeyEvent.VK_DOWN)  { deltaY = CRAFT_SPEED; }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)  { deltaX = 0; }
        if (key == KeyEvent.VK_RIGHT) { deltaX = 0; }
        if (key == KeyEvent.VK_UP)    { deltaY = 0; }
        if (key == KeyEvent.VK_DOWN)  { deltaY = 0; }
    }
}
