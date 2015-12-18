/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewars;

import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.sprites.Sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends BoardBase {

    private static final int INITIAL_POS_CRAFT_X = 40;
    private static final int INITIAL_POS_CRAFT_Y = 60;
    private static final int B_WIDTH = 1200;
    private static final int B_HEIGHT = 800;
    private static final int DELAY = 10;
    private StarField starField1 = new StarField(-0.5, B_WIDTH, B_HEIGHT, Color.WHITE, 0.001);
    private StarField starField2 = new StarField(-0.5, B_WIDTH, B_HEIGHT, Color.GRAY, 0.001);
    private StarField starField3 = new StarField(-0.3, B_WIDTH, B_HEIGHT, Color.DARK_GRAY, 0.001);

    /** Space craft */
    private Craft craft;

    /** Enemies */
    private List<Alien> aliens = new ArrayList<>();

    /**
     * Initialize the game.
     */
    public Board() {
        super(DELAY, B_WIDTH, B_HEIGHT, Color.BLACK);
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
            int x = B_WIDTH + rnd.nextInt(B_WIDTH * 5);
            int y = rnd.nextInt(B_HEIGHT - 40);
            Alien alien = new Alien(this, x,  y);
            addMouseListener(alien);
            aliens.add(alien);
        }

    }


    @Override
    protected synchronized void drawBackground(Graphics g) {
       starField1.draw(g, null);
       starField1.move();
       starField2.draw(g, null);
       starField2.move();
       starField3.draw(g, null);
       starField3.move();

    }

    protected void drawObjects(Graphics g) {
        aliens.forEach(a -> a.draw(g, this));
        craft.draw(g, this);
        craft.getMissiles().forEach(m -> m.draw(g, this));
        writeText(g, 5, 15, "Enemies left: " + aliens.size());
    }

    @Override
    protected synchronized void drawGameOver(Graphics g) {
        centerText(g, "Game Over");
    }

    @Override
    public boolean updateGame() {
        updateCraft();
        updateMissiles();
        updateAliens();
        checkCollisions();

        return !aliens.isEmpty() && craft.isVisible();
    }

    public void drawGame(Graphics g) {
        drawObjects(g);
    }

    private void updateCraft() {
        craft.move();
    }

    private synchronized void updateMissiles() {
        List<Missile> ms = craft.getMissiles();
        ms.forEach(Sprite::move);
        ms.removeIf(m -> !m.isVisible());
    }

    private synchronized void updateAliens() {
        aliens.forEach(Sprite::move);
        aliens.removeIf(a -> !a.isVisible());
    }

    public synchronized void checkCollisions() {

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
