/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.save_the_frog;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.BackgroundImage;
import de.smits_net.games.framework.image.ImageBase;
import de.smits_net.games.framework.sprite.SpriteCollection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Board of the save the frog game.
 */
public class SaveTheFrogBoard extends Board {

    /** Width of the board. */
    private static final int BOARD_WIDTH = 320;

    /** Height of the board. */
    private static final int BOARD_HEIGHT = 480;

    /** Delay between frames in milli seconds. */
    private static final int DELAY = 20;

    /** Background image. */
    private BackgroundImage backgroundImage;

    /** The frog. */
    private Frog frog;

    /** The cars. */
    private List<SpriteCollection<Car>> lanes = new ArrayList<>();

    /** The cars. */
    private static final String[] CARS = {
            "car_4", "car_2", "car_1", "car_3", "lorry"};

    /** x positions of the cars. */
    private static final int[][] START_POS = {
            {10, 200, 360, 500, 600},
            {10, 200, 360, 500, 600},
            {110, 300, 460, 600},
            {110, 400, 700},
            {10, 400, 600}
    };

    /** y positions of the cars. */
    private static final int[] START_Y = {385, 356, 327, 298, 269};

    /** Speed of the cars in the different lanes. */
    private static final double[] SPEED = {-1.0, 1.0, -1.5, 1.0, -1.5};

    /**
     * Create a new board.
     */
    public SaveTheFrogBoard() {
        super(DELAY, BOARD_WIDTH, BOARD_HEIGHT, Color.BLACK);
        init();
    }

    /**
     * Initialize the board.
     */
    private void init() {
        backgroundImage = new BackgroundImage(0,
                ImageBase.loadImage(ClassLoader.getSystemResource("save_the_frog/background.png")));
        frog = new Frog(this, new Point(10, 10));
        addKeyListener(frog);

        for (int i = 0; i < START_POS.length; i++) {
            int[] pos = START_POS[i];
            double speed = SPEED[i];
            int y = START_Y[i];

            SpriteCollection<Car> collection = new SpriteCollection<>();

            String carImage = CARS[i];

            for (int x : pos) {
                Car car = new Car(this, new Point(x, y), carImage, speed);
                collection.add(car);
            }

            lanes.add(collection);
        }
    }

    @Override
    public boolean updateGame() {
        frog.move();
        lanes.forEach(SpriteCollection::move);

        // check collisions of the frog
        for (SpriteCollection<Car> lane : lanes) {
            if (lane.intersects(frog)) {
                frog.die();
            }
        }

        return frog.isVisible();
    }

    @Override
    public void drawGame(Graphics g) {
        frog.draw(g, this);
        lanes.forEach(l -> l.draw(g, this));
    }

    @Override
    protected void drawGameOver(Graphics g) {
        centerText(g, "Game over");
    }

    @Override
    protected void drawBackground(Graphics g) {
        backgroundImage.draw(g, this);
    }
}
