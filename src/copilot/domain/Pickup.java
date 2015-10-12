/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

/**
 *
 * @author Niels
 */
public abstract class Pickup extends GameObject {

    private boolean isPickedUp;

    /**
     * Initialize an instance of the Pickup class which extends GameObject
     */
    public Pickup() {
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
