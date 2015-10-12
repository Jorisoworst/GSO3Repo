/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

/**
 *
 * @author Niels
 */
public class AirplanePart extends GameObject {
    private Airplane airplane;
    private User user;
    
    /**
     * Initialize an instance of the Airplane class which extends GameObject
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     */
    public AirplanePart(Airplane airplane, User user) {
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
