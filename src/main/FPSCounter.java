package main;

public abstract class FPSCounter {
    private static int fpsCount = 0;
    private static long timer = 0;

    public static void count(long lastRepainted) {
        fpsCount++;
        timer = System.nanoTime() - lastRepainted;
        if (timer >= Math.pow(10, 9)) {
            System.out.println("FPS: " + fpsCount);
            fpsCount = 0;
            timer = 0;
        }
    }
}
