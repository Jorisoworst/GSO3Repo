package copilot.domain;

import java.util.Calendar;

/**
 * @author IndyGames
 */
public abstract class User {

    private static int nextId = 0;

    /**
     * @return the nextId
     */
    public static int getNextId() {
        return User.nextId;
    }

    /**
     * @param nextId the nextId to set
     */
    public static void setNextId(int nextId) {
        User.nextId = nextId;
    }

    private Calendar registrationDate;
    private Calendar dateOfBirth;
    private String username;
    private String password;
    private String displayName;
    private boolean isBanned;
    private int id;
    private int personalBestScore;
    private int experiencePoints;
    private int level;
    private int reports;

    /**
     * Initialize an instance of the User class which is abstract
     *
     * @param username the username, may not be null or empty and must be unique
     * @param password the password, may not be null or empty
     * @param displayName the displayName, may be null
     * @param dateOfBirth the date of birth, may not be null
     */
    public User(String username, String password, String displayName, Calendar dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("No date of birth set!");
        }

        if (username == null
                || username.isEmpty()) {
            throw new IllegalArgumentException("No username set!");
        }

        if (password == null
                || password.isEmpty()) {
            throw new IllegalArgumentException("No password set!");
        }

        this.isBanned = false;
        this.registrationDate = Calendar.getInstance();
        this.dateOfBirth = dateOfBirth;
        this.id = User.nextId;
        this.personalBestScore = 0;
        this.experiencePoints = 0;
        this.reports = 0;
        this.username = username;
        this.password = password;

        if (displayName == null
                || displayName.isEmpty()) {
            this.displayName = username;
        } else {
            this.displayName = displayName;
        }

        User.nextId++;
    }

    /**
     * @return the isBanned
     */
    public boolean isBanned() {
        return this.isBanned;
    }

    /**
     * @param isBanned the isBanned to set
     */
    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * @return the registrationDate
     */
    public Calendar getRegistrationDate() {
        return this.registrationDate;
    }

    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Calendar registrationDate) {
        if (registrationDate == null) {
            throw new IllegalArgumentException("No registration date set!");
        }

        this.registrationDate = registrationDate;
    }

    /**
     * @return the dateOfBirth
     */
    public Calendar getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(Calendar dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("No date of birth set!");
        }

        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id the id to set, must not be negative
     */
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Value of id too low!");
        }

        this.id = id;
    }

    /**
     * @return the personalBestScore
     */
    public int getPersonalBestScore() {
        return this.personalBestScore;
    }

    /**
     * @param personalBestScore the personalBestScore to set, must not be
     * negative
     */
    public void setPersonalBestScore(int personalBestScore) {
        if (personalBestScore < 0) {
            personalBestScore = 0;
        } else {
            this.personalBestScore = personalBestScore;
        }
    }

    /**
     * @return the experiencePoints
     */
    public int getExperiencePoints() {
        return this.experiencePoints;
    }

    /**
     * @param experiencePoints the experiencePoints to set, must not be negative
     */
    public void setExperiencePoints(int experiencePoints) {
        if (experiencePoints < 0) {
            experiencePoints = 0;
        } else {
            this.experiencePoints = experiencePoints;
        }
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        if (level <= 0) {
            this.level = 1;
        } else {
            this.level = level;
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        if (username == null
                || username.isEmpty()) {
            throw new IllegalArgumentException("No username set!");
        }

        this.username = username;
    }

    /**
     * @return the reports
     */
    public int getReports() {
        return this.reports;
    }

    /**
     * @param reports the reports to set, must not be negative
     */
    public void setReports(int reports) {
        if (reports < 0) {
            throw new IllegalArgumentException("Value of reports too low!");
        }

        this.reports = reports;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set, must not be null or empty
     */
    public void setPassword(String password) {
        if (password == null
                || password.isEmpty()) {
            throw new IllegalArgumentException("No password set!");
        }

        this.password = password;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * @param displayName the displayName to set, must not be null or empty
     */
    public void setDisplayName(String displayName) {
        if (displayName == null
                || displayName.isEmpty()) {
            throw new IllegalArgumentException("No display name set!");
        }

        this.displayName = displayName;
    }

    /**
     * Method to add a report
     */
    public void addReport() {
        this.reports++;
    }
}
