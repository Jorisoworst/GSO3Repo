/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain.Test;

import copilot.domain.DatabaseAdministration;
import copilot.domain.GameSetting;
import copilot.domain.Player;
import copilot.domain.Score;
import copilot.domain.User;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ruud
 */
public class DatabaseAdministrationTest {
    
    DatabaseAdministration dbAdmin;
    private int testAccountId = 0;
    private Score testScoreObj = null;
    
    public DatabaseAdministrationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            dbAdmin = new DatabaseAdministration();
        } catch (Exception ex) {
            assertFalse(true);
        }
        
    }
    
    @After
    public void tearDown() {
    }

    public User AddTestUser()
    {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        User user = new Player("TestUser"+randomInt, "testPassword", Calendar.getInstance());
        user.setDisplayName("TestDisplayName");
        user.setRegistrationDate(Calendar.getInstance());
        User resultUser = dbAdmin.AddUser(user);
        if(resultUser != null)
        {
            this.testAccountId = resultUser.getId();
            return resultUser;
        }
        else
        {
            return null;
        }
    }
    
    public boolean DeleteTestUser()
    {
        return dbAdmin.DeleteUser(testAccountId);
    }

    
    /**
     * Test of AddUser method, of class DatabaseAdministration.
     */
    @Test
    public void testAddUser() {
        User result = AddTestUser();
        assertNotNull(result);
        DeleteTestUser();
    }
/**
     * Test of DeleteUser method, of class DatabaseAdministration.
     */
    @Test
    public void testDeleteUser() {
        User testUser = AddTestUser();
        boolean result = dbAdmin.DeleteUser(testUser.getId());
        assertTrue(result);
    }
    
    /**
     * Test of GetUsers method, of class DatabaseAdministration.
     */
    @Test
    public void testGetUsers() {
        //just test if we successfully get users from database.
        AddTestUser();
        boolean foundAddedUser = false;
        List<User> result = dbAdmin.GetUsers();
        if(result.size() > 0)
        {
            for (User usr : result)
            {
                if(usr.getId() == testAccountId)
                {
                    foundAddedUser = true;
                }
            }
        }
        assertTrue(foundAddedUser);
        DeleteTestUser();
        
    }
   
    
    /**
     * Test of UpdateUser method, of class DatabaseAdministration.
     */
    @Test
    public void testUpdateUser() {
        User testUser = AddTestUser();
        testUser.setDisplayName("testUserUpdated");
        testUser.setIsBanned(true);
        testUser.setLevel(1);
        testUser.setExperiencePoints(500);
        boolean result = dbAdmin.UpdateUser(testUser);
        assertTrue("update failed", result);
        
        //get all users, find this user and see if changes are really made
        List<User> users = dbAdmin.GetUsers();
        User getUser = null;
        if(users.size() > 0)
        {
            for (User usr : users)
            {
                if(usr.getId() == testAccountId)
                {
                    getUser = usr;
                }
            }
        }
        assertNotNull(getUser);
        assertEquals("display name check", testUser.getDisplayName(), getUser.getDisplayName());
        assertEquals("banned check", testUser.getIsBanned(), getUser.getIsBanned());
        assertEquals("level check", testUser.getLevel(), getUser.getLevel());
        assertEquals("exp check", testUser.getExperiencePoints(), getUser.getExperiencePoints());
        DeleteTestUser();
    }

    public Score AddTestScore()
    {
        //first add 4 users
        User user1 = AddTestUser();
        User user2 = AddTestUser();
        User user3 = AddTestUser();
        User user4 = AddTestUser();
        
        //then add score
        Score score = new Score(900, user1.getId(), user2.getId(), user3.getId(), user4.getId());
        return dbAdmin.AddScore(score);
        
    }
    
    public void DeleteTestScore()
    {
        //save testScoreUser id's
        if(testScoreObj != null)
        {
            int user1Id = this.testScoreObj.getUser1id();
            int user2Id = this.testScoreObj.getUser2id();
            int user3Id = this.testScoreObj.getUser3id();
            int user4Id = this.testScoreObj.getUser4id();
            dbAdmin.DeleteScore(testScoreObj.getScoreId());
            dbAdmin.DeleteUser(user1Id);
            dbAdmin.DeleteUser(user2Id);
            dbAdmin.DeleteUser(user3Id);
            dbAdmin.DeleteUser(user4Id);
            
        }
        
    }
    
    /**
     * Test of AddScore method, of class DatabaseAdministration.
     */
    @Test
    public void testAddScore() {
        Score score = AddTestScore();
        assertNotNull(score);
        //add asserts to check if user id's are not 0
        List<Score> allScores = dbAdmin.SelectScore();
        boolean isAdded = false;
        for (Score sc : allScores)
            {
                if(sc.getScoreId() == score.getScoreId())
                {
                    //is in list, so is added.
                    isAdded = true;
                    //check user Id's
                    assertEquals("user 1 check id.",sc.getUser1id(), score.getUser1id());
                    assertEquals("user 2 check id",sc.getUser2id(), score.getUser2id());
                    assertEquals("user 3 check id",sc.getUser3id(), score.getUser3id());
                    assertEquals("user 4 check id",sc.getUser4id(), score.getUser4id());
                }
            }
        assertTrue("added score does not exist in list.",isAdded);
        DeleteTestScore();
    }
    

    /**
     * Test of SelectScore method, of class DatabaseAdministration.
     */
    @Test
    public void testSelectScore() {
        List<Score> result = dbAdmin.SelectScore();
        assertNotNull(result); //if null then exception occured.
    }


    /**
     * Test of UpdateScore method, of class DatabaseAdministration.
     */
    @Test
    public void testUpdateScore() {
        
    }

    /**
     * Test of DeleteScore method, of class DatabaseAdministration.
     */
    @Test
    public void testDeleteScore() {
        
    }

    /**
     * Test of GetGameSetting method, of class DatabaseAdministration.
     */
    @Test
    public void testGetAndSaveGameSetting() {
        
        GameSetting result = dbAdmin.GetGameSetting();
        boolean addesTested = false;
        if(result == null)
        {
            //might be none in database, add one
            
            GameSetting gameSetting = new GameSetting();
            gameSetting.setLevelUp(50);
            gameSetting.setMaxFuelCapacity(100);
            gameSetting.setMaxUser(4);
            gameSetting.setRequiredExperiencePoints(2);
            boolean savedSetting = dbAdmin.SaveGameSetting(gameSetting);
            assertTrue(savedSetting);
            result = dbAdmin.GetGameSetting();
            addesTested = true;
        }
        assertNotNull(result); //if null then exception occured or  no game settings found.
        if(!addesTested)
        {
            //didn't test adding, so will try to resave the current result.
            boolean saved = dbAdmin.SaveGameSetting(result);
            assertTrue(saved);
        }
    }
    
}
