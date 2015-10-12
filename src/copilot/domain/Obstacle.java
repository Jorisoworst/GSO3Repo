/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import javafx.scene.image.Image;

/**
 *
 * @author Joris
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
