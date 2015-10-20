/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javafx.scene.image.Image;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;

/**
 *
 * @author Joris
 */
public abstract class GameObject extends Body {

    public static final double SCALE = 45.0;
    private double height;
    private double width;
    private Image image;
    private Color color;
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
        this.color = new Color(
                (float) Math.random() * 0.5f + 0.5f,
                (float) Math.random() * 0.5f + 0.5f,
                (float) Math.random() * 0.5f + 0.5f);
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
     * @return isDestroyed
     */
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    /**
     * Method to call when the GameObject gets destroyd
     */
    public void destroy() {
        this.isDestroyed = true;
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
     * Render the object
     *
     * @param g the 2D graphics object
     */
    public void render(Graphics2D g) {
        AffineTransform ot = g.getTransform();
        AffineTransform lt = new AffineTransform();
        lt.translate(this.transform.getTranslationX() * SCALE, this.transform.getTranslationY() * SCALE);
        lt.rotate(this.transform.getRotation());
        g.transform(lt);

        for (BodyFixture fixture : this.fixtures) {
            Convex convex = fixture.getShape();
            Graphics2DRenderer.render(g, convex, SCALE, color);
        }

        g.setTransform(ot);
    }
}
