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
import java.io.IOException;
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
 * @author IndyGames
 */
public class DatabaseAdministrationTest {
    private DatabaseAdministration dbAdmin;
    private int testAccountId;
    private Score testScoreObj;

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
            System.out.println(ex.toString());
        }

        this.testAccountId = 0;
        this.testScoreObj = null;
    }

    @After
    public void tearDown() {

    }

    /**
     * Add a user to the database
     *
     * @return the added user
     */
    public User addTestUser() {
        try {
            Random random = new Random();
            int randomInt = random.nextInt(10000);
            User user = new Player("TestUser" + randomInt, "testPassword", "TestDisplayName", Calendar.getInstance());
            user.setRegistrationDate(Calendar.getInstance());

            User resultUser = dbAdmin.AddUser(user);
            if (resultUser != null) {
                this.testAccountId = resultUser.getId();
                return resultUser;
            } else {
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(DatabaseAdministrationTest.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Delete a user
     *
     * @return whether the user is deleted or not
     */
    public boolean deleteTestUser() {
        try {
            return dbAdmin.DeleteUser(testAccountId);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    /**
     * Add users and add a score to them
     *
     * @return
     */
    public Score addTestScore() {
        // Add 4 users
        User user1 = addTestUser();
        User user2 = addTestUser();
        User user3 = addTestUser();
        User user4 = addTestUser();

        // Then add score
        Score score = new Score(900, user1.getId(), user2.getId(), user3.getId(), user4.getId());
        try {
            return dbAdmin.AddScore(score);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * Delete the score of the users
     */
    public void DeleteTestScore() {
        // Save testScoreUser id's
        if (testScoreObj != null) {
            int user1Id = this.testScoreObj.getUser1id();
            int user2Id = this.testScoreObj.getUser2id();
            int user3Id = this.testScoreObj.getUser3id();
            int user4Id = this.testScoreObj.getUser4id();

            try {
                dbAdmin.DeleteScore(testScoreObj.getScoreId());
                dbAdmin.DeleteUser(user1Id);

                dbAdmin.DeleteUser(user2Id);

                dbAdmin.DeleteUser(user3Id);
                dbAdmin.DeleteUser(user4Id);
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    /**
     * Test of AddUser method, of class DatabaseAdministration.
     */
    @Test
    public void testAddUser() {
        // Add a user, check if the result is correct and delete it afterwards
        User result = addTestUser();
        assertNotNull(result);
        deleteTestUser();
    }

    /**
     * Test of DeleteUser method, of class DatabaseAdministration.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testDeleteUser() throws IOException {
        // Add a user, delete it and check if it worked out or not
        User testUser = addTestUser();
        boolean result = dbAdmin.DeleteUser(testUser.getId());
        assertTrue(result);
    }

    /**
     * Test of GetUsers method, of class DatabaseAdministration.
     */
    @Test
    public void testGetUsers() {
        try {
            // Test if we successfully get users from database.
            addTestUser();
            boolean foundAddedUser = false;
            List<User> result = dbAdmin.GetUsers();
            if (result.size() > 0) {
                for (User usr : result) {
                    if (usr.getId() == testAccountId) {
                        foundAddedUser = true;
                    }
                }
            }

            assertTrue(foundAddedUser);
            deleteTestUser();
        } catch (IOException ex) {
            System.out.println(ex.toString());
            fail("Error database connection.");
        }
    }

    /**
     * Test of UpdateUser method, of class DatabaseAdministrat
     *
     * @throws java.io.IOException
     */
    @Test
    public void testUpdateUser() throws IOException {
        // Add a user and make changes to it
        User testUser = addTestUser();
        testUser.setDisplayName("testUserUpdated");
        testUser.setBanned(true);
        testUser.setLevel(1);
        testUser.setExperiencePoints(500);
        boolean result = dbAdmin.UpdateUser(testUser);
        assertTrue("update failed", result);

        // Get all users, find the user and see if changes are really made
        List<User> users = dbAdmin.GetUsers();
        User getUser = null;
        if (users.size() > 0) {
            for (User usr : users) {
                if (usr.getId() == testAccountId) {
                    getUser = usr;
                }
            }
        }

        assertNotNull(getUser);
        assertEquals("display name check", testUser.getDisplayName(), getUser.getDisplayName());
        assertEquals("banned check", testUser.isBanned(), getUser.isBanned());
        assertEquals("level check", testUser.getLevel(), getUser.getLevel());
        assertEquals("exp check", testUser.getExperiencePoints(), getUser.getExperiencePoints());
        deleteTestUser();
    }

    /**
     * Test of AddScore method, of class DatabaseAdministration.
     */
    @Test
    public void testAddScore() {
        Score score = addTestScore();
        assertNotNull(score);

        // Add asserts to check if user id's are not 0
        List<Score> allScores;
        try {
            allScores = dbAdmin.SelectScore();

            boolean isAdded = false;
            for (Score sc : allScores) {
                if (sc.getScoreId() == score.getScoreId()) {
                    // Is in the list, so is added.
                    isAdded = true;

                    // Check the user Id's
                    assertEquals("user 1 check id.", sc.getUser1id(), score.getUser1id());
                    assertEquals("user 2 check id", sc.getUser2id(), score.getUser2id());
                    assertEquals("user 3 check id", sc.getUser3id(), score.getUser3id());
                    assertEquals("user 4 check id", sc.getUser4id(), score.getUser4id());
                }
            }

            assertTrue("added score does not exist in list.", isAdded);
            DeleteTestScore();
        } catch (IOException ex) {
            System.out.println(ex.toString());
            fail("error making db connection.");
        }
    }

    /**
     * Test of SelectScore method, of class DatabaseAdministration.
     */
    @Test
    public void testSelectScore() {
        try {
            List<Score> result = dbAdmin.SelectScore();
            
            // if it is null an exception occures.
            assertNotNull(result);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
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
        GameSetting result;
        try {
            result = dbAdmin.GetGameSetting();

            boolean addesTested = false;
            if (result == null) {
                // Might be none in the database so add one
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
            // If it is null then exception occures or there are no game settings found
            assertNotNull(result); 
            if (!addesTested) {
                // Didn't test adding, so try to resave the current result.
                boolean saved = dbAdmin.SaveGameSetting(result);
                assertTrue(saved);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            fail("error making db connection.");
        }
    }
}
