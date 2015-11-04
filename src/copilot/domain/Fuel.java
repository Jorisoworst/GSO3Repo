package copilot.domain;

import java.awt.Image;

/**
 * @author IndyGames
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
