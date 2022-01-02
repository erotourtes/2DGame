package swappingImages;

import entity.State;

import java.awt.image.BufferedImage;
import java.util.Map;

public class SwappingPlayerMoving extends SwapImg{
    public SwappingPlayerMoving(int delay, BufferedImage[] images, Map<State, BufferedImage> animatedImages) {
        super(delay, images, animatedImages);
    }

    protected void changeIndex() {
        index = index % 2 + 3;
    }

    protected State getState() {
        return State.MOVING;
    }
}
