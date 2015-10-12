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
public class Obstacle extends GameObject {

    private boolean isDestructable;

    /**
     * Initialize an instance of the Obstacle class which extends GameObject
     */
    public Obstacle() {
        this.isDestructable = false;
    }

    /**
     * @return the isDestructable
     */
    public boolean isIsDestructable() {
        return this.isDestructable;
    }

    /**
     * @param isDestructable the isDestructable to set
     */
    public void setIsDestructable(boolean isDestructable) {
        this.isDestructable = isDestructable;
    }
}
