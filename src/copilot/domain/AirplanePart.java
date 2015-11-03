package copilot.domain;

import java.awt.Image;

/**
 * @author IndyGames
 */
public class AirplanePart extends GameObject {
    private Airplane airplane;
    private User user;

    /**
     * Initialize an instance of the Airplane class which extends GameObject
     * @param image the image, may not be null
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     */
    public AirplanePart(Image image, Airplane airplane, User user) {
       
        super(image);
        this.airplane = airplane;
        this.user = user;
    }

    /**
     * @return the airplane
     */
    public Airplane getAirplane() {
        return airplane;
    }

    /**
     * @param airplane the airplane to set
     */
    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
