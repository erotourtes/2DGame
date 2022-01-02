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
    GamePanel gp;
    Tile[] tiles;

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

    public void draw(Graphics2D g) {
        int size = gp.tileSize;
        for (int y = 0; y < gp.maxScreenRow; y++) {
            for (int x = 0; x < gp.maxScreenColumn; x++) {
                g.drawImage(tiles[0].image, x * size, y * size, size, size, null);
            }
        }
    }
}
