package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler key;

    public Player(GamePanel gp, KeyHandler key) {
        this.gp = gp;
        this.key = key;
        setDefault();
        getImages();
    }

    public void setDefault() {
        x = 100;
        y = 100;
        speed = 4;
        direction = Direction.DOWN;
    }

    public void getImages() {
        try {
            short numberOfImages = 3;
            up = new BufferedImage[numberOfImages];
            down = new BufferedImage[numberOfImages];
            left = new BufferedImage[numberOfImages];
            right = new BufferedImage[numberOfImages];
            String path = "/resources/Player/Soldier/";

            for (int i = 0; i < up.length; i++) {
                up[i] = ImageIO.read(getClass().getResourceAsStream(path + "up/" + i + ".png"));
                down[i] = ImageIO.read(getClass().getResourceAsStream(path + "down/" + i + ".png"));
                left[i] = ImageIO.read(getClass().getResourceAsStream(path + "left/" + i + ".png"));
                right[i] = ImageIO.read(getClass().getResourceAsStream(path + "right/" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (!key.isPressedAnyOf()) {
            if (++spriteUpdatesHandler >= 15) {
                spriteNumber = (short)((spriteNumber + 1) % 3);
                spriteUpdatesHandler = 0;
            }
        }

        if (key.isUp()) {
            direction = Direction.UP;
            y -= speed;
        }
        if (key.isDown()) {
            direction = Direction.DOWN;
            y += speed;
        }
        if (key.isLeft()) {
            direction = direction.LEFT;
            x -= speed;
        }
        if (key.isRight()) {
            direction = Direction.RIGHT;
            x += speed;
        }
    }

    public void draw(Graphics2D g2D) {
        BufferedImage image = null;
        switch (direction) {
            case UP -> image = up[spriteNumber];
            case DOWN -> image = down[spriteNumber];
            case LEFT -> image = left[spriteNumber];
            case RIGHT -> image = right[spriteNumber];
        }
        g2D.drawImage(image, x, y, playerWidth * GamePanel.scale, playerHeight * GamePanel.scale, null);
    }
}
