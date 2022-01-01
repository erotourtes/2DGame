package main;

import entity.State;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class SwapImg implements Runnable {
    Map<State, BufferedImage>  animatedImages;
    BufferedImage[] images;
    Thread thread;
    Timer timer;
    int imageIndexMoving;
    int imageIndexWaiting;

    public SwapImg(BufferedImage[] images, Map<State, BufferedImage> animatedImages) {
        this.images = images;
        this.animatedImages = animatedImages;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (thread != null) {
            if(GamePanel.isGameThreadIsNull())
                break;

            imageIndexWaiting = (imageIndexWaiting + 1) % 3;
            imageIndexMoving = (imageIndexWaiting + 1) % 2 + 3;
            animatedImages.put(State.WAITING, images[imageIndexWaiting]);
            animatedImages.put(State.MOVING, images[imageIndexMoving]);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
