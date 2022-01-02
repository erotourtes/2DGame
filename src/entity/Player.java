package entity;

import main.GamePanel;
import main.KeyHandler;
import swappingImages.SwapImg;
import swappingImages.SwappingPlayerMoving;
import swappingImages.SwappingPlayerWaiting;

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
        swapImg();
    }

    public void setDefault() {
        x = 100;
        y = 100;
        speed = 4;
        direction = Direction.DOWN;
    }

    private void getImages() {
        try {
            String path = "/resources/Player/Soldier.png";
            var image = ImageIO.read(getClass().getResourceAsStream(path));
            var directions = Direction.values();
            for (int y = 0; y < directions.length; y++)
                for (int x = 0; x < numberOfImages; x++)
                    images.get(directions[y])[x] = image.getSubimage(x * playerWidth, y * playerHeight, playerWidth, playerHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void swapImg() {
        for (var e : Direction.values()) {
            new SwappingPlayerWaiting(220, images.get(e), animatedImages.get(e));
            new SwappingPlayerMoving(150, images.get(e), animatedImages.get(e));
        }
    }

    public void update() {
        if (key.isMoving())
            state = State.MOVING;
        else
            state = State.WAITING;

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
        BufferedImage image = animatedImages.get(direction).get(state);
        g2D.drawImage(image, x, y, playerWidth * GamePanel.scale, playerHeight * GamePanel.scale, null);
    }
}
