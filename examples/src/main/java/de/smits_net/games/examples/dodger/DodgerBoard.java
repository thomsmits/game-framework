/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.dodger;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.ImageBase;
import de.smits_net.games.framework.sprite.Sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A very simple game.
 */
public class DodgerBoard extends Board {

    /** Width of the board. */
    private static final int BOARD_WIDTH = 1024;

    /** Height of the board. */
    private static final int BOARD_HEIGHT = 800;

    /** Delay between frames in milli seconds. */
    private static final int DELAY = 10;

    /** Initial x position of the figure on the board. */
    private static final int INITIAL_POS_RABBIT_X = BOARD_WIDTH / 2;

    /** Initial x position of the figure on the board. */
    private static final int INITIAL_POS_RABBIT_Y = BOARD_HEIGHT - 200;

    /** Maximum number of enemies on the board. */
    private static final int MAX_NUMBER_OF_ENEMIES = 10;

    private int score;

    /**
     * Create a new board.
     */
    public DodgerBoard() {
        super(DELAY, BOARD_WIDTH, BOARD_HEIGHT, Color.BLACK);
        init();
    }

    private Rabbit rabbit;

    private List<Sun> wingmen = new ArrayList<>();

    private Random rnd = new Random();

    private BufferedImage background;

    /**
     * Initialize the game.
     */
    private void init() {
        rabbit = new Rabbit(this,
                new Point(INITIAL_POS_RABBIT_X, INITIAL_POS_RABBIT_Y));
        addKeyListener(rabbit);
        background = ImageBase.loadImage(ClassLoader.getSystemResource("dodger/background.png"));
    }


    @Override
    public boolean updateGame() {
        rabbit.move();
        wingmen.forEach(Sprite::move);

        if (rabbit.intersects(wingmen)) {
            rabbit.hit();
        }

        if (wingmen.size() < MAX_NUMBER_OF_ENEMIES) {

            Point start = new Point(rnd.nextInt(BOARD_WIDTH - 70),
                    -1 * rnd.nextInt(BOARD_HEIGHT));

            Sun newWingman = new Sun(this, start, 1 + rnd.nextInt(2));

            if (!newWingman.intersects(wingmen)) {
                wingmen.add(newWingman);
            }
        }

        for (Sun w : wingmen) {
            if (w.getPosition().y > BOARD_HEIGHT) {
                w.setVisible(false);
                score++;
            }
        }

        wingmen.removeIf(w -> !w.isVisible());

        return rabbit.isVisible();
    }

    @Override
    public void drawGame(Graphics g) {
        rabbit.draw(g, this);
        wingmen.forEach(wm -> wm.draw(g, this));
        writeText(g, 10, 20, String.format("Score: %d", score));
    }

    @Override
    protected void drawGameOver(Graphics g) {
        writeText(g, 10, 20, String.format("Score: %d", score));
        centerText(g, "Game Over");
    }

    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(background, 0, 0, this);
    }
}
