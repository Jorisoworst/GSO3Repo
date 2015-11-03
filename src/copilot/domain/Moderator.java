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
public class Moderator extends User {

    /**
     * Initialize an instance of the Moderator class which implements User
     *
     * @param username the username, may not be null or empty
     * @param password the password, may not be null or empty
     * @param displayName the displayName, may be null
     * @param dateOfBirth the date of birth, may not be null or empty
     */
    public Moderator(String username, String password, String displayName, Calendar dateOfBirth) {
        super(username, password, displayName, dateOfBirth);
    }

    /**
     * Method to ban a user
     *
     * @param user the user must not be null
     */
    public void banUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The user must not be null");
        }

        user.setIsBanned(true);
    }
}
