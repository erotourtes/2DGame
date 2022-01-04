package background;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TileManager {
    private GamePanel gp;
    private Tile[] tiles;
    private int[][] mapArr;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        getTileImages();
    }

    public void getTileImages() {
        try {
            var listOfFileNames = Files.walk(Paths.get("./src/resources/Tiles/Old version/"))
                    .filter(Files::isRegularFile)
                    .map((path -> {
                        var str = path.toString();
                        var preElement = str.lastIndexOf("\\");
                        return str.substring(preElement);
                    }))
                    .collect(Collectors.toList());

            String path = "/resources/Tiles/Old version";
            for (int i = 0; i < listOfFileNames.size(); i++) {
                tiles[i] = new Tile();
                tiles[i].image = ImageIO.read(getClass().getResourceAsStream(path + listOfFileNames.get(i)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateNewMap() {
        this.mapArr = PerlinNoise3D.getNoiseArray(gp.getMaxWorldCol(), gp.getMaxWorldRow(), Math.random());
    }
    public void draw(Graphics2D g) {
        int tileSize = gp.tileSize;

        for (int y = 0; y < gp.getMaxWorldRow(); y++) {
            for (int x = 0; x < gp.getMaxWorldCol(); x++) {
                int worldX = x * tileSize;
                int worldY = y * tileSize;

                if (isInVisibleZone(worldX, worldY)) {
                    var tileNumber =  mapArr[y][x];
                    g.drawImage(tiles[tileNumber].image, getRelativeX(x, tileSize), getRelativeY(y, tileSize), tileSize, tileSize, null);
                }
            }
        }
    }

    private boolean isInVisibleZone(int worldX, int worldY) {
        return worldX + gp.tileSize > gp.getPlayer().getWorldX() - gp.getPlayer().screenX &&
                worldX - gp.tileSize < gp.getPlayer().getWorldX() + gp.getPlayer().screenX &&
                worldY + gp.tileSize > gp.getPlayer().getWorldY() - gp.getPlayer().screenY &&
                worldY - gp.tileSize < gp.getPlayer().getWorldY() + gp.getPlayer().screenY;
    }

    private int getRelativeX(int x, int tileSize) {
        int worldX = x * tileSize; //absolute tile coordinates
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().screenX;//where to draw relatively
        return screenX;
    }

    private int getRelativeY(int y, int tileSize) {
        int worldY = y * tileSize;
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().screenY;
        return screenY;
    }
}
