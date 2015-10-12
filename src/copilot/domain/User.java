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
public abstract class User {

    /**
     * @return the nextId
     */
    public static int getNextId() {
        return nextId;
    }

    /**
     * @param aNextId the nextId to set
     */
    public static void setNextId(int aNextId) {
        nextId = aNextId;
    }
    private boolean isBanned;
    private Date registrationDate;
    private Date dateOfBirth;
    private int id;
    private int personalBestScore;
    private int experiencePoints;
    private int level;
    private String username;
    private String password;
    private String displayName;
    private static int nextId = 0;
    
    /**
     * Initialize an instance of the User class which is abstract
     * @param username the username, may not be null or empty
     * @param password the password, may not be null or empty
     * @param dateOfBirth the date of birth, may not be null or empty
     */
    public User(String username, String password, Date dateOfBirth) {
        this.isBanned = false;
        this.registrationDate = new Date();
        this.dateOfBirth = dateOfBirth;
        this.id = User.nextId;
        this.personalBestScore = 0;
        this.experiencePoints = 0;
        this.username = username;
        this.password = password;
        
        User.nextId++;
    }

    /**
     * @return the isBanned
     */
    public boolean isIsBanned() {
        return isBanned;
    }

    /**
     * @param isBanned the isBanned to set
     */
    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * @return the registrationDate
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the personalBestScore
     */
    public int getPersonalBestScore() {
        return personalBestScore;
    }

    /**
     * @param personalBestScore the personalBestScore to set
     */
    public void setPersonalBestScore(int personalBestScore) {
        this.personalBestScore = personalBestScore;
    }

    /**
     * @return the experiencePoints
     */
    public int getExperiencePoints() {
        return experiencePoints;
    }

    /**
     * @param experiencePoints the experiencePoints to set
     */
    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }
    
    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
