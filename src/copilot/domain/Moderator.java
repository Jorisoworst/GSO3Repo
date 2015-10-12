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
public class Moderator extends User {

    /**
     * Initialize an instance of the Moderator class which implements User
     * @param username the username, may not be null or empty
     * @param password the password, may not be null or empty
     * @param dateOfBirth the date of birth, may not be null or empty
     */
    public Moderator(String username, String password, Date dateOfBirth) {
        super(username, password, dateOfBirth);
    }
    
    /**
    * 
    * @param id the user id, may not be 0 or negative
    * @return a boolean whether banning the user went well or not
    */
    public boolean banUser(int id) {
        // TODO
        return false;
    }
}
