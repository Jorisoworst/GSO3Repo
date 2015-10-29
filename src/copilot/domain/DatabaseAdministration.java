/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ruud
 */
public class DatabaseAdministration {
    
    private String serverUrl = "jdbc:mysql://athena01.fhict.local:3306/dbi324569";
    private String username = "dbi324569";
    private String password = "ccxQc8VL6i";
    private Connection conn;
    
    public DatabaseAdministration() throws Exception
    {
        if(!initConnection())
        {
            throw new Exception("initializing database failed.");
        }
    }
    
    private boolean initConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(serverUrl,username,password);
            return true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            return false;
        }    
    }
    
    public ArrayList<User> GetUsers()
    {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next())
            {
                int accountId = rs.getInt("Id");
                Date bday = rs.getDate("DateOfBirth");
                Calendar calBirthday = null;
                if(bday != null)
                {
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
                if(registrationDate != null)
                {
                    calRegistrationDate = Calendar.getInstance();
                    calRegistrationDate.setTime(bday);
                }
                
                String username = rs.getString("Username");
                String userType = rs.getString("UserType");
                //a,m,p
                User user = null;
                if(userType.equals("A"))
                {
                    user = new Administrator(username, password, calBirthday);
                }
                else if(userType.equals("M"))
                {
                    user = new Moderator(username, password, calBirthday);
                    
                }
                else if(userType.equals("P"))
                {
                    user = new Player(username, password, calBirthday);
                    
                }
                if(user != null)
                {
                    user.setDisplayName(displayName);
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
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return users;
    }
    
    public User AddUser(User user)
    {   
        String query = "INSERT INTO user (DateOfBirth, DisplayName, ExperiencePoint, IsBanned, Level, Password, PersonalBest, RegistrationDate, Report, Username, UserType) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
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
            if(user instanceof Administrator)
            {
                st.setString(11, "A");
            }
            else if(user instanceof Moderator)
            {
                st.setString(11, "M");
            }
            else
            {
                st.setString(11, "P");
            }
            int resultId = st.executeUpdate();
             
            user.setId(resultId);
            return user;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }
    
    public boolean UpdateUser(User user)
    {
        String query = "UPDATE user SET DateOfBirth = ?, DisplayName = ?, ExperiencePoint = ?, IsBanned = ?, Level = ?, Password = ?, PersonalBest = ?,"
                + " RegistrationDate = ?, Report = ?, Username = ?, UserType = ? WHERE Id = ?";
        try {
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
            if(user instanceof Administrator)
            {
                st.setString(11, "A");
            }
            else if(user instanceof Moderator)
            {
                st.setString(11, "M");
            }
            else
            {
                st.setString(11, "P");
            }
            st.setInt(12, user.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean DeleteUser(int userId)
    {
        try {
            String query = "DELETE FROM user WHERE Id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, userId);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    public ArrayList<Score> SelectScore()
    {
        ArrayList<Score> scores = new ArrayList<>();
        
        String query = "SELECT * FROM score";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next())
            {
                int scoreId = rs.getInt("Id");
                int score = rs.getInt("Score");
                int user1 = rs.getInt("User_1");
                int user2 = rs.getInt("User_2");
                int user3 = rs.getInt("User_3");
                int user4 = rs.getInt("User_4");
                
                Score scoreObj = new Score(score,user1,user2,user3,user4);
                scoreObj.setScoreId(scoreId);
                scores.add(scoreObj);
            }
            
            st.close();
            return scores;
        }
        catch(SQLException sqe)
        {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, sqe);
        }
        
        return null;
    }
    
    public Score AddScore(Score score)
    {
        String query = "INSERT INTO score (Score, User_1, User_2, User_3, User_4) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, score.getScore());
            st.setInt(2, score.getUser1id());
            st.setInt(3, score.getUser2id());
            st.setInt(4, score.getUser3id());
            st.setInt(5, score.getUser4id());
            int resultId = st.executeUpdate();
            score.setScoreId(resultId);
            return score;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
      public boolean UpdateScore(Score score)
    {
        String query = "UPDATE score SET Score = ?, User_1 =?, User_2 = ?, User_3 = ?, User_4 = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, score.getScore());
            st.setInt(2, score.getUser1id());
            st.setInt(3, score.getUser2id());
            st.setInt(4, score.getUser3id());
            st.setInt(5, score.getUser4id());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean DeleteScore(int scoreId)
    {
         try {
            String query = "DELETE FROM score WHERE Id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, scoreId);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public GameSetting GetGameSetting()
    {
        GameSetting gameSettings = null;
        String query = "SELECT * FROM gameSetting";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next())
            {
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
            st.close();
        }
        catch(SQLException sqe)
        {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, sqe);
        }
        
        return gameSettings;
    }
    
    public boolean SaveGameSetting(GameSetting gameSetting)
    {
        String trunQuery = "TRUNCATE TABLE gamesetting";
        String query = "INSERT INTO gamesetting VALUES(?,?,?,?)";
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(trunQuery);
            PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setDouble(1, gameSetting.getLevelUp());
            st.setInt(2, gameSetting.getMaxFuelCapacity());
            st.setInt(3, gameSetting.getMaxUser());
            st.setInt(4, gameSetting.getRequiredExperiencePoints());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
