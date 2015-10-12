/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.util.Date;

/**
 *
 * @author Niels
 */
public class Player extends User {

    /**
     * Initialize an instance of the Player class which implements User
     * @param username the username, may not be null or empty
     * @param password the password, may not be null or empty
     * @param dateOfBirth the date of birth, may not be null or empty
     */
    public Player(String username, String password, Date dateOfBirth) {
        super(username, password, dateOfBirth);
    }   
    
    /**
     * Method to report a user
     * @param user the user to report, may not be null
     * @return a boolean whether reporting the player went well or not
     */
    public boolean reportPlayer(User user) {
        // TODO
        return false;
    }
}
