package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SupperObject {
    protected BufferedImage image;

    protected String name;

    protected boolean collision = false;

    protected Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    protected int solidAreaDefaultX = 0;

    protected int getSolidAreaDefaultY = 0;

    protected int worldX, worldY;
    protected void setImage(String name) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resources/Object/" + name + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g, GamePanel gp) {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().screenX;
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().screenY;

        g.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getGetSolidAreaDefaultY() {
        return getSolidAreaDefaultY;
    }

    public boolean isCollision() {
        return collision;
    }

    public String getName() {
        return name;
    }
}
