/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.save_the_frog;

import de.smits_net.games.framework.board.MainWindow;

import java.awt.EventQueue;

/**
 * Main window of the Save the Frog game.
 */
public class SaveTheFrogMain extends MainWindow {

    /**
     * Create a new window.
     */
    public SaveTheFrogMain() {
        super("Reach the other side", new SaveTheFrogBoard());
    }

    /**
     * Main method.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(SaveTheFrogMain::new);
    }
}
