/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import javafx.scene.image.Image;

/**
 *
 * @author Niels
 */
public class Fuel extends AirplanePart {

    /**
     * Initialize an instance of the Fuel class which extends ArplanePart
     *
     * @param image the image, may not be null
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     */
    public Fuel(Image image, Airplane airplane, User user) {
        super(image, airplane, user);
    }
}
