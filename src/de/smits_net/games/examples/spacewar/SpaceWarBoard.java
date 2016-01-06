/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewar;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.sprite.Sprite;
import de.smits_net.games.framework.sprite.SpriteCollection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Random;

/**
 * The game board.
 *
 * @author Thomas Smits
 */
public class SpaceWarBoard extends Board {

    /** Start x position of the craft. */
    private static final int INITIAL_POS_CRAFT_X = 40;

    /** Start y position of the craft. */
    private static final int INITIAL_POS_CRAFT_Y = 60;

    /** Width of the board. */
    private static final int BOARD_WIDTH = 800;

    /** Height of the board. */
    private static final int BOARD_HEIGHT = 400;

    /** Delay between frames in milli seconds. */
    private static final int DELAY = 10;

    /** The background. */
    private StarField starField1 = new StarField(
            -0.5, BOARD_WIDTH, BOARD_HEIGHT, Color.WHITE, 0.001);

    /** The background. */
    private StarField starField2 = new StarField(
            -0.5, BOARD_WIDTH, BOARD_HEIGHT, Color.GRAY, 0.001);

    /** The background. */
    private StarField starField3 = new StarField(
            -0.3, BOARD_WIDTH, BOARD_HEIGHT, Color.DARK_GRAY, 0.001);

    /** Space craft. */
    private Craft craft;

    /** Enemies. */
    private SpriteCollection<Alien> aliens = new SpriteCollection<>();

    /**
     * Initialize the game.
     */
    public SpaceWarBoard() {
        super(DELAY, BOARD_WIDTH, BOARD_HEIGHT, Color.BLACK);
        init();
    }

    /**
     * Initialize the game.
     */
    private void init() {
        craft = new Craft(this, new Point(
                INITIAL_POS_CRAFT_X, INITIAL_POS_CRAFT_Y));
        addKeyListener(craft);
        addMouseListener(craft);
        Random rnd = new Random();

        for (int i = 0; i < 30; i++) {
            int x = BOARD_WIDTH + rnd.nextInt(BOARD_WIDTH * 5);
            int y = rnd.nextInt(BOARD_HEIGHT - 40);
            Alien alien = new Alien(this, new Point(x,  y));
            addMouseListener(alien);
            aliens.add(alien);
        }
    }

    /**
     * @see SpaceWarBoard#drawBackground(Graphics)
     */
    @Override
    protected void drawBackground(Graphics g) {
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
        aliens.draw(g, this);
        craft.draw(g, this);
        craft.getMissiles().draw(g, this);
        writeText(g, 5, 15, "Enemies left: " + aliens.size());
    }

    /**
     * @see SpaceWarBoard#drawGameOver(Graphics)
     */
    @Override
    protected void drawGameOver(Graphics g) {
        centerText(g, "Game Over");
    }

    /**
     * @see SpaceWarBoard#updateGame()
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
     * @see SpaceWarBoard#drawGame(Graphics)
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
    private void updateMissiles() {
        SpriteCollection<Missile> ms = craft.getMissiles();
        ms.forEach(Sprite::move);
        ms.removeIfInvisble();
    }

    /**
     * Update the aliens.
     */
    private void updateAliens() {
        aliens.forEach(Sprite::move);
        aliens.removeIf(a -> !a.isVisible());
    }

    /**
     * Check for collisions.
     */
    public void handleCollisions() {

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
