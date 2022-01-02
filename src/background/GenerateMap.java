package background;

import main.GamePanel;

public class GenerateMap {
    GamePanel g;

    public GenerateMap(GamePanel gp) {
        g = gp;
    }

    public void generate() {
        double[][] arr = PerlinNoise3D.getNoiseArray(g.maxScreenColumn * g.tileSize, g.maxScreenRow * g.tileSize);
        for (var el : arr) {
            for (var value : el) {
                System.out.print(String.format("%.3f", value) + " ");
            }
            System.out.println();
        }
    }
}
