package main;

import background.TileManager;
import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //game settings
    private final int originalTileSize = 19;
    public static final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    public final int maxScreenColumn = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenColumn;
    public final int screenHeight = tileSize * maxScreenRow;

    //world settings
    private int maxWorldCol = 50;
    private int maxWorldRow = 50;
    private int worldWidth = maxWorldCol * tileSize;
    private int worldHeight = maxWorldRow * tileSize;

    private final short fps = 60;
    private double nextRepaintTime;

    private static Thread gameThread;
    private KeyHandler key = new KeyHandler();

    private Player player;
    private TileManager tileManager;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.yellow);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);

        player = new Player(this, key);
        tileManager = new TileManager(this);
        tileManager.generateNewMap();
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

    public Player getPlayer() {
        return player;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public void setMaxWorldCol(int maxWorldCol) {
        this.maxWorldCol = maxWorldCol;
        worldWidth = maxWorldCol * tileSize;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public void setMaxWorldRow(int maxWorldRow) {
        this.maxWorldRow = maxWorldRow;
        worldHeight = maxWorldRow * tileSize;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
}
