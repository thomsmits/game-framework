/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.dodger;

import de.smits_net.games.framework.board.MainWindow;

import java.awt.EventQueue;

/**
 * Main class of the game.
 *
 * @author Thomas Smits
 */
public class DodgerMain extends MainWindow {

    /**
     * Create a new object.
     */
    public DodgerMain() {
        super("Dodger: Avoid the evil suns", new DodgerBoard());
    }

    /**
     * Main method.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(DodgerMain::new);
    }
}
