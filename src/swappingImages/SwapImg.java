package swappingImages;

import entity.State;
import main.GamePanel;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class SwapImg {
    Map<State, BufferedImage>  animatedImages;
    BufferedImage[] images;
    Timer timer;
    int index;

    public SwapImg(int delay, BufferedImage[] images, Map<State, BufferedImage> animatedImages) {
        this.animatedImages = animatedImages;
        this.images = images;

        timer = new Timer(delay, (e)->swap());
        timer.start();
    }

    private void swap() {
        if(GamePanel.isGameThreadIsNull()) timer.stop();

        changeIndex();
        animatedImages.put(getState(), images[index]);
    }

    abstract protected void changeIndex();

    abstract protected State getState();
}
