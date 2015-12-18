/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprites;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.util.List;

import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.images.ImageBase;
import de.smits_net.games.framework.images.ImageStack;

/**
 * Class representing a sprite, i.e. an moveable object in the game.
 *
 * @author Thomas Smits
 */
public class Sprite implements KeyListener, MouseListener {

    /**
     * How to handle sprites that reach the boundaries of the borard.
     */
    public static enum BoundaryPolicy {
        /** Stop at the boundary */
        STOP,

        /** Set sprite invisible */
        INVISIBLE,

        /** Jump back to the opposite boundary */
        JUMP_BACK,

        /** Do nothing. */
        NONE;
    }

    /** position */
    protected Point position;

    /** dimensions */
    protected Dimension dimension;

    /** indicates whether the sprite is visible */
    protected boolean visible;

    /** the image that is displayed */
    protected ImageBase image;

    /** lower bounds of the sprite movement */
    protected Point lowerBounds;

    /** upper bounds of the sprite movement */
    protected Point upperBounds;

    /** what happens when the sprite hits the boundaries */
    protected BoundaryPolicy policy;

    /** velocity in x direction */
    protected int deltaX;

    /** velocity in y direction */
    protected int deltaY;

    /** the game board */
    protected BoardBase board;

    /** Sprite takes part in collision detection */
    private boolean active = true;

    /**
     * Creates a new sprite.
     *
     * @param board the board the sprite is displayed on
     * @param position position of the upper left corner of the sprite
     * @param policy the policy when the sprite reaches the boundaries
     *      of the board
     * @param image the images
     */
    public Sprite(BoardBase board, Point position, BoundaryPolicy policy, ImageStack image) {
        this.board = board;
        this.position = position;
        this.visible = true;
        this.image = image;
        dimension = new Dimension(
                image.getWidth(),
                image.getHeight());
        this.lowerBounds = new Point(0, 0);
        this.upperBounds = new Point(board.getWidth(),
                board.getHeight());
        this.policy = policy;
    }

    public Sprite(BoardBase board, int x, int y, ImageStack image) {
        this(board, new Point(x, y), BoundaryPolicy.STOP, image);
    }

    public Sprite(BoardBase board, int x, int y, BoundaryPolicy policy, ImageStack image) {
        this(board, new Point(x, y), policy, image);
    }

    public void move() {
        move(deltaX, deltaY);
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public void replaceImage(String imagePath) {
        this.image = new ImageStack(imagePath);
    }

    public void stop() {
        deltaX = 0;
        deltaY = 0;
    }

    public void draw(Graphics g, ImageObserver observer) {

        if (isVisible()) {
            image.draw(g, position, observer);
        }
    }

    public boolean intersects(Sprite other) {
        Rectangle r1 = this.getBounds();
        Rectangle r2 = other.getBounds();
        return r1.intersects(r2);
    }

    public boolean intersects(Rectangle other) {
        Rectangle r1 = this.getBounds();
        return r1.intersects(other);
    }

    public <T extends Sprite> boolean intersects(List<T> others) {
        for (T other : others) {
            if (this.intersects(other)) {
                return true;
            }
        }

        return false;
    }


    public Point getPosition() {
        return position;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isActive() {
        return active;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public void move(int dx, int dy) {

        position.translate(dx, dy);

        if (policy == BoundaryPolicy.STOP) {
            if (position.x < lowerBounds.x) {
                position.x = lowerBounds.x;
            }

            if (position.y < lowerBounds.y) {
                position.y = lowerBounds.y;
            }

            if (position.x > upperBounds.x - dimension.width) {
                position.x = upperBounds.x - dimension.width;
            }

            if (position.y > upperBounds.y - dimension.height) {
                position.y = upperBounds.y - dimension.height;
            }
        }
        else if (policy == BoundaryPolicy.JUMP_BACK) {
            if (position.x < lowerBounds.x) {
                position.x = upperBounds.x;
            }

            if (position.y < lowerBounds.y) {
                position.y = upperBounds.y;
            }

            if (position.x > upperBounds.x) {
                position.x = upperBounds.x;
            }

            if (position.y > upperBounds.y) {
                position.y = upperBounds.y;
            }
        }
        else if (policy == BoundaryPolicy.INVISIBLE) {
            if (position.x < lowerBounds.x) {
                visible = false;
            }

            if (position.y < lowerBounds.y) {
                visible = false;
            }

            if (position.x > upperBounds.x) {
                visible = false;
            }

            if (position.y > upperBounds.y) {
                visible = false;
            }

        }
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(position, dimension);
    }

    protected void mouseClicked() {
        // empty
    }

    protected void mousePressed() {
        // empty
    }

    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
        // Can be overwritten by keyboard-aware sprites
    }

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        // Can be overwritten by keyboard-aware sprites
    }

    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        // Can be overwritten by keyboard-aware sprites
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        if (intersects(new Rectangle(e.getX(), e.getY(), 1, 1))) {
            mouseClicked();
        }
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        if (intersects(new Rectangle(e.getX(), e.getY(), 1, 1))) {
            mousePressed();
        }
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        // Can be overwritten by mouse-aware sprites
    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
        // Can be overwritten by mouse-aware sprites
    }

    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
        // Can be overwritten by mouse-aware sprites
    }
}
