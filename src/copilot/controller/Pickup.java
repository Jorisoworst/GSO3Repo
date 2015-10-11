/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

/**
 *
 * @author Niels
 */
public abstract class Pickup extends GameObject {
    private boolean hasBeenPickedUp;

    /**
     * Initialize an instance of the Pickup class which extends GameObject
     */
    public Pickup() {
        this.hasBeenPickedUp = false;
    }
    
    /**
     * @return the hasBeenPickedUp
     */
    public boolean isHasBeenPickedUp() {
        return hasBeenPickedUp;
    }

    /**
     * @param hasBeenPickedUp the hasBeenPickedUp to set
     */
    public void setHasBeenPickedUp(boolean hasBeenPickedUp) {
        this.hasBeenPickedUp = hasBeenPickedUp;
    }
    
}
