package copilot.domain;

import java.util.Calendar;

/**
 * @author IndyGames
 */
public class Administrator extends Moderator {

    /**
     * Initialize an instance of the Administrator class which extends User
     * @param username the username, may not be null or empty
     * @param password the password, may not be null or empty
     * @param displayName the displayName, may be null
     * @param dateOfBirth the date of birth, may not be null or in the future
     */
    public Administrator(String username, String password, String displayName, Calendar dateOfBirth) {
        
        super(username, password, displayName, dateOfBirth);
    }
}
