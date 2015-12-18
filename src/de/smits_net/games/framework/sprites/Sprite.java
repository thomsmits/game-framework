/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprites;

import de.smits_net.games.framework.Constants;
import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.images.ImageBase;
import de.smits_net.games.framework.images.ImageStack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Class representing a sprite, i.e. an moveable object in the game.
 *
 * @author Thomas Smits
 */
public class Sprite implements KeyListener, MouseListener {

    /** Border of the sprite (for collision detection) */
    private Polygon border = new Polygon();

    /**
     * How to handle sprites that reach the boundaries of the borard.
     */
    public enum BoundaryPolicy {
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

        // assume rectangular border
        border.addPoint(0, 0);
        border.addPoint(image.getWidth(), 0);
        border.addPoint(image.getWidth(), image.getHeight());
        border.addPoint(0, image.getHeight());
    }

    /**
     * Create a new sprite.
     *
     * @param board the board
     * @param x x position of the sprite
     * @param y y position of the sprite
     * @param image the sprite's image
     */
    public Sprite(BoardBase board, int x, int y, ImageStack image) {
        this(board, new Point(x, y), BoundaryPolicy.STOP, image);
    }

    /**
     * Create a new sprite.
     *
     * @param board the board
     * @param x x position of the sprite
     * @param y y position of the sprite
     * @param policy the policy when the sprite reaches the boundaries
     *      of the board
     * @param image the sprite's image
     */
    public Sprite(BoardBase board, int x, int y, BoundaryPolicy policy, ImageStack image) {
        this(board, new Point(x, y), policy, image);
    }

    /**
     * Sets the border of the sprite (for collision detection).
     *
     * @param border the border to set
     */
    public void setBorder(Polygon border) {
        this.border = border;
    }

    /**
     * Move the sprite according to the defined speed.
     */
    public void move() {
        move(deltaX, deltaY);
    }

    /**
     * Set the speed in x direction.
     *
     * @param deltaX speed in x direction
     */
    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }

    /**
     * Set the speed in y direction.
     *
     * @param deltaY speed in y direction
     */
    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    /**
     * Get the speed in y direction.
     *
     * @return speed in y direction.
     */
    public int getDeltaY() {
        return deltaY;
    }

    /**
     * Get the speed in x direction.
     *
     * @return speed in x direction.
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Stop the movement of the sprite.
     */
    public void stop() {
        deltaX = 0;
        deltaY = 0;
    }

    /**
     * Calculate the border in relation to the current position. This is required
     * to detect intersections between sprites.
     *
     * @return the border's absolute position
     */
    protected Polygon absoluteBorder() {
        Polygon p = new Polygon(border.xpoints, border.ypoints, border.npoints);
        p.translate(position.x, position.y);
        return p;
    }

    /**
     * Draw the sprite.
     *
     * @param g graphics context
     * @param observer image observer
     */
    public void draw(Graphics g, ImageObserver observer) {

        if (!isVisible()) {
            return;
        }

        image.draw(g, position, observer);

        if (Constants.DEBUG_SPRITE_OUTLINE) {
            g.setColor(Color.RED);
            g.drawPolygon(absoluteBorder());
        }
    }

    /**
     * Checks whether this sprite intersects with the given polygon.
     *
     * @param p2 the polygon.
     * @return true if there is an intersection,
     */
    public boolean intersects(Polygon p2) {

        Polygon p1 = absoluteBorder();

        for (int i = 0; i < p1.npoints; i++) {
            if (p2.contains(new Point(p1.xpoints[i], p1.ypoints[i]))) {
                return true;
            }
        }

        for (int i = 0; i < p2.npoints;i++) {
            if (p1.contains(new Point(p2.xpoints[i], p2.ypoints[i]))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether this sprite intersects with the given sprite.
     *
     * @param other the other sprite.
     * @return true if there is an intersection,
     */
    public boolean intersects(Sprite other) {
        return intersects(other.absoluteBorder());
    }

    /**
     * Checks whether this sprite intersects with the given rectangle.
     *
     * @param other the rectangle.
     * @return true if there is an intersection,
     */
    public boolean intersects(Rectangle other) {
        Polygon p = new Polygon();
        p.addPoint(other.x, other.y);
        p.addPoint(other.x + other.width, other.y);
        p.addPoint(other.x + other.width, other.y + other.height);
        p.addPoint(other.x, other.y + other.height);
        return intersects(p);
    }

    /**
     * Checks the given other objects regarding an intersection with this object.
     *
     * @param others the others
     * @param <T> Type of the objects
     * @return true if there is an intersection,
     */
    public <T extends Sprite> boolean intersects(List<T> others) {
        for (T other : others) {
            if (this.intersects(other)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get the current position.
     *
     * @return the current position.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Is the sprite visible.
     *
     * @return true if visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility.
     *
     * @param visible visibility state
     */
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    /**
     * Is the sprite active, i.e. takes part in the collision detection.
     *
     * @return true if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets activity state of the sprite.
     *
     * @param active state to be set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Get the x position.
     *
     * @return x position
     */
    public int getX() {
        return position.x;
    }

    /**
     * Get the y position.
     *
     * @return y position
     */
    public int getY() {
        return position.y;
    }

    /**
     * Moves the sprite.
     *
     * @param dx delta in x direction
     * @param dy delta in y direction
     */
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

    /**
     * Load a polygon definition from a file.
     *
     * @param path directory
     * @param fileName name of the file
     * @return the loaded polygon
     */
    public static Polygon loadPolygonFromFile(String path, String fileName) {

        Polygon result = new Polygon();
        File directory = new File(path);
        File filePath = new File(directory, fileName);

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {

                // remove all whitespaces
                line = line.replaceAll(" ", "");

                // ignore empty lines
                if (line.length() == 0) {
                    continue;
                }

                // ignore comments
                if (line.startsWith("#")) {
                    continue;
                }

                // split values
                String[] elements = line.split(",");

                int x = Integer.parseInt(elements[0]);
                int y = Integer.parseInt(elements[1]);

                result.addPoint(x, y);
            }
        }
        catch (IOException | NumberFormatException e) {
            // do nothing. In case of error, we return an empty
            // polygon
            System.err.println("Error loading polygon: " + e);
        }

        return result;
    }

    /**
     * Called when the mouse was clicked inside the sprite.
     */
    protected void mouseClicked() {
        // empty
    }

    /**
     * Called when the mouse was pressed inside the sprite.
     */
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
