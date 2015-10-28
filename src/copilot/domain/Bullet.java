/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.awt.Image;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author Ruud
 */
public class Bullet extends GameObject {

    private Vector2 location;

    public Bullet(Image image, Vector2 location) {
        super(image);
        this.location = location;
    }

    public Vector2 getLocation() {
        return this.location;
    }
}
