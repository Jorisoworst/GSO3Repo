/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.util.Calendar;

/**
 *
 * @author IndyGames
 */
public class Player extends User {

    /**
     * Initialize an instance of the Player class which implements User
     *
     * @param username the username, may not be null or empty
     * @param password the password, may not be null or empty
     * @param displayName the displayName, may be null
     * @param dateOfBirth the date of birth, may not be null or empty
     */
    public Player(String username, String password, String displayName, Calendar dateOfBirth) {
        super(username, password, displayName, dateOfBirth);
    }

    /**
     * Method to report a user
     *
     * @param user the user to report, may not be null
     */
    public void reportPlayer(User user) {
        if (user == null) {
            throw new IllegalArgumentException("No user to report");
        }

        user.addReport();
    }
}
