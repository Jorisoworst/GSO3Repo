package copilot.domain;

import java.awt.Image;

/**
 *
 * @author IndyGames
 */
public class Obstacle extends GameObject {

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
}
