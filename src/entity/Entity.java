package entity;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
    protected int worldX;
    protected int worldY;
    protected int speed;

    protected Direction direction;
    protected State state;

    protected final static short playerWidth = 19;
    protected final static short playerHeight = 23;

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

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }
}
