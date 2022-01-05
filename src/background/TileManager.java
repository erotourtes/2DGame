package background;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
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
        setCollisionInObjects();
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

    private void setCollisionInObjects() {
        tiles[3].setCollision(true);
        tiles[4].setCollision(true);
        tiles[5].setCollision(true);
    }

    public void generateNewMap() {
        this.mapArr = PerlinNoise3D.getNoiseArray(gp.getMaxWorldCol(), gp.getMaxWorldRow(), Math.random());
    }

    public void draw(Graphics2D g) {
        int tileSize = gp.tileSize;

        int relateXBeginning = gp.getPlayer().getWorldX() - gp.getPlayer().screenX;
        int relateXEnd = gp.getPlayer().getWorldX() + gp.getPlayer().screenX;

        int relateYBeginning = gp.getPlayer().getWorldY() - gp.getPlayer().screenY;
        int relateYEnd = gp.getPlayer().getWorldY() + gp.getPlayer().screenY;

        for (int y = relateYBeginning / tileSize - 1; y < relateYEnd / tileSize + 2; y++) {
            for (int x = relateXBeginning / tileSize - 1; x < relateXEnd / tileSize + 1; x++) {
                if (ifHasTilesForPosition(x, y)) {
                    var tileNumber = mapArr[y][x];
                    g.drawImage(tiles[tileNumber].image, getRelativeX(x, tileSize), getRelativeY(y, tileSize), tileSize, tileSize, null);
                } else
                    g.drawImage(tiles[5].image, getRelativeX(x, tileSize), getRelativeY(y, tileSize), tileSize, tileSize, null);
            }
        }
    }

    private boolean ifHasTilesForPosition(int x, int y) {
        return y < mapArr.length && x < mapArr[0].length &&
                y >= 0 && x >= 0;
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

    public int[][] getMapArr() {
        return mapArr;
    }

    public Tile[] getTile() {
        return tiles;
    }
}
