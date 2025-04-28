package de.smits_net.games.examples.spacewar;

import de.smits_net.games.framework.image.ImagePack;
import de.smits_net.games.framework.sprite.Sprite;

import java.awt.*;
import java.net.URL;

public class Resources {

    public static final ImagePack IMAGE_ALIEN =
            ImagePack.loadStripedImage(
                    r("spike_fist_move_strip5.png"), 5);

    public static final ImagePack IMAGE_MISSILE =
            new ImagePack(r("rocket.png"));

    public static final ImagePack IMAGE_EXPLOSION =
            ImagePack.loadStripedImage(
                    r("explosion_1.png"), 43);

    public static final ImagePack IMAGE_SPACECRAFT_SOLO =
            new ImagePack(r("craft_1.png"));

    public static final ImagePack IMAGE_SPACECRAFT_FORWARD =
            new ImagePack(r(
                    "craft_1.png",
                    "craft_2.png",
                    "craft_3.png",
                    "craft_4.png"));

    public static final ImagePack IMAGE_SPACECRAFT_BACKWARD =
            new ImagePack(r(
                    "craft_1.png",
                    "craft_5.png"));

    public static final Polygon POLYGON_SPACECRAFT =
            Sprite.loadPolygon(r("craft.poly"));

    private static URL r(String name) {
        return Resources.class.getResource(name);
    }

    private static URL[] r(String... name) {
        URL[] urls = new URL[name.length];
        for (int i = 0; i < name.length; i++) {
            urls[i] = Resources.class.getResource(name[i]);
        }
        return urls;
    }
}
