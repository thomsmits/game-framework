/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewars;

import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.sprites.Sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The game board.
 *
 * @author Thomas Smits
 */
public class Board extends BoardBase {

    /** Start x position of the craft */
    private static final int INITIAL_POS_CRAFT_X = 40;

    /** Start y position of the craft */
    private static final int INITIAL_POS_CRAFT_Y = 60;

    /** Width of the board */
    private static final int BOARD_WIDTH = 1200;

    /** Height of the board */
    private static final int BOARD_HEIGHT = 800;

    /** Delay between frames in milli seconds */
    private static final int DELAY = 10;

    /** The background */
    private StarField starField1 = new StarField(-0.5, BOARD_WIDTH, BOARD_HEIGHT, Color.WHITE, 0.001);

    /** The background */
    private StarField starField2 = new StarField(-0.5, BOARD_WIDTH, BOARD_HEIGHT, Color.GRAY, 0.001);

    /** The background */
    private StarField starField3 = new StarField(-0.3, BOARD_WIDTH, BOARD_HEIGHT, Color.DARK_GRAY, 0.001);

    /** Space craft */
    private Craft craft;

    /** Enemies */
    private List<Alien> aliens = new ArrayList<>();

    /**
     * Initialize the game.
     */
    public Board() {
        super(DELAY, BOARD_WIDTH, BOARD_HEIGHT, Color.BLACK);
        init();
    }

    /**
     * Initialize the game.
     */
    private void init() {
        craft = new Craft(this, INITIAL_POS_CRAFT_X, INITIAL_POS_CRAFT_Y);
        addKeyListener(craft);
        addMouseListener(craft);
        Random rnd = new Random();

        for (int i = 0; i < 30; i++) {
            int x = BOARD_WIDTH + rnd.nextInt(BOARD_WIDTH * 5);
            int y = rnd.nextInt(BOARD_HEIGHT - 40);
            Alien alien = new Alien(this, x,  y);
            addMouseListener(alien);
            aliens.add(alien);
        }
    }

    /**
     * @see Board#drawBackground(Graphics)
     */
    @Override
    protected synchronized void drawBackground(Graphics g) {
       starField1.draw(g, null);
       starField1.move();
       starField2.draw(g, null);
       starField2.move();
       starField3.draw(g, null);
       starField3.move();
    }

    /**
     * Draw the game objects.
     *
     * @param g graphics context
     */
    protected void drawObjects(Graphics g) {
        aliens.forEach(a -> a.draw(g, this));
        craft.draw(g, this);
        craft.getMissiles().forEach(m -> m.draw(g, this));
        writeText(g, 5, 15, "Enemies left: " + aliens.size());
    }

    /**
     * @see Board#drawGameOver(Graphics)
     */
    @Override
    protected synchronized void drawGameOver(Graphics g) {
        centerText(g, "Game Over");
    }

    /**
     * @see Board#updateGame()
     */
    @Override
    public boolean updateGame() {
        updateCraft();
        updateMissiles();
        updateAliens();
        handleCollisions();

        return !aliens.isEmpty() && craft.isVisible();
    }

    /**
     * @see Board#drawGame(Graphics)
     */
    @Override
    public void drawGame(Graphics g) {
        drawObjects(g);
    }

    /**
     * Update the craft.
     */
    private void updateCraft() {
        craft.move();
    }

    /**
     * Update the missiles.
     */
    private synchronized void updateMissiles() {
        List<Missile> ms = craft.getMissiles();
        ms.forEach(Sprite::move);
        ms.removeIf(m -> !m.isVisible());
    }

    /**
     * Update the aliens.
     */
    private synchronized void updateAliens() {
        aliens.forEach(Sprite::move);
        aliens.removeIf(a -> !a.isVisible());
    }

    /**
     * Check for collisions.
     */
    public synchronized void handleCollisions() {

        for (Alien alien : aliens) {
            if (craft.intersects(alien) && alien.isActive()) {
                craft.explode();
                alien.explode();
            }
        }

        if (!craft.isVisible()) {
            stopGame();
        }

        List<Missile> ms = craft.getMissiles();

        for (Missile m : ms) {
            for (Alien alien : aliens) {
                if (m.intersects(alien) && alien.isActive()) {
                    m.setVisible(false);
                    alien.explode();
                }
            }
        }
    }
}
