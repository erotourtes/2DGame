package main;

import background.GenerateMap;
import background.TileManager;
import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private final int originalTileSize = 16;
    public static final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    public final int maxScreenColumn = 16;
    public final int maxScreenRow = 12;

    private final int screenWidth = tileSize * maxScreenColumn;
    private final int screenHeight = tileSize * maxScreenRow;

    private final short fps = 60;
    private double nextRepaintTime;

    private static Thread gameThread;
    private KeyHandler key = new KeyHandler();

    private Player player;
    private TileManager tileManager;
    private GenerateMap mapGenerator;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.yellow);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);

        player = new Player(this, key);
        tileManager = new TileManager(this);
        mapGenerator = new GenerateMap(this);

        mapGenerator.generate();

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double interval = Math.pow(10, 9) / fps;
        nextRepaintTime = System.nanoTime() + interval;

        while (gameThread != null) {
            update(); //update player position;
            repaint(); //call paintComponent() method;

            updateWithFPS(interval);
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;

        tileManager.draw(g2D);
        player.draw(g2D);

        g2D.dispose();
    }

    private void updateWithFPS(double interval) {
        double timeLeft = nextRepaintTime - System.nanoTime();
        timeLeft = toMilliseconds(timeLeft);

        if(timeLeft < 0)
            timeLeft = 0;

        try {
            Thread.sleep((long)timeLeft);
            nextRepaintTime += interval;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isGameThreadIsNull() {
        return gameThread == null;
    }

    private double toMilliseconds(double time) {
        return time / Math.pow(10, 6);
    }
}
