/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain.Test;

import copilot.domain.GameAdministration;
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
 * @author Niels
 */
public class GameAdministrationTest {
    GameAdministration admin;
    
    public GameAdministrationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.admin = GameAdministration.getInstance();
    }
    
    @After
    public void tearDown() {
    }

    // test
    @Test
    public void createSessionTest() {
        // create a calendar to use for the player
        Calendar calendar = Calendar.getInstance();
        calendar.set(1997, 04, 16);
        
        // create a new player
        Player player = new Player("NukeFireX", "12345", calendar);
        
        // check if you get true when adding a real host
        Assert.assertTrue("Not able to create a session", this.admin.createSession(player));
        
        // check if the list with sessions has count 1
        Assert.assertEquals("the session was not added to the list", 1, this.admin.getSessions().size());
        
        // check if you get false when adding null as host
        Assert.assertFalse("Creating a session with null as host should not be accepted", this.admin.createSession(null));
    }
}
