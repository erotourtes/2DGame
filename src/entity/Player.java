package entity;

import main.GamePanel;
import main.KeyHandler;
import swappingImages.SwappingPlayerMoving;
import swappingImages.SwappingPlayerWaiting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler key;

    public final int screenX, screenY;

    public Player(GamePanel gp, KeyHandler key) {
        this.gp = gp;
        this.key = key;

        screenX = gp.screenWidth / 2 - Entity.playerWidth / 2 + 8;
        screenY = gp.screenHeight / 2 - Entity.playerHeight / 2 - 8;

        solidArea = new Rectangle();
        int recValueY = 10;
        int recValueX = 3;
        solidArea.y = recValueY * gp.scale;
        solidArea.x = recValueX * gp.scale;
        solidArea.width = (playerWidth - recValueX * 2) * gp.scale;
        solidArea.height = (recValueY) * gp.scale;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefault();
        getImages();
        swapImg();
    }

    public void setDefault() {
        worldX = gp.getMaxWorldCol() / 2 * gp.tileSize;
        worldY = gp.getMaxWorldRow() / 2 * gp.tileSize;
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
        if (key.isMoving()) {
            state = State.MOVING;
        }
        else {
            state = State.WAITING;
        }

        if (key.isUp()) direction = Direction.UP;
        if (key.isDown()) direction = Direction.DOWN;
        if (key.isLeft()) direction = direction.LEFT;
        if (key.isRight()) direction = Direction.RIGHT;

        checkCollision();

        if (!collision) {
            if(key.isUp()) worldY -= speed;
            if(key.isDown()) worldY += speed;
            if(key.isLeft()) worldX -= speed;
            if(key.isRight()) worldX += speed;
        }

    }

    private void checkCollision() {
        collision = false;
        gp.collisionChecker.checkTile(this);

        var obj = gp.collisionChecker.checkObject(this, true);
        System.out.println(obj);
    }

    public void draw(Graphics2D g2D) {
        BufferedImage image = animatedImages.get(direction).get(state);
        g2D.drawImage(image, screenX, screenY, playerWidth * GamePanel.scale, playerHeight * GamePanel.scale, null);
    }
}
