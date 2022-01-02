package swappingImages;

import entity.State;

import java.awt.image.BufferedImage;
import java.util.Map;

public class SwappingPlayerWaiting extends SwapImg{
    public SwappingPlayerWaiting(int delay, BufferedImage[] images, Map<State, BufferedImage> animatedImages) {
        super(delay, images, animatedImages);
    }

    protected void changeIndex() {
        index = ++index % 3;
    }

    protected State getState() {
        return State.WAITING;
    }
}
