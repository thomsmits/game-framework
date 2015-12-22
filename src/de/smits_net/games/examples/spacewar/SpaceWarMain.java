/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewar;

import de.smits_net.games.framework.board.MainWindow;

import java.awt.EventQueue;

/**
 * Main class of the game.
 *
 * @author Thomas Smits
 */
public class SpaceWarMain extends MainWindow {

    /**
     * Create a new object.
     */
    public SpaceWarMain() {
        super("Fight against the aliens", new SpaceWarBoard());
    }

    /**
     * Main method.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(SpaceWarMain::new);
    }
}
