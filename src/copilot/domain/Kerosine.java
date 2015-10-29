/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.awt.Image;

/**
 *
 * @author IndyGames
 */
public class Kerosine extends Pickup {
    private int amount;

    /**
     * Initialize an instance of the Kerosine class which extends Pickup
     *
     * @param image the image, may not be null
     */
    public Kerosine(Image image, int amount) {
        super(image);
        this.amount = amount;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return this.amount;
    }
}
