package entity;

import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int speed;

    protected final short playerWidth = 19;
    protected final short playerHeight = 23;

    protected BufferedImage[] up, down, left, right;
    protected Direction direction;

    protected short spriteNumber = 0;
    protected short spriteUpdatesHandler = 0;
}
