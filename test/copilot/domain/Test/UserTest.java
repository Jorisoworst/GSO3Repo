/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain.Test;

import copilot.domain.Administrator;
import copilot.domain.Moderator;
import copilot.domain.Player;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author IndyGames
 */
public class UserTest {
    
    Calendar now;
    Player player;
    Moderator moderator;
    Administrator administrator;
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.now = Calendar.getInstance();
        this.player = new Player("testPlayer", "pass", "", now);
        this.moderator = new Moderator("testModerator", "pass", "", now);
        this.administrator = new Administrator("testAdmin", "pass", "", now);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test from Player constructor in class Player.
     */
    @Test
    public void testPlayer() {
        // Check if a Player can be instantiated
        Assert.assertTrue("Username is incorrect", 
                this.player.getUsername().equals("testPlayer"));
        Assert.assertTrue("Password is incorrect", 
                this.player.getPassword().equals("pass"));
        Assert.assertTrue("Birthday is incorrect", 
                this.player.getDateOfBirth().compareTo(now) == 0);
            
        //* @param username the username, may not be null or empty and must be unique
        // Check if it is possible to use null as an username
        try {
            this.player = new Player(null, "password", "", this.now);
            Assert.fail("The username must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        // Check if it is possible to use an empty string as an username
        try {
            this.player = new Player("", "password", "", this.now);
            Assert.fail("The username must not be empty");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        //* @param password the password, may not be null or empty
        // Check if it is possible to use null as a password
        try {
            this.player = new Player("username", null, "", this.now);
            Assert.fail("The password must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        // Check if it is possible to use an empty string as a password
        try {
            this.player = new Player("username", "", "", this.now);
            Assert.fail("The password must not be empty");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        //* @param dateOfBirth the date of birth, may not be null
        // Check if it is possible to use null as a date of birth
        try {
            this.player = new Player("username", "password", "", null);
            Assert.fail("The date of birth must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
    }

    /**
     * Test from Moderator constructor in class Moderator.
     */
    @Test
    public void testModerator() {
        // Check if a Moderator can be instantiated
        Assert.assertTrue("Username is incorrect", 
                this.moderator.getUsername().equals("testModerator"));
        Assert.assertTrue("Password is incorrect", 
                this.moderator.getPassword().equals("pass"));
        Assert.assertTrue("Birthday is incorrect", 
                this.moderator.getDateOfBirth().compareTo(now) == 0);
                
        //* @param username the username, may not be null or empty and must be unique
        // Check if it is possible to use null as an username
        try {
            this.moderator = new Moderator(null, "password", "", this.now);
            Assert.fail("The username must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        // Check if it is possible to use an empty string as an username
        try {
            this.moderator = new Moderator("", "password", "", this.now);
            Assert.fail("The username must not be empty");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        //* @param password the password, may not be null or empty
        // Check if it is possible to use null as a password
        try {
            this.moderator = new Moderator("username", null, "", this.now);
            Assert.fail("The password must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        // Check if it is possible to use an empty string as a password
        try {
            this.moderator = new Moderator("username", "", "", this.now);
            Assert.fail("The password must not be empty");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        //* @param dateOfBirth the date of birth, may not be null
        // Check if it is possible to use null as a date of birth
        try {
            this.moderator = new Moderator("username", "password", "", null);
            Assert.fail("The date of birth must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
    }

    /**
     * Test from Administrator constructor in class Administrator.
     */
    @Test
    public void testAdministrator() {
        // Check if a Administrator can be instantiated
        Assert.assertTrue("Username is incorrect", 
                this.administrator.getUsername().equals("testAdmin"));
        Assert.assertTrue("Password is incorrect", 
                this.administrator.getPassword().equals("pass"));
        Assert.assertTrue("Birthday is incorrect", 
                this.administrator.getDateOfBirth().compareTo(now) == 0);
                
        //* @param username the username, may not be null or empty and must be unique
        // Check if it is possible to use null as an username
        try {
            this.administrator = new Administrator(null, "password", "", this.now);
            Assert.fail("The username must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        // Check if it is possible to use an empty string as an username
        try {
            this.administrator = new Administrator("", "password", "", this.now);
            Assert.fail("The username must not be empty");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        //* @param password the password, may not be null or empty
        // Check if it is possible to use null as a password
        try {
            this.administrator = new Administrator("username", null, "", this.now);
            Assert.fail("The password must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        // Check if it is possible to use an empty string as a password
        try {
            this.administrator = new Administrator("username", "", "", this.now);
            Assert.fail("The password must not be empty");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
        
        //* @param dateOfBirth the date of birth, may not be null
        // Check if it is possible to use null as a date of birth
        try {
            this.administrator = new Administrator("username", "password", "", null);
            Assert.fail("The date of birth must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
    }
    
    /**
     * Test from banUser method in class Moderator.
     */
    @Test
    public void testBanUser() {
        // Check if it is possible to ban a player
        this.moderator.banUser(player);
        Assert.assertTrue("Player is not banned", player.getIsBanned());
        
        // Check if it is possible to ban a moderator
        this.moderator.banUser(moderator);
        Assert.assertTrue("Moderator is not banned", moderator.getIsBanned());
        
        // Check if it is possible to ban an administrator
        this.moderator.banUser(administrator);
        Assert.assertTrue("Administrator is not banned", administrator.getIsBanned());
        
        //* @param user the user must not be null
        // Check if it is possible to use null as a user
        try {
            this.moderator.banUser(null);
            Assert.fail("The user to be banned must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
    }
    
    /**
     * Test from reportPlayer method in class Player.
     */
    @Test
    public void testReportPlayer() {
        // Check if it is possible to report a player
        this.player.reportPlayer(player);
        Assert.assertTrue("Player is not reported", player.getReports() == 1);
        
        // Check if it is possible to report a moderator
        this.player.reportPlayer(moderator);
        Assert.assertTrue("Moderator is not reported", moderator.getReports() == 1);
        
        // Check if it is possible to report an administrator
        this.player.reportPlayer(administrator);
        Assert.assertTrue("Administrator is not reported", administrator.getReports() == 1);
        
        //* @param user the user must not be null
        // Check if it is possible to use null as a user
        try {
            this.player.reportPlayer(null);
            Assert.fail("The user to be reported must not be null");
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == IllegalArgumentException.class);
        }
    }
}
