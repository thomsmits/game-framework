/* (c) 2015 Thomas Smits */
package de.smits_net.games.framework.sprite;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;

import static de.smits_net.games.framework.sprite.Direction.EAST;
import static de.smits_net.games.framework.sprite.Direction.NONE;
import static de.smits_net.games.framework.sprite.Direction.NORTH;
import static de.smits_net.games.framework.sprite.Direction.NORTHEAST;
import static de.smits_net.games.framework.sprite.Direction.NORTHWEST;
import static de.smits_net.games.framework.sprite.Direction.SOUTH;
import static de.smits_net.games.framework.sprite.Direction.SOUTHEAST;
import static de.smits_net.games.framework.sprite.Direction.SOUTHWEST;
import static de.smits_net.games.framework.sprite.Direction.WEST;

/**
 * A sprite with different animations depending on the direction
 * of movement.
 *
 * @author Thomas Smits
 */
public class DirectionAnimatedSprite extends AnimatedSprite {

    /** Images for the different directions */
    protected Map<Direction, AnimatedImage> movementAnimations = new HashMap<>();

    /** Images for the different directions */
    protected Map<Direction, AnimatedImage> noMovementAnimations = new HashMap<>();

    /** Direction the sprite is pointing at */
    protected Direction currentDirection = NONE;

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param startPoint position of sprite
     * @param policy policy used when sprite reaches
     * @param noDirection animation for no direction
     * @param north animation for movement north
     * @param northeast animation for movement to the northeast
     * @param east animation for movement to the east
     * @param southeast animation for movement to the southeast
     * @param south animation for movement south
     * @param southwest animation for movement to the southwest
     * @param west animation for movement to the west
     * @param northwest animation for movement to the northwest
     * @param noMovementNorth animation for non-moving sprite, facing to the north
     * @param noMovementNortheast animation for non-moving sprite, facing to the northeast
     * @param noMovementEast animation for non-moving sprite, facing to the east
     * @param noMovementSoutheast animation for non-moving sprite, facing to the southeast
     * @param noMovementSouth animation for non-moving sprite, facing to the south
     * @param noMovementWest animation for non-moving sprite, facing to the west
     * @param noMovementSouthwest animation for non-moving sprite, facing to the southwest
     * @param noMovementNorthwest animation for non-moving sprite, facing to the northwest
     */
    public DirectionAnimatedSprite(Board board, Point startPoint, BoundaryPolicy policy,
                                   AnimatedImage noDirection,
                                   AnimatedImage north,
                                   AnimatedImage northeast,
                                   AnimatedImage east,
                                   AnimatedImage southeast,
                                   AnimatedImage south,
                                   AnimatedImage southwest,
                                   AnimatedImage west,
                                   AnimatedImage northwest,
                                   AnimatedImage noMovementNorth,
                                   AnimatedImage noMovementNortheast,
                                   AnimatedImage noMovementEast,
                                   AnimatedImage noMovementSoutheast,
                                   AnimatedImage noMovementSouth,
                                   AnimatedImage noMovementWest,
                                   AnimatedImage noMovementSouthwest,
                                   AnimatedImage noMovementNorthwest) {

        super(board, startPoint, policy, north);

        setMovementAnimation(NONE, noDirection);
        setMovementAnimation(EAST, east);
        setMovementAnimation(NORTH, north);
        setMovementAnimation(SOUTH, south);
        setMovementAnimation(WEST, west);
        setMovementAnimation(NORTHWEST, northwest);
        setMovementAnimation(NORTHEAST, northeast);
        setMovementAnimation(SOUTHWEST, southwest);
        setMovementAnimation(SOUTHEAST, southeast);

        setNoMovementAnimation(NONE, noDirection);
        setNoMovementAnimation(EAST, noMovementEast);
        setNoMovementAnimation(NORTH, noMovementNorth);
        setNoMovementAnimation(SOUTH, noMovementSouth);
        setNoMovementAnimation(WEST, noMovementWest);
        setNoMovementAnimation(NORTHWEST, noMovementNorthwest);
        setNoMovementAnimation(NORTHEAST, noMovementNortheast);
        setNoMovementAnimation(SOUTHWEST, noMovementSouthwest);
        setNoMovementAnimation(SOUTHEAST, noMovementSoutheast);
    }

    /**
     * Create a new sprite.
     *
     * @param board our board
     * @param startPoint position of sprite
     * @param policy policy used when sprite reaches
     * @param noDirection animation for no direction
     * @param north animation for movement north
     * @param east animation for movement to the east
     * @param south animation for movement south
     * @param west animation for movement to the west
     * @param noMovementNorth animation for non-moving sprite, facing to the north
     * @param noMovementEast animation for non-moving sprite, facing to the east
     * @param noMovementSouth animation for non-moving sprite, facing to the south
     * @param noMovementWest animation for non-moving sprite, facing to the west
     */
    public DirectionAnimatedSprite(Board board, Point startPoint, BoundaryPolicy policy,
                                   AnimatedImage noDirection,
                                   AnimatedImage north,
                                   AnimatedImage east,
                                   AnimatedImage south,
                                   AnimatedImage west,
                                   AnimatedImage noMovementNorth,
                                   AnimatedImage noMovementEast,
                                   AnimatedImage noMovementSouth,
                                   AnimatedImage noMovementWest) {

        this(board, startPoint, policy, noDirection, north, north, east, south, south, south, west, north,
                noMovementNorth, noMovementNorth, noMovementEast, noMovementSouth, noMovementSouth, noMovementWest, noMovementSouth, noMovementNorth);
    }

    /**
     * Create a new sprite. The no movement animations are derived from the movement
     * animations using only the first image in the pack.
     *
     * @param board our board
     * @param startPoint position of sprite
     * @param policy policy used when sprite reaches
     * @param noDirection animation for no direction
     * @param north animation for movement north
     * @param east animation for movement to the east
     * @param south animation for movement south
     * @param west animation for movement to the west
     */
    public DirectionAnimatedSprite(Board board, Point startPoint, BoundaryPolicy policy,
                                   AnimatedImage noDirection,
                                   AnimatedImage north,
                                   AnimatedImage east,
                                   AnimatedImage south,
                                   AnimatedImage west) {

        this(board, startPoint, policy,
                noDirection,
                north,
                north,
                east,
                south,
                south,
                south,
                west,
                north,
                north.getSubAnimation(0, 1),
                north.getSubAnimation(0, 1),
                east.getSubAnimation(0, 1),
                south.getSubAnimation(0, 1),
                south.getSubAnimation(0, 1),
                west.getSubAnimation(0, 1),
                south.getSubAnimation(0, 1),
                north.getSubAnimation(0, 1)
        );
    }

    /**
     * Sets the animation for the given direction in case of movement.
     *
     * @param direction the direction
     * @param animation the animation. If set to {@code null} it will be ignored
     */
    public final void setMovementAnimation(Direction direction, AnimatedImage animation) {

        if (animation == null) {
            return;
        }

        movementAnimations.put(direction, animation);
    }

    /**
     * Sets the animation for the given direction in case of no movement.
     *
     * @param direction the direction
     * @param animation the animation
     */
    public final void setNoMovementAnimation(Direction direction, AnimatedImage animation) {
        noMovementAnimations.put(direction, animation);
    }

    /**
     * Replaces the animation for all directions with the same one.
     *
     * @param animation the animation to be set
     */
    public void setAllMovementAnimations(AnimatedImage animation) {

        for (Direction d : Direction.values()) {
            movementAnimations.put(d, animation);
        }
    }

    /**
     * Replaces the animation for all directions with the same one.
     *
     * @param animation the animation to be set
     */
    public void setAllNoMovementAnimations(AnimatedImage animation) {

        for (Direction d : Direction.values()) {
            noMovementAnimations.put(d, animation);
        }
    }

    /**
     * @see Sprite#draw(Graphics, ImageObserver)
     */
    @Override
    public void draw(Graphics g, ImageObserver observer) {

        if (invisibleAfterFrames > 0) {
            invisibleAfterFrames--;
        }
        else if (invisibleAfterFrames == 0) {
            setVisible(false);
        }

        if (!isVisible()) {
            return;
        }

        Map<Direction, AnimatedImage> mapToUse;

        Direction direction = velocity.direction();

        if (direction == NONE) {
            mapToUse = noMovementAnimations;
        }
        else {
            mapToUse = movementAnimations;
            currentDirection = direction;
        }

        animatedImage = mapToUse.get(currentDirection);

        super.draw(g, observer);
    }
}
