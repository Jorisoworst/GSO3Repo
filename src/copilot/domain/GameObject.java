/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import javafx.scene.image.Image;
import org.dyn4j.dynamics.Body;

/**
 *
 * @author Joris
 */
public abstract class GameObject extends Body {

    private final double height;
    private final double width;
    private final Image image;
    private boolean isDestroyed;

    /**
     * Initialize an instance of the GameObject class which is abstract
     *
     * @param image the image, may not be null
     */
    public GameObject(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("No image set!");
        }

        this.image = image;
        this.isDestroyed = false;
        this.height = this.image.getHeight();
        this.width = this.image.getWidth();
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Method to call when a collision happened
     *
     * @param otherGameObject the GameObject which the airplane collides with,
     * may not be null
     */
    public void onCollision(GameObject otherGameObject) {
        if (otherGameObject == null) {
            throw new IllegalArgumentException("No other GameObject found!");
        }

        if (Obstacle.class.isInstance(otherGameObject)) {
            this.destroy();
        } else if (Pickup.class.isInstance(otherGameObject)) {
            ((Pickup) otherGameObject).setPickedUp(true);
        }

        otherGameObject.destroy();
    }

    /**
     * Method to call when the GameObject gets destroyd
     */
    public void destroy() {
        this.isDestroyed = true;
    }
}
