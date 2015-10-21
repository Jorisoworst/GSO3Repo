/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public List<User> GetUsers()
    {
        List<User> users = new ArrayList<>();
        
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
                int experiencePoint = rs.getInt("ExperiancePoint");
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
    
    public boolean AddUser(User user)
    {   
        String query = "INSERT INTO user (DateOfBirth, DisplayName, ExperiancePoint, IsBanned, Level, Password, PersonalBest, RegistrationDate, Username, UserType) VALUES(?,?,?,?,?,?,?,?,?,?)";
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
            st.setString(9, user.getUsername());
            if(user instanceof Administrator)
            {
                st.setString(10, "A");
            }
            else if(user instanceof Moderator)
            {
                st.setString(10, "M");
            }
            else
            {
                st.setString(10, "P");
            }
            st.executeQuery();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return false;
    }
    
    public boolean UpdateUser(User user)
    {
        String query = "UPDATE user SET DateOfBirth = ?, DisplayName = ?, ExperiancePoint = ?, IsBanned = ?, Level = ?, Password = ?, PersonalBest = ?,"
                + " RegistrationDate = ?, Username = ?, UserType = ? WHERE Id = ?";
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
            st.setString(9, user.getUsername());
            if(user instanceof Administrator)
            {
                st.setString(10, "A");
            }
            else if(user instanceof Moderator)
            {
                st.setString(10, "M");
            }
            else
            {
                st.setString(10, "P");
            }
            st.setInt(11, user.getId());
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
            st.executeQuery();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAdministration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    public List<Score> SelectScore()
    {
        
        return null;
    }
    
    public boolean AddScore(Score score)
    {
        
        return false;
    }
    
    public boolean UpdateScore(Score score)
    {
        
        return false;
    }
    
    public boolean DeleteScore(int scoreId)
    {
        return false;
    }
    
    
    
    
    
}
