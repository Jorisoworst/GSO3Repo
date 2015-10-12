/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import org.dyn4j.dynamics.Body;

/**
 *
 * @author Joris
 */
public abstract class GameObject extends Body {

//    private double height;
//    private double width;
//    private double x;
//    private double y;
    private boolean isDestroyed;

    /**
     * Initialize an instance of the GameObject class which is abstract
     */
    public GameObject() {
//        this.height = 0;
//        this.width = 0;
//        this.x = 0;
//        this.y = 0;
        this.isDestroyed = false;
    }

    /**
     * @return the height
     */
//    public double getHeight() {
//        return height;
//    }

    /**
     * @param height the height to set
     */
//    public void setHeight(double height) {
//        this.height = height;
//    }

    /**
     * @return the width
     */
//    public double getWidth() {
//        return width;
//    }

    /**
     * @param width the width to set
     */
//    public void setWidth(double width) {
//        this.width = width;
//    }

    /**
     * @return the x
     */
//    public double getX() {
//        return x;
//    }

    /**
     * @param x the x to set
     */
//    public void setX(double x) {
//        this.x = x;
//    }

    /**
     * @return the y
     */
//    public double getY() {
//        return y;
//    }

    /**
     * @param y the y to set
     */
//    public void setY(double y) {
//        this.y = y;
//    }

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

        // Bounce or something
        this.destroy();
    }

    /**
     * Method to call when the GameObject gets destroyd
     */
    public void destroy() {
        this.isDestroyed = true;
    }
}
