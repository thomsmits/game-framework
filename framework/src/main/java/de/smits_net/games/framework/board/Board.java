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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.awt.event.KeyEvent.KEY_PRESSED;
import static java.awt.event.KeyEvent.KEY_RELEASED;
import static java.awt.event.KeyEvent.KEY_TYPED;
import static java.awt.event.MouseEvent.MOUSE_CLICKED;
import static java.awt.event.MouseEvent.MOUSE_ENTERED;
import static java.awt.event.MouseEvent.MOUSE_EXITED;
import static java.awt.event.MouseEvent.MOUSE_PRESSED;
import static java.awt.event.MouseEvent.MOUSE_RELEASED;

/**
 * Base class for the game board. Games typically subclass this class
 * and overwrite its methods to control the behavior of the game. The
 * instances of the subclasses are embedded into a window which is
 * provided by the class {@link MainWindow}.
 * <p>
 * This class creates the game thread, consumes the mouse and keyboard
 * events and contains the game loop. The game loop periodically calls
 * the {@link Board#updateGame()}, {@link Board#drawBackground(Graphics)}
 * and {@link Board#drawGame(Graphics)} methods.
 * <p>
 * A minimal game has to overwrite at least two methods:
 * <ul>
 *     <li>{@link Board#updateGame()} to periodically update the state of
 *     all game objects.</li>
 *     <li>{@link Board#drawGame(Graphics)} to draw the game objects.</li>
 * </ul>
 * <p>
 * When the game is over, the framework calls the
 * {@link Board#drawGameOver(Graphics)} to display a game over message.
 * You have to overwrite this method to control this message.
 * <p>
 * By default, the game background is monochrome and in the color provided
 * in the constructor. If you want to change this, overwrite the
 * {@link Board#drawBackground(Graphics)} method.
 * <p>
 * To ensure the desired performance of the game, the game loop measures the
 * frames per second and skips the drawing if necessary to avoid a drop in
 * the game performance. Nevertheless, the game update method is called.
 * <p>
 * If your game should use mouse or keyboard for interaction, you have to
 * register a mouse or keyboard listener using the
 * {@link Board#addMouseListener(MouseListener)} or the
 * {@link Board#addKeyListener(KeyListener)} methods.
 *
 * @author Thomas Smits
 */
public abstract class Board extends JPanel
        implements Runnable, KeyListener, MouseListener {

    /** Number of updates without sleep before a yield is triggered. */
    private static final int NO_DELAYS_PER_YIELD = 16;

    /** Maximum number of frames skipped in the output. */
    private static final int MAX_FRAME_SKIPS = 5;

    /* Time between two debug updates. */
    private static final long DEBUG_FREQUENCY
            = Constants.NANOSECONDS_PER_SECOND / 4;

    /** Indicator that the game is still running. */
    private volatile boolean gameRunning = true;

    /** Dimension of the board. */
    private final Dimension dimension;

    /** Time for each iteration (frame) in nano seconds. */
    private final long delay;

    /** The thread. */
    private Thread thread;

    /** Canvas the game is drawn on. */
    private Image image;

    /** Color of the background of the board. */
    private Color backgroundColor;

    /** Frame per second counter (only used if debugging is on). */
    private long fps;

    /** Timestamp of the last update of the debug line. */
    private long lastDebugUpdate;

    /** The captured mouse events. */
    private List<MouseEvent> mouseEvents = new CopyOnWriteArrayList<>();

    /** The captured key events for typed keys. */
    private List<KeyEvent> keyEvents = new CopyOnWriteArrayList<>();

    /** All game elements that want to get key events. */
    private List<KeyListener> keyListener = new CopyOnWriteArrayList<>();

    /** All game elements that want to get mouse events. */
    private List<MouseListener> mouseListener = new CopyOnWriteArrayList<>();

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
        super.addKeyListener(this);
        super.addMouseListener(this);

        setFocusable(true);
        setBackground(color);

        setPreferredSize(dimension);
    }

    /**
     * @see javax.swing.JComponent#getWidth()
     */
    @Override
    public final int getWidth() {
        return dimension.width;
    }

    /**
     * @see javax.swing.JComponent#getHeight()
     */
    @Override
    public final int getHeight() {
        return dimension.height;
    }

    /**
     * Returns the dimension of the board.
     *
     * @return the dimension
     */
    public final Dimension getDimension() {
        return (Dimension) dimension.clone();
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
     *
     * @param g Graphics context.
     */
    public abstract void drawGame(Graphics g);

    @Override
    public final synchronized void addKeyListener(KeyListener l) {
        keyListener.add(l);
    }

    @Override
    public final synchronized void addMouseListener(MouseListener l) {
        mouseListener.add(l);
    }

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
            g.drawString(String.format("FPS: %d", fps),
                    0, dimension.height - 5);
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
    protected final void centerText(Graphics g, String msg) {
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
    protected final void writeText(Graphics g, int x, int y, String msg) {
        g.setColor(Color.WHITE);
        g.drawString(msg, x, y);
    }

    /**
     * Draw game over message.
     *
     * @param g the graphics context
     */
    protected void drawGameOver(Graphics g) {
        // do nothing
    }

    /**
     * Draw the background. The default implementation clears the
     * background with the current background color.
     *
     * @param g the graphics context
     */
    protected void drawBackground(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, dimension.width, dimension.height);
    }

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
    public final void run() {

        // Updates that were not fast enough and therefore caused a
        // missed sleep
        int numberOfDelays = 0;

        // Time we slipped over the expected FPS
        long excess = 0L;

        // correction of sleep time
        long sleepCorrection = 0L;

        // The approach used here is taken from the book
        // Killer Game Programming in Java by Andrew Davison
        // O’Reilly Media, 2005
        while (gameRunning) {

            // time before game actions
            long beforeTime = System.nanoTime();

            // The listeners will be called by the AWT thread with the
            // consequence that events occur in parallel to the game
            // loop. This requires some synchronization because otherwise
            // strange ConcurrentModificationExceptions or other thread
            // issues will occur. To avoid this, I gobble all the events
            // with this class and then dispatch them synchronously to
            // the other game elements here.
            KeyEvent keyEvent;

            while ((keyEvent = fetchEvent(keyEvents)) != null) {
                for (KeyListener l : keyListener)  {
                    switch (keyEvent.getID()) {
                        case KEY_PRESSED:
                            l.keyPressed(keyEvent);
                            break;
                        case KEY_RELEASED:
                            l.keyReleased(keyEvent);
                            break;
                        case KEY_TYPED:
                            l.keyTyped(keyEvent);
                            break;
                        default:
                            // do nothing
                    }
                }
            }

            MouseEvent mouseEvent;

            while ((mouseEvent = fetchEvent(mouseEvents)) != null) {
                for (MouseListener l : mouseListener)  {
                    switch (mouseEvent.getID()) {
                        case MOUSE_PRESSED:
                            l.mousePressed(mouseEvent);
                            break;
                        case MOUSE_RELEASED:
                            l.mouseReleased(mouseEvent);
                            break;
                        case MOUSE_CLICKED:
                            l.mouseClicked(mouseEvent);
                            break;
                        case MOUSE_ENTERED:
                            l.mouseEntered(mouseEvent);
                            break;
                        case MOUSE_EXITED:
                            l.mouseExited(mouseEvent);
                            break;
                        default:
                            // do nothing
                    }
                }
            }

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
                    Thread.sleep(sleepDuration
                            / Constants.NANOSECONDS_PER_MILLISECOND);
                }
                catch (InterruptedException e) {
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

                    fps = Constants.NANOSECONDS_PER_SECOND
                            / (System.nanoTime() - beforeTime);

                    lastDebugUpdate = System.nanoTime();
                }
            }
        }

        // call one last time into subclass to allow rendering of
        // game over information
        triggerRendering();
        paintScreen();
    }

    /**
     * Fetch the pending events.
     *
     * @param <T> the type of the event
     * @param eventList the list of events
     *
     * @return the event or {@code null} if no events are present
     */
    private <T> T fetchEvent(List<T> eventList) {

        if (eventList.size() > 0) {
            T e = eventList.get(0);
            eventList.remove(0);
            return e;
        }

        return null;
    }

    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
        keyEvents.add(e);
    }

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        keyEvents.add(e);
    }

    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        keyEvents.add(e);
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        mouseEvents.add(e);
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        mouseEvents.add(e);
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        mouseEvents.add(e);
    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
        mouseEvents.add(e);
    }

    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
        mouseEvents.add(e);
    }
}
