/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewars;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class SpaceWars {

    JFrame frame;

    public SpaceWars() {
        initUI();
    }

    private void initUI() {
        frame = new JFrame("Fight against the aliens");
        frame.getContentPane().add(new Board());

        frame.setResizable(false);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(SpaceWars::new);
    }
}
