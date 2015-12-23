/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.frogger;

import de.smits_net.games.framework.board.MainWindow;

import java.awt.EventQueue;

/**
 * Main window of the Frogger game.
 */
public class FroggerMain extends MainWindow {

    /**
     * Create a new window.
     */
    public FroggerMain() {
        super("Reach the other side", new FroggerBoard());
    }

    /**
     * Main method.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(FroggerMain::new);
    }
}
