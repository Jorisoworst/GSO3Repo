package copilot.domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author IndyGames
 */
public class DatabaseAdministration {

    private Connection conn;

    /**
     * Initializes an instance of the DatabseAdministration
     * @throws Exception
     */
    public DatabaseAdministration() throws Exception {
        
        if (!initConnection()) {
            throw new Exception("initializing database failed.");
        }
        closeConnection();
    }

    /**
     * Initilialize the connection to the database
     * @return True if the connection was succesfully established, false if not
     * @throws FileNotFoundException
     * @throws IOException
     */
    private boolean initConnection() throws FileNotFoundException, IOException {
        try {
            Properties props = new Properties();

            try (FileInputStream in = new FileInputStream("database.properties")) {
                props.load(in);
            }

            Class.forName(props.getProperty("driver")).newInstance();
            this.conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("username"),
                    props.getProperty("password"));

            return true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Close the connection to the database
     */
    private void closeConnection() {
        try {
            this.conn.close();
            this.conn = null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Get all the user from the database
     * @return All the users from the databas
     * @throws IOException
     */
    public ArrayList<User> GetUsers() throws IOException {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        try {
            if (initConnection()) {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    int accountId = rs.getInt("Id");
                    Date bday = rs.getDate("DateOfBirth");
                    Calendar calBirthday = null;

                    if (bday != null) {
                        calBirthday = Calendar.getInstance();
                        calBirthday.setTime(bday);
                    }

                    String displayName = rs.getString("DisplayName");
                    int experiencePoint = rs.getInt("ExperiencePoint");
                    boolean isBanned = rs.getBoolean("IsBanned");
                    int level = rs.getInt("Level");
                    String password = rs.getString("Password");
                    int personalBest = rs.getInt("PersonalBest");
                    Date registrationDate = rs.getDate("RegistrationDate");
                    Calendar calRegistrationDate = null;

                    if (registrationDate != null) {
                        calRegistrationDate = Calendar.getInstance();
                        calRegistrationDate.setTime(bday);
                    }

                    String username = rs.getString("Username");
                    String userType = rs.getString("UserType");

                    //a,m,p
                    User user = null;

                    switch (userType) {
                        case "A":
                            user = new Administrator(username, password, displayName, calBirthday);
                            break;
                        case "M":
                            user = new Moderator(username, password, displayName, calBirthday);
                            break;
                        case "P":
                            user = new Player(username, password, displayName, calBirthday);
                            break;
                    }

                    if (user != null) {
                        user.setExperiencePoints(experiencePoint);
                        user.setId(accountId);
                        user.setLevel(level);
                        user.setIsBanned(isBanned);
                        user.setPassword(password);
                        user.setPersonalBestScore(personalBest);
                        user.setRegistrationDate(calRegistrationDate);
                        user.setReports(0);
                        users.add(user);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return users;
    }

    /**
     * Add a new user to the database
     * @param user The user to be added
     * @return The user that was added
     * @throws IOException
     */
    public User AddUser(User user) throws IOException {
        String query = "INSERT INTO user (DateOfBirth, DisplayName, ExperiencePoint, IsBanned, Level, Password, PersonalBest, RegistrationDate, Report, Username, UserType) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try {
            if (initConnection()) {
                PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                st.setDate(1, new java.sql.Date(user.getDateOfBirth().getTimeInMillis()));
                st.setString(2, user.getDisplayName());
                st.setInt(3, user.getExperiencePoints());
                st.setBoolean(4, user.getIsBanned());
                st.setInt(5, user.getLevel());
                st.setString(6, user.getPassword());
                st.setInt(7, user.getPersonalBestScore());
                st.setDate(8, new java.sql.Date(user.getRegistrationDate().getTimeInMillis()));
                st.setInt(9, user.getReports());
                st.setString(10, user.getUsername());

                if (user instanceof Administrator) {
                    st.setString(11, "A");
                } else if (user instanceof Moderator) {
                    st.setString(11, "M");
                } else {
                    st.setString(11, "P");
                }

                int resultId = st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();

                if (rs.next()) {
                    resultId = rs.getInt(1);
                }

                user.setId(resultId);

                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return null;
    }

    /**
     * Update an existing user from the database
     * @param user The user to be updated
     * @return True if the update was succesful, false if not
     * @throws IOException
     */
    public boolean UpdateUser(User user) throws IOException {
        String query = "UPDATE user SET DateOfBirth = ?, DisplayName = ?, ExperiencePoint = ?, IsBanned = ?, Level = ?, Password = ?, PersonalBest = ?,"
                + " RegistrationDate = ?, Report = ?, Username = ?, UserType = ? WHERE Id = ?";
        try {
            if (initConnection()) {
                PreparedStatement st = conn.prepareStatement(query);
                st.setDate(1, new java.sql.Date(user.getDateOfBirth().getTimeInMillis()));
                st.setString(2, user.getDisplayName());
                st.setInt(3, user.getExperiencePoints());
                st.setBoolean(4, user.getIsBanned());
                st.setInt(5, user.getLevel());
                st.setString(6, user.getPassword());
                st.setInt(7, user.getPersonalBestScore());
                st.setDate(8, new java.sql.Date(user.getRegistrationDate().getTimeInMillis()));
                st.setInt(9, user.getReports());
                st.setString(10, user.getUsername());

                if (user instanceof Administrator) {
                    st.setString(11, "A");
                } else if (user instanceof Moderator) {
                    st.setString(11, "M");
                } else {
                    st.setString(11, "P");
                }

                st.setInt(12, user.getId());
                st.executeUpdate();

                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return false;
    }

    /**
     * Delete an existing user from the database
     * @param userId The user id of the user to be deleted
     * @return True if the deletion was succesful, false if not
     * @throws IOException
     */
    public boolean DeleteUser(int userId) throws IOException {
        try {
            if (initConnection()) {
                String query = "DELETE FROM user WHERE Id = ?";
                PreparedStatement st = conn.prepareStatement(query);
                st.setInt(1, userId);
                st.executeUpdate();

                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return false;
    }

    /**
     * Get a highscore from the database
     * @return The score
     * @throws IOException
     */
    public ArrayList<Score> SelectScore() throws IOException {
        ArrayList<Score> scores = new ArrayList<>();
        String query = "SELECT * FROM score";

        try {
            if (initConnection()) {
                try (Statement st = conn.createStatement()) {
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        int scoreId = rs.getInt("Id");
                        int score = rs.getInt("Score");
                        int user1 = rs.getInt("User_1");
                        int user2 = rs.getInt("User_2");
                        int user3 = rs.getInt("User_3");
                        int user4 = rs.getInt("User_4");

                        Score scoreObj = new Score(score, user1, user2, user3, user4);
                        scoreObj.setScoreId(scoreId);
                        scores.add(scoreObj);
                    }
                }
                return scores;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return null;
    }

    /**
     * Add a highscore from the database
     * @param score The score to be added
     * @return The score that was added
     * @throws IOException
     */
    public Score AddScore(Score score) throws IOException {
        String query = "INSERT INTO score (Score, User_1, User_2, User_3, User_4) VALUES(?,?,?,?,?)";

        try {
            if (initConnection()) {
                PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                st.setInt(1, score.getScore());
                st.setInt(2, score.getUser1id());
                st.setInt(3, score.getUser2id());
                st.setInt(4, score.getUser3id());
                st.setInt(5, score.getUser4id());
                int resultId = st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();

                if (rs.next()) {
                    resultId = rs.getInt(1);
                }

                score.setScoreId(resultId);

                return score;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return null;
    }

    /**
     * Update a highscore from the database
     * @param score The score to be updated
     * @return True if the update was succesful, false if not
     * @throws IOException
     */
    public boolean UpdateScore(Score score) throws IOException {
        String query = "UPDATE score SET Score = ?, User_1 =?, User_2 = ?, User_3 = ?, User_4 = ?";

        try {
            if (initConnection()) {
                PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                st.setInt(1, score.getScore());
                st.setInt(2, score.getUser1id());
                st.setInt(3, score.getUser2id());
                st.setInt(4, score.getUser3id());
                st.setInt(5, score.getUser4id());
                st.executeUpdate();

                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return false;
    }

    /**
     * Delete a highscore from the database
     * @param scoreId The score id of the score to be deleted
     * @return True if the deletion was succesful, false if not
     * @throws IOException
     */
    public boolean DeleteScore(int scoreId) throws IOException {
        try {
            if (initConnection()) {
                String query = "DELETE FROM score WHERE Id = ?";
                PreparedStatement st = conn.prepareStatement(query);
                st.setInt(1, scoreId);
                st.executeUpdate();

                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return false;
    }

    /**
     * Get a game setting from the database
     * @return The game setting
     * @throws IOException
     */
    public GameSetting GetGameSetting() throws IOException {
        GameSetting gameSettings = null;
        String query = "SELECT * FROM gameSetting";

        try {
            if (initConnection()) {
                try (Statement st = conn.createStatement()) {
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        double levelUp = rs.getDouble("LevelUp");
                        int maxFuel = rs.getInt("MaxFuelCapacity");
                        int maxUser = rs.getInt("MaxUser");
                        int requiredExp = rs.getInt("RequiredExperiencePoint");

                        gameSettings = new GameSetting();
                        gameSettings.setLevelUp(levelUp);
                        gameSettings.setMaxFuelCapacity(maxFuel);
                        gameSettings.setMaxUser(maxUser);
                        gameSettings.setRequiredExperiencePoints(requiredExp);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return gameSettings;
    }

    /**
     * Save a game setting to the database
     * @param gameSetting The game setting to be added
     * @return True if the insert was succesful, false if not
     * @throws IOException
     */
    public boolean SaveGameSetting(GameSetting gameSetting) throws IOException {
        String trunQuery = "TRUNCATE TABLE gamesetting";
        String query = "INSERT INTO gamesetting VALUES(?,?,?,?)";

        try {
            if (initConnection()) {
                Statement s = conn.createStatement();
                s.executeUpdate(trunQuery);
                
                PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                st.setDouble(1, gameSetting.getLevelUp());
                st.setInt(2, gameSetting.getMaxFuelCapacity());
                st.setInt(3, gameSetting.getMaxUser());
                st.setInt(4, gameSetting.getRequiredExperiencePoints());
                st.executeUpdate();
                
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return false;
    }
}
