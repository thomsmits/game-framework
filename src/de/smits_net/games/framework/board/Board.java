/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.board;

import de.smits_net.games.framework.Constants;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Base class for the game board. This class starts the game thread.
 *
 * @author Thomas Smits
 */
public abstract class Board extends JPanel implements Runnable {

    /** Number of updates without sleep before a yield is triggered */
    private static final int NO_DELAYS_PER_YIELD = 16;

    /** Maximum number of frames skipped in the output */
    private static final int MAX_FRAME_SKIPS = 5;

    /* Time between two debug updates */
    private static final long DEBUG_FREQUENCY = Constants.NANOSECONDS_PER_SECOND / 4;

    /** Indicator that the game is still running */
    protected boolean gameRunning = true;

    /** Dimension of the board */
    protected Dimension dimension;

    /** Time for each iteration (frame) in nano seconds */
    protected long delay;

    /** The thread */
    protected Thread thread;

    /** Canvas the game is drawn on */
    private Image image;

    /** Color of the background of the board */
    private Color backgroundColor;

    /** Frame per second counter (only used if debugging is on) */
    private long fps;

    /** Timestamp of the last update of the debug line */
    private long lastDebugUpdate;

    /**
     * Create a new board.
     *
     * @param delay delay between two frames in milliseconds
     * @param width width of the board in pixel
     * @param height height of the board in pixel
     * @param color background color of the board
     */
    public Board(int delay, int width, int height, Color color) {
        this(delay, new Dimension(width, height), color);
    }

    /**
     * Create a new board.
     *
     * @param delay delay between two frames in milliseconds
     * @param dimension dimension of the board
     * @param color background color of the board
     */
    public Board(int delay, Dimension dimension, Color color) {
        this.delay = delay * Constants.NANOSECONDS_PER_MILLISECOND;
        this.backgroundColor = color;
        this.dimension = dimension;

        setFocusable(true);
        setBackground(color);

        setPreferredSize(dimension);
    }

    /**
     * @see javax.swing.JComponent#getWidth()
     */
    @Override
    public int getWidth() {
        return dimension.width;
    }

    /**
     * @see javax.swing.JComponent#getHeight()
     */
    @Override
    public int getHeight() {
        return dimension.height;
    }

    /**
     * Returns the dimension of the board.
     *
     * @return the dimension
     */
    public Dimension getDimension() {
        return dimension;
    }
    /**
     * Stops the game.
     */
    public void stopGame() {
        gameRunning = false;
    }

    /**
     * The game loop for the game logic. This method will be called
     * periodically be the framework to execute the game. All game
     * actions must be triggered from this loop.
     *
     * @return true if the game should continue, otherwise false
     */
    public abstract boolean updateGame();

    /**
     * The graphics output of the game. This method is called
     * periodically by the framework to draw the game graphics.
     */
    public abstract void drawGame(Graphics g);

    /**
     * Prepare the graphics context and then call the render methods.
     */
    private void triggerRendering() {

        // we use double buffering. This means that we do not draw on
        // the screen directly but into an image that is eventually
        // blitted to screen in onw step. Therefore, we need an image
        // as the target of our drawing operations.
        if (image == null) {
            image = createImage(dimension.width, dimension.height);
            if (image == null) {
                return;
            }
        }

        Graphics g = image.getGraphics();

        // clear the background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, dimension.width, dimension.height);

        // Let subclass draw its background
        drawBackground(g);

        // Draw game
        if (gameRunning) {
            drawGame(g);
        }
        else {
            drawGameOver(g);
        }

        if (Constants.DEBUG_SHOW_FPS) {
            g.setColor(Color.RED);
            g.drawString(String.format("FPS: %d", fps), 0, dimension.height - 5);
        }

        g.dispose();
    }

    /**
     * Paints the screen.
     */
    private void paintScreen() {

        try {
            Graphics g = this.getGraphics();
            if ((g != null) && (image != null)) {
                g.drawImage(image, 0, 0, null);
                g.dispose();
            }

            Toolkit.getDefaultToolkit().sync();
        }
        catch (Exception e) {
            System.err.println("Graphics error: " + e);
        }
    }

    /**
     * Draw a text centered on the screen.
     *
     * @param g the graphics context
     * @param msg the message to be shown
     */
    protected void centerText(Graphics g, String msg) {
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (dimension.width - fm.stringWidth(msg)) / 2,
                dimension.height / 2);
    }

    /**
     * Writes a text at the given position.
     *
     * @param g graphics context
     * @param x x position of the text
     * @param y y position of the text
     * @param msg the message to be drawn
     */
    protected void writeText(Graphics g, int x, int y, String msg) {
        g.setColor(Color.WHITE);
        g.drawString(msg, x, y);
    }

    /**
     * Draw game over message.
     *
     * @param g the graphics context
     */
    protected abstract void drawGameOver(Graphics g);

    /**
     * Draw the background.
     *
     * @param g the graphics context
     */
    protected abstract void drawBackground(Graphics g);

    /**
     * @see javax.swing.JComponent#addNotify()
     */
    @Override
    public void addNotify() {
        super.addNotify();

        thread = new Thread(this);
        thread.start();
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {

        // Updates that were not fast enough and therefore caused a
        // missed sleep
        int numberOfDelays = 0;

        // Time we slipped over the expected FPS
        long excess = 0L;

        // The approach used here is taken from the book
        // Killer Game Programming in Java by Andrew Davison
        // O’Reilly Media, 2005
        while (gameRunning) {

            // time before game actions
            long beforeTime = System.nanoTime();

            // correction of sleep time
            long sleepCorrection = 0L;

            // execute the game actions
            gameRunning = updateGame();
            triggerRendering();
            paintScreen();

            // time after game actions
            long afterTime = System.nanoTime();

            // time passed during game actions
            long timeDiff = afterTime - beforeTime;

            // sleep long enough to reach the desired delays between updates
            long sleepDuration = (delay - timeDiff - sleepCorrection);

            if (sleepDuration < 0) {
                sleepCorrection = 0L;
                excess -= sleepDuration;

                if (++numberOfDelays >= NO_DELAYS_PER_YIELD) {
                    Thread.yield();
                    numberOfDelays = 0;
                }
            }
            else {
                try {
                    Thread.sleep(sleepDuration / Constants.NANOSECONDS_PER_MILLISECOND);
                } catch (InterruptedException e) {
                    break;
                }

                // sleep may be inaccurate, therefore calculate sleep's skew
                sleepCorrection = System.nanoTime() - afterTime - sleepDuration;
            }

            int skips = 0;

            // Rendering takes too much time, therefore update the game without
            // rendering the screen. The display will lack FPS but the game
            // logic will proceed with the right speed
            while ((excess > delay) && (skips < MAX_FRAME_SKIPS)) {
                excess -= delay;
                gameRunning = updateGame();
                skips++;
            }

            if (Constants.DEBUG_SHOW_FPS) {
                if (System.nanoTime() - lastDebugUpdate > DEBUG_FREQUENCY) {
                    fps = Constants.NANOSECONDS_PER_SECOND / (System.nanoTime() - beforeTime);
                    lastDebugUpdate = System.nanoTime();
                }
            }
        }

        // call one last time into subclass to allow rendering of
        // game over information
        triggerRendering();
        paintScreen();
    }
}
