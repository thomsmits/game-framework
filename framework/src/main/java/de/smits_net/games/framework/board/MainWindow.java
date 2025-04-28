/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.board;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Main Window of the game. The games will subclass it to produce
 * the main window. They pass their specialized {@link Board} and
 * the title of the main window using the constructor to this class.
 * <p>
 * Additionally, the subclasses of this class contain the main method
 * and they start the game thread. A typical example of a subclass of
 * this class may look like this (assuming that the specialized board
 * is called {@code MyBoard}.
 *
 * <pre>
 * import java.awt.EventQueue;
 * import de.smits_net.games.framework.board.MainWindow;
 *
 * public class GameMain extends MainWindow {
 *
 *     public GameMain() {
 *         // pass data to parent constructor and create out board
 *         super("Title of a cool game", new MyBoard());
 *     }
 *
 *     public static void main(String[] args) {
 *         // start the game thread
 *         EventQueue.invokeLater(GameMain::new);
 *     }
 * }
 * </pre>
 *
 * @author Thomas Smits
 */
public class MainWindow {

    /** The window */
    protected JFrame frame;

    /**
     * Create a new object.
     *
     * @param title Title of the window
     * @param board Board to be displayed
     */
    public MainWindow(String title, Board board) {
        frame = new JFrame(title);
        frame.getContentPane().add(board);

        frame.setResizable(false);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
