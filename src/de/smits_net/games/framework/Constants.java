/** (c) 2015 Thomas Smits */
package de.smits_net.games.framework;

/**
 * Constants used by the framework.
 */
public class Constants {

    /** Conversion factor from nano to milliseconds */
    public static final long NANOSECONDS_PER_MILLISECOND = 1000000L;

    /** Conversion factor from nano to seconds */
    public static final long NANOSECONDS_PER_SECOND = NANOSECONDS_PER_MILLISECOND * 1000L;

    /** Draws the outline of a sprite */
    public static final boolean DEBUG_SPRITE_OUTLINE = true;

    /** Writes the FPS to the screen */
    public static final boolean DEBUG_SHOW_FPS = true;

    private Constants() {
        // no instances
    }
}
