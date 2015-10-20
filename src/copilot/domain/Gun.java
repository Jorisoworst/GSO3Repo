/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.awt.Point;
import javafx.scene.image.Image;

/**
 *
 * @author Niels
 */
public class Gun extends AirplanePart {
    private int fireRate;
    
    /**
     * Initialize an instance of the Gun class which extends ArplanePart
     *
     * @param image the image, may not be null
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     */
    public Gun(Image image, Airplane airplane, User user) {
        super(image, airplane, user);
    }

    /**
     * @return the fireRate
     */
    public int getFireRate() {
        return fireRate;
    }

    /**
     * @param fireRate the fireRate to set
     */
    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }
    
    public void shoot(Point mousePoint) {
        // TODO
        Bullet bullet = new Bullet(null, mousePoint);
        //TODO: end where to? give a start position to bullet?
    }
}
