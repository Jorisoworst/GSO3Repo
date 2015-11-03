package copilot.domain;

import copilot.view.anim.Animation;
import java.awt.Image;

/**
 * @author IndyGames
 */
public class Obstacle extends GameObject {

    private Animation animation;
    private boolean isDestructable;

    /**
     * Initialize an instance of the Obstacle class which extends GameObject
     *
     * @param image the image, may not be null
     */
    public Obstacle(Image image) {

        super(image);
        this.isDestructable = false;
    }

    /**
     * @return the isDestructable
     */
    public boolean isDestructable() {
        return this.isDestructable;
    }

    /**
     * @param isDestructable the isDestructable to set
     */
    public void setDestructable(boolean isDestructable) {
        this.isDestructable = isDestructable;
    }

    /**
     * @return the animation
     */
    public Animation getAnimation() {
        return this.animation;
    }

    /**
     * @param animation the animation to set
     */
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
}
