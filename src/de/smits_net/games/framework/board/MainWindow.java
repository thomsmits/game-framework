/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.board;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Main Window of the game. The concrete games will subclass it to produce
 * the ain window.
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
