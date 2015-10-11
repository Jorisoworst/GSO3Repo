/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

import java.util.Date;

/**
 *
 * @author Niels
 */
public class Administrator extends Moderator {
    
    /**
     * Initialize an instance of the Administrator class which extends User
     * @param username the username, may not be null or empty
     * @param password the password, may not be null or empty
     * @param dateOfBirth the date of birth, may not be null or in the future
     */
    public Administrator(String username, String password, Date dateOfBirth) {
        super(username, password, dateOfBirth);
    }
   
}
