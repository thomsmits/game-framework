/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewars;

import javax.swing.*;
import java.awt.*;

/**
 * Main class of the game.
 *
 * @author Thomas Smits
 */
public class SpaceWars {

    /** The window */
    JFrame frame;

    /**
     * Create a new object.
     */
    public SpaceWars() {
        initUI();
    }

    /**
     * Initialize the UI.
     */
    private void initUI() {
        frame = new JFrame("Fight against the aliens");
        frame.getContentPane().add(new Board());

        frame.setResizable(false);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Main method.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(SpaceWars::new);
    }
}
