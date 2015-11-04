package copilot.domain;

import java.awt.Image;

/**
 * @author IndyGames
 */
public class Kerosine extends Pickup {

    private final int amount;

    /**
     * Initialize an instance of the Kerosine class which extends Pickup
     *
     * @param image the image, may not be null
     * @param amount the amount of fuel of this pickup, must be greater than 0
     */
    public Kerosine(Image image, int amount) {
        super(image);

        if (amount <= 0) {
            throw new IllegalArgumentException("Value of amount too low!");
        }

        this.amount = amount;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return this.amount;
    }
}
