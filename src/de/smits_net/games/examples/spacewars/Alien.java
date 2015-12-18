/* (c) 2015 Thomas Smits */
package de.smits_net.games.examples.spacewars;

import de.smits_net.games.framework.board.BoardBase;
import de.smits_net.games.framework.images.TiledImage;
import de.smits_net.games.framework.sprites.AnimatedSprite;

public class Alien extends AnimatedSprite {

    private static final int ALIEN_SPEED = -1;

    public Alien(BoardBase board, int x, int y) {
        super(board, x, y, BoundaryPolicy.NONE,
                new TiledImage(
                "assets/spacewars/spike_fist_move_strip5.png", 61, 47), 50);
        setDeltaX(ALIEN_SPEED);
    }

    public void explode() {
        setActive(false);
        setImages(new Explosion(), 20);
        setInvisibleAfterFrames(30);
    }

    @Override
    public void mousePressed() {
        explode();
    }
}
