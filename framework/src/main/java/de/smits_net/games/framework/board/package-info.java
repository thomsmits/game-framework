/**
 * Main window and game board. Every game written with this framework
 * consists of a main window and an area inside this window where the
 * game graphics is shown. This package contains the two classes required
 * to realize the board and display the game main window.
 * <p>
 * To write your own game you have to subclass
 * {@link de.smits_net.games.framework.board.MainWindow}
 * and {@link de.smits_net.games.framework.board.Board}. Your instance
 * of MainWindow should contain the main method and it creates
 * the instance of the Board you are using.
 * <p>
 * An example how to start the game is found in the documentation of the
 * class {@link de.smits_net.games.framework.board.MainWindow}.
 */
package de.smits_net.games.framework.board;
