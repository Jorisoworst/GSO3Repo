package copilot.domain;

import java.awt.Image;

/**
 * @author IndyGames
 */
public abstract class Pickup extends GameObject {

    private boolean isPickedUp;

    /**
     * Initialize an instance of the Pickup class which extends GameObject
     *
     * @param image the image, may not be null
     */
    public Pickup(Image image) {
        super(image);

        this.isPickedUp = false;
    }

    /**
     * @return the isPickedUp
     */
    public boolean isPickedUp() {
        return this.isPickedUp;
    }

    /**
     * @param isPickedUp the isPickedUp to set
     */
    public void setPickedUp(boolean isPickedUp) {
        this.isPickedUp = isPickedUp;
    }
}
