/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import org.dyn4j.dynamics.Body;

/**
 *
 * @author IndyGames
 */
public abstract class GameObject extends Body {

    private double height, width;
    private Image image;

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
        this.height = this.image.getHeight(null);
        this.width = this.image.getWidth(null);
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
     * @return the image
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * @param image the image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Render the object
     *
     * @param g the 2D graphics object
     */
    public void render(Graphics2D g) {
        AffineTransform ot = g.getTransform();
        AffineTransform lt = new AffineTransform();
        lt.translate(this.transform.getTranslationX(), this.transform.getTranslationY());
        lt.rotate(this.transform.getRotation());
        g.transform(lt);
        g.setTransform(ot);
        g.drawImage(this.image, (int) lt.getTranslateX(), (int) lt.getTranslateY(), null);
    }
}
