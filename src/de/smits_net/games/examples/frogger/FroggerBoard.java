/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.frogger;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.BackgroundImage;
import de.smits_net.games.framework.image.ImageBase;
import de.smits_net.games.framework.sprite.SpriteCollection;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Board of the frogger game.
 */
public class FroggerBoard extends Board {

    /** Width of the board */
    private static final int BOARD_WIDTH = 320;

    /** Height of the board */
    private static final int BOARD_HEIGHT = 480;

    /** Delay between frames in milli seconds */
    private static final int DELAY = 20;

    private static final int LANE1_Y = 385;
    private static final int LANE2_Y = 356;
    private static final int LANE3_Y = 327;
    private static final int LANE4_Y = 298;
    private static final int LANE5_Y = 269;

    private BackgroundImage backgroundImage;

    private Frog frog;

    private List<SpriteCollection<Car>> lanes = new ArrayList<>();

    private String[] cars = { "car_4", "car_2", "car_1", "car_3", "lorry"};

    private static final int[][] START_POS = {
            { 10, 150, 200, 300 },
            { 10, 150, 200, 300 },
            { 10, 150, 200, 300 },
            { 10, 150, 200, 300 },
            { 30, 180, 230, 380, 500, 550 }
    };

    private static final int[] START_Y = { 385, 356, 327, 298, 269 };

    private static final double[] SPEED = { -1.0, 1.0, -1.5, 1.0, -1.5 };

    public FroggerBoard() {
        super(DELAY, BOARD_WIDTH, BOARD_HEIGHT, Color.BLACK);
        init();
    }

    private void init() {
        backgroundImage = new BackgroundImage(0, ImageBase.loadImage("assets/frogger/background.png"));
        frog = new Frog(this, 10, 10);
        addKeyListener(frog);

        for (int i = 0; i < START_POS.length; i++) {
            int[] pos = START_POS[i];
            double speed = SPEED[i];
            int y = START_Y[i];

            SpriteCollection<Car> collection = new SpriteCollection<>();

            String carImage = cars[i];

            for (int k = 0; k < pos.length; k++) {
                int x = pos[k];

                Car car = new Car(this, x, y, carImage, speed);
                collection.add(car);
            }


            lanes.add(collection);
        }
    }

    @Override
    public boolean updateGame() {
        frog.move();
        lanes.forEach(SpriteCollection::move);
        return true;
    }

    @Override
    public void drawGame(Graphics g) {
        frog.draw(g, this);
        lanes.forEach(l -> l.draw(g, this));
    }

    @Override
    protected void drawGameOver(Graphics g) {

    }

    @Override
    protected void drawBackground(Graphics g) {
        backgroundImage.draw(g, this);
    }
}
