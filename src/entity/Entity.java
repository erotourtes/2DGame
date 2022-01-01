package entity;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int speed;

    protected Direction direction;
    protected State state;

    protected final short playerWidth = 19;
    protected final short playerHeight = 23;

    protected Map<Direction, BufferedImage[]> images;
    protected Map<Direction, Map<State, BufferedImage>> animatedImages;
    protected short numberOfImages = 5;

    public Entity() {
        images = new HashMap<>();
        animatedImages = new HashMap<>();
        for (var direction : Direction.values()) {
            images.put(direction, new BufferedImage[numberOfImages]);

            for (var state : State.values()) {
                animatedImages.put(direction, new HashMap<>());
            }
        }
    }
}
