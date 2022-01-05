package background;

import java.awt.image.BufferedImage;

public class Tile {
    protected BufferedImage image;
    private boolean collision = false;

    public void setCollision(boolean variable) {
        collision = variable;
    }

    public boolean getCollision() {
        return collision;
    }

}
